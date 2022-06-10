package com.example.myapp.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.controller.fragment.news.NewsCreate;
import com.example.myapp.controller.fragment.news.NewsDetail;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.model.user.CurrentUser;

import java.util.ArrayList;
import java.util.List;
/**
 * Adapter for News General list
 */
public class NewsGeneralRVAdapter extends RecyclerView.Adapter<NewsGeneralRVAdapter.ViewHolder>{
    private Context context;
    private List<NewsModel> rvItems;

    //Constructor
    public NewsGeneralRVAdapter(Context context, ArrayList<NewsModel> rvItems){
        this.context=context;
        this.rvItems=rvItems;
    }

    //Build List
    @Override
    public NewsGeneralRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_general,parent,false);
        return new NewsGeneralRVAdapter.ViewHolder(inflate);
    }

    //Build List
    @Override
    public void onBindViewHolder(NewsGeneralRVAdapter.ViewHolder viewHolder, final int position){
        NewsModel news = rvItems.get(position);
        if(!news.getPostImg().isEmpty())
            // handle News image with Glide
            Glide.with(viewHolder.itemView)
                    .load(news.getPostImg())
                    .placeholder(R.drawable.news_general_placeholder)
                    .error(R.drawable.news_general_placeholder)
                    .centerCrop()
                    .into(viewHolder.news_image);
        viewHolder.news_title.setText(news.getPostTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CurrentUser.getCurrentUser().getPosition().equals("employee"))
                    replace(new NewsDetail(news));
                else
                    replace(new NewsCreate("Cập nhập bài đăng", news));
            }
        });
    }

    @Override
    public int getItemCount(){
        return rvItems.size();
    }

    //Build List
    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView news_image;
        TextView news_title;

        public ViewHolder(View itemView) {
            super(itemView);
            news_image = itemView.findViewById(R.id.news_item_image_general);
            news_title = itemView.findViewById(R.id.news_item_title_general);
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
        transaction.addToBackStack("news_general");
        transaction.commit();
    }

    /**
     * set List News for List
     * @param news
     */
    public void setItems(List<NewsModel> news) {
        this.rvItems = news;
    }

    /**
     * search News item in List
     * @param filterllist
     */
    public void filterList (List<NewsModel> filterllist) {
        rvItems = filterllist;
        notifyDataSetChanged();
    }
}
