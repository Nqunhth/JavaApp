package com.example.myapp.controller.fragment.news;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.controller.adapter.NewsListRVAdapter;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.model.user.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsDetail extends Fragment {
    private NewsModel news;
    public NewsDetail(NewsModel _news) {news = _news;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ImageView iv_back = view.findViewById(R.id.news_detail_backicon);

        //cal UI object
        TextView tv_title = view.findViewById(R.id.news_detail_title);
        TextView tv_author = view.findViewById(R.id.news_detail_author);
        TextView tv_position = view.findViewById(R.id.news_detail_position);
        ImageView iv_image = view.findViewById(R.id.news_detail_image);
        TextView tv_content = view.findViewById(R.id.news_detail_content);
        TextView tv_date = view.findViewById(R.id.news_detail_date);
        TextView tv_time = view.findViewById(R.id.news_detail_time);
        tv_title.setText(news.getPostTitle());
        tv_author.setText(news.getAuthorName());
        tv_position.setText(news.getDisplay_place());

        //handle News image with Glide
        Glide.with(view)
                .load(news.getPostImg())
                .placeholder(R.drawable.news_detail_placeholder)
                .error(R.drawable.news_detail_placeholder)
                .centerCrop()
                .into(iv_image);
        tv_content.setText(news.getPostContent());

        String daa = news.getPostUpdate();

        Log.d("time",daa);
        tv_date.setText(daa);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(view);
            }
        });
        tv_content.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    /**
     * back to previous fragment
     * @param v
     */
    private void back(View v){
        getFragmentManager().popBackStack();
    }
}