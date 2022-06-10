package com.example.myapp.controller.fragment.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.controller.adapter.NewsGeneralRVAdapter;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.utils.Fetcher;
import com.example.myapp.utils.JSONHandler;
import com.example.myapp.utils.Listener;
import com.example.myapp.utils.MyResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainNews extends Fragment {
    private int success = 0;
    private JSONArray restfulJsonArray;
    private ArrayList<NewsModel> rvItems = new ArrayList<>();
    private String apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/News/news/list_detail/";
    NewsGeneralRVAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_news, container, false);
        TextView tv_transaction = view.findViewById(R.id.news_transaction);
        RecyclerView rv = view.findViewById(R.id.news_general_rv);

        // call UI object
        SearchView search = view.findViewById(R.id.news_search);
        TextView tv_for_gone = view.findViewById(R.id.news_title);
        View v_for_gone = view.findViewById(R.id.view);
        View fm_for_gone = view.findViewById(R.id.news_slider);
        adapter = new NewsGeneralRVAdapter (this.getContext(),rvItems);

        // build adapter
        rv.setAdapter(adapter);
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

        tv_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replace(new NewsList());
            }
        });

        //search News function
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setIconified(false);
                tv_for_gone.setVisibility(View.GONE);
                v_for_gone.setVisibility(View.GONE);
                fm_for_gone.setVisibility(View.GONE);
            }
        });
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv_for_gone.setVisibility(View.VISIBLE);
                v_for_gone.setVisibility(View.VISIBLE);
                fm_for_gone.setVisibility(View.VISIBLE);
                return false;
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
     * refresh fragment
     * @param fragment
     */
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment);
        transaction.addToBackStack("main_news");
        transaction.commit();
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
}