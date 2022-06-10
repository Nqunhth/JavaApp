package com.example.myapp.controller.fragment.news;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.controller.adapter.NewsListRVAdapter;
import com.example.myapp.controller.fragment.qa.MainQA;
import com.example.myapp.model.qa.QaModel;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.model.user.UserModel;
import com.example.myapp.utils.FragMethodToAdapter;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.utils.Fetcher;
import com.example.myapp.utils.JSONHandler;
import com.example.myapp.utils.Listener;
import com.example.myapp.utils.MyResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsList extends Fragment implements FragMethodToAdapter {
    private int success = 0;
    private JSONArray restfulJsonArray;
    private ArrayList<NewsModel> rvItems = new ArrayList<>();
    private String apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/News/news/list_detail/";
    NewsListRVAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        RecyclerView rv = view.findViewById(R.id.news_list_rv);
        ImageView iv = view.findViewById(R.id.news_list_backicon);
        FloatingActionButton b_add = view.findViewById(R.id.news_button_add);
        SearchView search = view.findViewById(R.id.news_list_search);
        if(CurrentUser.getCurrentUser().getPosition().equals("employee"))
            b_add.setVisibility(View.VISIBLE);
        else
            b_add.setVisibility(View.GONE);

        //build adapter
        adapter = new NewsListRVAdapter (this.getContext(),rvItems, this);
        rv.setAdapter(adapter);

        //fetch data to list
        Fetcher.fetchDataFrom(apiUrl, new Listener<String>() {
            @Override
            public void on(String result) {
                MyResult temp = JSONHandler.turnToJSONObject("news_detail" ,result);
                success = temp.getStatus();
                restfulJsonArray = temp.getJSONArray();

                if (success == 1) {
                    rvItems.clear();
                    if (null != restfulJsonArray) {
                        for (int i = 0; i < restfulJsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = restfulJsonArray.getJSONObject(i);
                                rvItems.add(new NewsModel(jsonObject));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        };
                        adapter.setItems(rvItems);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replace(new NewsCreate());
            }
        });

        // search News function
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setIconified(false);
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return view;
    }

    /**
     * back to previous fragment
     * @param v
     */
    private void back(View v){
        getFragmentManager().popBackStack();
    }

    /**
     * replace fragment
     * @param fragment
     */
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment);
        transaction.addToBackStack("news_list");
        transaction.commit();
    }

    @Override
    public void updateQa(QaModel qa) {}
    @Override
    public void updateNews(NewsModel news) { }

    @Override
    public void callDialog(UserModel item, SwitchCompat object) { }

    /**
     * show dialog confirm
     * @param itemId
     */
    @Override
    public void callDialog(String itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thực hiện xóa Bài đăng này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "post_id";
                        String[] data = new String[1];
                        data[0] = itemId;
                        PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/News/newscontroller.php?view=remove", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.contains("Remove Success")) {
                                    Toast.makeText(getActivity(), "Xóa bỏ thành công", Toast.LENGTH_SHORT).show();
                                    refresh();
                                } else {
                                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
        builder.setNeutralButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Hủy thực hiện", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * search News
     * @param text
     */
    private void filter(String text) {
        ArrayList<NewsModel> filteredlist = new ArrayList<>();

        for (NewsModel item : rvItems) {
            if (item.getPostTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            //Toast.makeText(this.getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

    /**
     * refresh fragment
     */
    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, new NewsList());
        transaction.commit();
    }
}