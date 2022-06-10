package com.example.myapp.controller.fragment.imageSlider;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.myapp.R;
import com.example.myapp.controller.adapter.AutoSliderAdapter;
import com.example.myapp.model.imageSlider.SliderData;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.utils.Fetcher;
import com.example.myapp.utils.JSONHandler;
import com.example.myapp.utils.Listener;
import com.example.myapp.utils.MyResult;
import com.example.myapp.utils.ShareIP;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Image Slider with auto play
 */
public class AutoSlider extends Fragment {
    private int success = 0;
    private JSONArray restfulJsonArray;
    private ArrayList<NewsModel> rvItems = new ArrayList<>();
    private String apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/News/news/news_slider/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_auto_slider, container, false);

        SliderView sliderView = view.findViewById(R.id.autoSlider);
        AutoSliderAdapter adapter = new AutoSliderAdapter(this.requireContext(), rvItems);

        //setup Slider
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        //fetch data to Slider
        Fetcher.fetchDataFrom(apiUrl, new Listener<String>() {
            @Override
            public void on(String result) {
                MyResult temp = JSONHandler.turnToJSONObject("newest_slider" ,result);
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
        return view;
    }
    public void invisible(){
        getView().setVisibility(View.GONE);
    }

}

