package com.example.myapp.controller.fragment.qa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.controller.adapter.QaListRVAdapter;
import com.example.myapp.controller.fragment.user.MainUser;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.model.user.UserModel;
import com.example.myapp.utils.FragMethodToAdapter;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.model.qa.QaModel;
import com.example.myapp.utils.Fetcher;
import com.example.myapp.utils.JSONHandler;
import com.example.myapp.utils.Listener;
import com.example.myapp.utils.MyResult;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.namespace.QName;

public class MainQA extends Fragment implements FragMethodToAdapter {
    private int success = 0;
    private JSONArray restfulJsonArray;
    private ArrayList<QaModel> rvItems = new ArrayList<>();
    private String apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/QandA/qa/list/";
    private ArrayList<String> rv_filter = new ArrayList<>();
    ArrayList<QaModel> rv_filteredlist = new ArrayList<>();
    private QaListRVAdapter adapter;

    public MainQA() { }

    /**
     * set constructor
     * @param test
     */
    public MainQA(ArrayList<String> test) {
        rv_filter = test;
        Log.d("mainQa", rv_filter.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_qa, container, false);
        FloatingActionButton b_add = view.findViewById(R.id.qa_button_add);
        MaterialButton b_filter = view.findViewById(R.id.qa_filter);
        RecyclerView rv = view.findViewById(R.id.qa_rv);
        SearchView search = view.findViewById(R.id.qa_search);
        adapter = new QaListRVAdapter(this.getContext(), rvItems, this);

        if (CurrentUser.getCurrentUser().getPosition().equals("employee"))
            b_add.setVisibility(View.VISIBLE);
        else
            b_add.setVisibility(View.GONE);

        //Q & A
        String s1 = getResources().getString(R.string.qa_title);
        SpannableString ss1 = new SpannableString(s1);
        ss1.setSpan(new RelativeSizeSpan(0.6f), 1, 3, 0); // set size
        TextView tv1 = (TextView) view.findViewById(R.id.qa_title);
        tv1.setText(ss1);

        //FloatButton
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QaCreate dialogFragment = new QaCreate();
                dialogFragment.show(getFragmentManager(), "create qa dialog");
            }
        });
        //RecyclerView
        rv.setAdapter(adapter);

        Fetcher.fetchDataFrom(apiUrl, new Listener<String>() {
            @Override
            public void on(String result) {
                MyResult temp = JSONHandler.turnToJSONObject("qa", result);
                success = temp.getStatus();
                restfulJsonArray = temp.getJSONArray();

                if (success == 1) {
                    rvItems.clear();
                    if (null != restfulJsonArray) {
                        for (int i = 0; i < restfulJsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = restfulJsonArray.getJSONObject(i);
                                rvItems.add(new QaModel(jsonObject));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        };
                        adapter.setItems(rvItems);
                        adapter.notifyDataSetChanged();
                        reloadForFilter();
                    }
                }
            }
        });
        b_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QaFilter dialogFragment = new QaFilter(rv_filter);
                dialogFragment.show(getFragmentManager(), "simple dialog");
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

    // call dialog UpdateOa
    @Override
    public void updateQa(QaModel qa) {
        QaCreate dialogFragment = new QaCreate(qa, true);
        dialogFragment.show(getFragmentManager(), "create qa dialog");
    }
    @Override
    public void updateNews(NewsModel news) {}

    @Override
    public void callDialog(UserModel item, SwitchCompat object) { }

    // show dialog confirm
    @Override
    public void callDialog(String itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thực hiện xóa mục Hỏi đáp này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "qa_id";
                        String[] data = new String[1];
                        data[0] = itemId;
                        PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/QandA/qacontroller.php?view=remove", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.contains("Remove Success")){
                                    Toast.makeText(getActivity(), "Xóa bỏ thành công", Toast.LENGTH_SHORT).show();
                                    refresh();
                                }
                                else{
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
     * search Qa
     * @param text
     */
    private void filter(String text) {
        Log.d("bcvbc",rvItems.toString());
        ArrayList<QaModel> filteredlist = new ArrayList<>();

        for (QaModel item : rvItems) {
            if (item.getQaQ().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
//            Toast.makeText(this.getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

    /**
     * filter Qa by chips
     * @param text
     */
    private void filterByChip(String text) {
        if (!text.equals("center") && !text.equals("course")) {
            for (QaModel item : rvItems) {
                if (item.getQaSubSub_a().equals(text)) {
                    rv_filteredlist.add(item);
                }
            }
        } else {
            for (QaModel item : rvItems) {
                if (item.getQaMainSub().equals(text)) {
                    rv_filteredlist.add(item);
                }
            }
        }
        if (rv_filteredlist.isEmpty()) {
            Toast.makeText(this.getContext(), "Không tìm thấy dữ liệu phù hợp", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * reset filter chips
     */
    private void reloadForFilter() {
        if (rv_filter.size() > 0) {
            for (int i = 0; i < rv_filter.size(); i++) {
                filterByChip(rv_filter.get(i));
            }
        }
        if (!rv_filteredlist.isEmpty()) {
            adapter.filterList(rv_filteredlist);
        }
    }

    /**
     * refresh fragment
     */
    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, new MainQA());
        transaction.commit();
    }
}
