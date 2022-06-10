package com.example.myapp.controller.fragment.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.google.android.material.imageview.ShapeableImageView;

import org.w3c.dom.Text;

public class IntroFacility extends Fragment {
    private ShapeableImageView ib_faci;
    private ShapeableImageView ib_zone;
    private ShapeableImageView ib_class;
    private ShapeableImageView ib_tech;
    private TextView tv_title;
    private TextView tv_content;
    private ImageView iv_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_intro_facility, container, false);

        // call UI object
        ib_faci = view.findViewById(R.id.intro_faci_faci);
        ib_zone = view.findViewById(R.id.intro_faci_zone);
        ib_class = view.findViewById(R.id.intro_faci_class);
        ib_tech = view.findViewById(R.id.intro_faci_tech);
        tv_title = view.findViewById(R.id.intro_faci_title);
        tv_content = view.findViewById(R.id.intro_faci_content);
        iv_img = view.findViewById(R.id.intro_faci_img);

        //imple OnItemClick to show info
        setSelected(ib_faci,R.string.intro_faci_t_faci,R.string.intro_faci_d_faci,R.drawable.intro_zone);
        ib_faci.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetButton();
                setSelected(ib_faci,R.string.intro_faci_t_faci,R.string.intro_faci_d_faci,R.drawable.intro_zone);
            }
        });
        ib_zone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetButton();
                setSelected(ib_zone,R.string.intro_faci_t_zone,R.string.intro_faci_d_zone, R.drawable.intro_facility);
            }
        });
        ib_class.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetButton();
                setSelected(ib_class,R.string.intro_faci_t_class,R.string.intro_faci_d_class,R.drawable.intro_class);
            }
        });
        ib_tech.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetButton();
                setSelected(ib_tech,R.string.intro_faci_t_tech,R.string.intro_faci_d_tech,R.drawable.intro_tech);
            }
        });
        return view;
    }

    /**
     * reset all button
     */
    private void resetButton(){
        ib_faci.setBackgroundResource(R.drawable.round_corner_layout);
        ib_zone.setBackgroundResource(R.drawable.round_corner_layout);
        ib_class.setBackgroundResource(R.drawable.round_corner_layout);
        ib_tech.setBackgroundResource(R.drawable.round_corner_layout);
    }

    /**
     * set selected Button
     * @param ib
     * @param title
     * @param content
     * @param imgResource
     */
    private void setSelected(ShapeableImageView ib, int title, int content, int imgResource){
        ib.setBackgroundResource(R.drawable.intro_faci_bg_onselect);
        tv_title.setText(title);
        tv_content.setText(content);
        Glide.with(this.getContext())
                .load(imgResource)
                .fitCenter()
                .into(iv_img);
    }
}
