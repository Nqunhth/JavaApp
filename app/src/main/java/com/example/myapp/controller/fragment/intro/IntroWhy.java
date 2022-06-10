package com.example.myapp.controller.fragment.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myapp.R;

public class IntroWhy extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_intro_why, container, false);

        View ad_whyus = view.findViewById(R.id.who_us);

        //call UI object and set TextColor
        TextView why_title = ad_whyus.findViewById(R.id.why_title);
        why_title.setTextColor(getResources().getColor(R.color.intro_text_1));
        TextView why_content = ad_whyus.findViewById(R.id.why_content);
        why_content.setTextColor(getResources().getColor(R.color.sunrise_1));

        View ad_achie_a = view.findViewById(R.id.who_achie_1);
        View ad_achie_b = view.findViewById(R.id.who_achie_2);

        //Change Border Layout
        ConstraintLayout achie_1a = ad_achie_a.findViewById(R.id.achie_1);
        ConstraintLayout achie_2a = ad_achie_a.findViewById(R.id.achie_2);
        ConstraintLayout achie_3a = ad_achie_a.findViewById(R.id.achie_3);
        achie_1a.setBackgroundResource(R.drawable.intro_border_layout);
        achie_2a.setBackgroundResource(R.drawable.intro_border_layout);
        achie_3a.setBackgroundResource(R.drawable.intro_border_layout);

        ConstraintLayout achie_1b = ad_achie_b.findViewById(R.id.achie_1);
        ConstraintLayout achie_2b = ad_achie_b.findViewById(R.id.achie_2);
        ConstraintLayout achie_3b = ad_achie_b.findViewById(R.id.achie_3);
        achie_1b.setBackgroundResource(R.drawable.intro_border_layout);
        achie_2b.setBackgroundResource(R.drawable.intro_border_layout);
        achie_3b.setBackgroundResource(R.drawable.intro_border_layout);

        //Change achievement block 1
        TextView data_1a = ad_achie_a.findViewById(R.id.ad_data_1);
        TextView type_1a = ad_achie_a.findViewById(R.id.ad_type_1);
        data_1a.setTextColor(getResources().getColor(R.color.intro_text_1));
        type_1a.setTextColor(getResources().getColor(R.color.intro_text_1));

        TextView data_1b = ad_achie_b.findViewById(R.id.ad_data_1);
        TextView type_1b = ad_achie_b.findViewById(R.id.ad_type_1);
        data_1b.setTextColor(getResources().getColor(R.color.intro_text_1));
        data_1b.setText("Môi trường");
        type_1b.setTextColor(getResources().getColor(R.color.intro_text_1));
        type_1b.setText("thân thiện, năng động");

        //Change achievement block 2
        TextView data_2a = ad_achie_a.findViewById(R.id.ad_data_2);
        TextView type_2a = ad_achie_a.findViewById(R.id.ad_type_2);
        data_2a.setTextColor(getResources().getColor(R.color.intro_text_1));
        type_2a.setTextColor(getResources().getColor(R.color.intro_text_1));

        TextView data_2b = ad_achie_b.findViewById(R.id.ad_data_2);
        TextView type_2b = ad_achie_b.findViewById(R.id.ad_type_2);
        data_2b.setTextColor(getResources().getColor(R.color.intro_text_1));
        data_2b.setText("Giáo viên");
        type_2b.setTextColor(getResources().getColor(R.color.intro_text_1));
        type_2b.setText("tận tụy, giàu kinh nghiệm");

        //Change achievement block 3
        TextView data_3a = ad_achie_a.findViewById(R.id.ad_data_3);
        TextView type_3a = ad_achie_a.findViewById(R.id.ad_type_3);
        data_3a.setTextColor(getResources().getColor(R.color.intro_text_1));
        type_3a.setTextColor(getResources().getColor(R.color.intro_text_1));

        TextView data_3b = ad_achie_b.findViewById(R.id.ad_data_3);
        TextView type_3b = ad_achie_b.findViewById(R.id.ad_type_3);
        data_3b.setTextColor(getResources().getColor(R.color.intro_text_1));
        data_3b.setText("Giảng dạy");
        type_3b.setTextColor(getResources().getColor(R.color.intro_text_1));
        type_3b.setText("hiệu quả, chất lượng");
        return view;
    }
}