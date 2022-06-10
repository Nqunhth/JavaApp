package com.example.myapp.controller.fragment.advertise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;

public class Whyus extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //show fragment
        View view = inflater.inflate(R.layout.fragment_ad_whyus, container, false);

        return view;
    }
}