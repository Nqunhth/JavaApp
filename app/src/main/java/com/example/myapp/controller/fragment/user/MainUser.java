package com.example.myapp.controller.fragment.user;

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
import com.example.myapp.controller.adapter.UserListRVAdapter;
import com.example.myapp.controller.fragment.qa.MainQA;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.model.qa.QaModel;
import com.example.myapp.utils.FragMethodToAdapter;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.model.user.UserModel;
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

public class MainUser extends Fragment implements FragMethodToAdapter {
    private int success = 0;
    private JSONArray restfulJsonArray;
    private ArrayList<UserModel> rvItems = new ArrayList<>();
    private String apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/Account/account/list_detail/";
    UserListRVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_user, container, false);
        RecyclerView rv = view.findViewById(R.id.user_list_rv);
        FloatingActionButton b_add = view.findViewById(R.id.user_button_add);
        ImageView iv = view.findViewById(R.id.user_list_backicon);
        SearchView search = view.findViewById(R.id.user_search);

        adapter = new UserListRVAdapter(this.getContext(), rvItems, this);

        rv.setAdapter(adapter);
        // fetch data
        Fetcher.fetchDataFrom(apiUrl, new Listener<String>() {
            @Override
            public void on(String result) {
                MyResult temp = JSONHandler.turnToJSONObject("accounts_detail" ,result);
                success = temp.getStatus();
                restfulJsonArray = temp.getJSONArray();
                if (success == 1) {
                    rvItems.clear();
                    if (null != restfulJsonArray) {
                        for (int i = 0; i < restfulJsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = restfulJsonArray.getJSONObject(i);
                                rvItems.add(new UserModel(jsonObject));
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
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegist dialogFragment = new UserRegist();
                dialogFragment.show(getFragmentManager(),"add user");
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(view);
            }
        });
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
     * search for User
     * @param text
     */
    private void filter(String text) {
        ArrayList<UserModel> filteredlist = new ArrayList<>();

        for (UserModel item : rvItems) {
            if (item.getFullname().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            //Toast.makeText(this.getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }


    @Override
    public void updateQa(QaModel qa) {
    }

    @Override
    public void callDialog(String itemId) {
    }

    @Override
    public void updateNews(NewsModel news) {
    }

    @Override
    public void callDialog(UserModel user, SwitchCompat object) {
        String status = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        if(object.isChecked()) {
            status = "enable";
            builder.setMessage("Bạn có muốn kích hoạt tài khoản này không ?");
        }
        else {
            status = "disable";
            builder.setMessage("Bạn có muốn khóa tài khoản này không ?");
        };
        String finalStatus = status;
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "account_id";
                        field[1] = "email";
                        String[] data = new String[2];
                        data[0] = user.getAccountId();
                        data[1] = user.getEmail();
                        PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/Account/accountcontroller.php?view=" + finalStatus, "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.contains("Success")){
                                    Toast.makeText(getActivity(), "Thao tác thành công", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        refresh();
                    }
                });
            }
        });
        builder.setNeutralButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Hủy thực hiện", Toast.LENGTH_SHORT).show();
                refresh();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, new MainUser());
        transaction.commit();
    }
}