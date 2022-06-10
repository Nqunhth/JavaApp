package com.example.myapp.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapp.R;
import com.example.myapp.controller.fragment.news.NewsDetail;
import com.example.myapp.model.imageSlider.SliderData;
import com.example.myapp.model.news.NewsModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for AutoSlider
 */
public class AutoSliderAdapter extends SliderViewAdapter<AutoSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<NewsModel> mSliderItems;

    // Constructor
    public AutoSliderAdapter(Context context, ArrayList<NewsModel> sliderDataArrayList) {
        this.context = context;
        this.mSliderItems = sliderDataArrayList;
    }

    // Renew Items in slider
    public void renewItems(List<NewsModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    //delete Item in slider
    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    //add Item in silder
    public void addItem(NewsModel sliderData) {
        this.mSliderItems.add(sliderData);
        notifyDataSetChanged();
    }

    //Build Slider
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_image_view, null);
        return new SliderAdapterVH(inflate);
    }

    //Build Slider
    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        NewsModel item = mSliderItems.get(position);
        Glide.with(viewHolder.itemView)
                .load(item.getPostImg())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Chuyển đến chi tiết tin tức", Toast.LENGTH_SHORT).show();
                replace(new NewsDetail(item));
            }
        });
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    //Build Slider
    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image_for_Slider);
            this.itemView = itemView;
        }
    }

    /**
     * replace fragment
     * @param fragment
     */
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment,"news_list");
        transaction.addToBackStack("news_list");
        transaction.commit();
    }

    /**
     * set list Item for slider
     * @param news
     */
    public void setItems(List<NewsModel> news) {
        this.mSliderItems = news;
    }
}