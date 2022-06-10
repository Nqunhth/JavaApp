package com.example.myapp.controller.fragment.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.controller.adapter.IntroTeacherRVAdapter;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.model.teacher.TeacherModel;
import com.example.myapp.utils.Fetcher;
import com.example.myapp.utils.JSONHandler;
import com.example.myapp.utils.Listener;
import com.example.myapp.utils.MyResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IntroTeacher extends Fragment {
    private int success = 0;
    private JSONArray restfulJsonArray;
    private ArrayList<TeacherModel> rvItems = new ArrayList<>();
    private IntroTeacherRVAdapter adapter = new IntroTeacherRVAdapter(this.getContext(),rvItems);
    private String apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/Teacher/teacher/list/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_intro_teacher, container, false);

        //Build adapter
        RecyclerView rv = view.findViewById(R.id.intro_teacher_rv);
        rv.setAdapter(adapter);

        //Fetch data to list
        Fetcher.fetchDataFrom(apiUrl, new Listener<String>() {
            @Override
            public void on(String result) {
                MyResult temp = JSONHandler.turnToJSONObject("teachers" ,result);
                success = temp.getStatus();
                restfulJsonArray = temp.getJSONArray();

                if (success == 1) {
                    rvItems.clear();
                    if (null != restfulJsonArray) {
                        for (int i = 0; i < restfulJsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = restfulJsonArray.getJSONObject(i);
                                rvItems.add(new TeacherModel(jsonObject));
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
        return view;
    }
}
