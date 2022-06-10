package com.example.myapp.controller.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
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
import com.example.myapp.utils.FragMethodToAdapter;
import com.example.myapp.utils.ShareIP;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;
/**
 * Adapter for All News list
 */
public class NewsListRVAdapter extends RecyclerView.Adapter<NewsListRVAdapter.ViewHolder>{
    private Context context;
    private List<NewsModel> rvItems;
    private FragMethodToAdapter listener;

    //Constructor
    public NewsListRVAdapter(Context context, ArrayList<NewsModel> rvItems, FragMethodToAdapter listener){
        this.context=context;
        this.rvItems=rvItems;
        this.listener = listener;
    }

    //Build List
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent,false);
        AppCompatButton iv_del = inflate.findViewById(R.id.news_item_delete);
        if(!CurrentUser.getCurrentUser().getPosition().equals("employee"))
            iv_del.setVisibility(View.GONE);
        else
            iv_del.setVisibility(View.VISIBLE);
        return new ViewHolder(inflate);
    }
    //Build List
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position){
        NewsModel news = rvItems.get(position);
        if(!news.getPostImg().isEmpty())
            //handle News image with Gilde
            Glide.with(viewHolder.itemView)
                    .load(news.getPostImg())
                    .placeholder(R.drawable.news_list_placeholder)
                    .error(R.drawable.news_list_placeholder)
                    .centerCrop()
                    .into(viewHolder.news_image);
        viewHolder.news_title.setText(news.getPostTitle());
        viewHolder.news_author.setText(news.getAuthorName());
        viewHolder.news_date.setText(news.getPostUpdate());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CurrentUser.getCurrentUser().getPosition().equals("employee"))
                    replace(new NewsDetail(news));
                else
                    replace(new NewsCreate("Cập nhập bài đăng", news));
            }
        });
        viewHolder.news_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.callDialog(news.getPostId());
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
        TextView news_author;
        TextView news_date;
        AppCompatButton news_del;

        public ViewHolder(View itemView) {
            super(itemView);
            news_image = itemView.findViewById(R.id.news_item_image_list);
            news_title = itemView.findViewById(R.id.news_item_title_list);
            news_author = itemView.findViewById(R.id.news_item_author_list);
            news_date = itemView.findViewById(R.id.news_item_date_list);
            news_del = itemView.findViewById(R.id.news_item_delete);

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
     * set List News to List
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
