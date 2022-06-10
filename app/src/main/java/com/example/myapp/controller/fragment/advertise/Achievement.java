package com.example.myapp.controller.fragment.advertise;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;

public class Achievement extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            //show fragment
            View view = inflater.inflate(R.layout.fragment_ad_achievement, container, false);

            //8.5/9.0
            String s1= getResources().getString(R.string.ad_data_1);
            SpannableString ss1=  new SpannableString(s1);
            ss1.setSpan(new RelativeSizeSpan(2f), 0,3, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.sunrise_1)), 0, 3, 0);// set color
            TextView tv1= (TextView) view.findViewById(R.id.ad_data_1);
            tv1.setText(ss1);

            //900/990
            String s2= getResources().getString(R.string.ad_data_2);
            SpannableString ss2=  new SpannableString(s2);
            ss2.setSpan(new RelativeSizeSpan(2f), 0,3, 0); // set size
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.sunrise_1)), 0, 3, 0);// set color
            TextView tv2= (TextView) view.findViewById(R.id.ad_data_2);
            tv2.setText(ss2);

            //50 trieu
            String s3= getResources().getString(R.string.ad_data_3);
            SpannableString ss3=  new SpannableString(s3);
            ss3.setSpan(new RelativeSizeSpan(2f), 0,2, 0); // set size
            ss3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.sunrise_1)), 0, 2, 0);// set color
            TextView tv3= (TextView) view.findViewById(R.id.ad_data_3);
            tv3.setText(ss3);
            return view;
        }



}
