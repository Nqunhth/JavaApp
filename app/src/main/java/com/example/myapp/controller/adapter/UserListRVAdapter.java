package com.example.myapp.controller.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.controller.fragment.news.NewsDetail;
import com.example.myapp.controller.fragment.user.UserDetail;
import com.example.myapp.model.qa.QaModel;
import com.example.myapp.model.user.UserModel;
import com.example.myapp.utils.FragMethodToAdapter;
import com.example.myapp.utils.ShareIP;
import com.google.android.material.imageview.ShapeableImageView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Adapter for All User list
 */
public class UserListRVAdapter  extends RecyclerView.Adapter<UserListRVAdapter.ViewHolder>{
    private Context context;
    private List<UserModel> rvItems;
    private FragMethodToAdapter listener;

    //Constructor
    public UserListRVAdapter(Context context, ArrayList<UserModel> rvItems, FragMethodToAdapter listener){
        this.context=context;
        this.rvItems=rvItems;
        this.listener = listener;
    }

    //Build List
    @Override
    public UserListRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent,false);
        return new UserListRVAdapter.ViewHolder(inflate);
    }
    //Build List
    @Override
    public void onBindViewHolder(UserListRVAdapter.ViewHolder viewHolder, final int position){
        UserModel user = rvItems.get(position);
        viewHolder.user_name.setText(user.getFullname());
        viewHolder.user_email.setText(user.getEmail());
        viewHolder.user_date.setText(user.getCreatedDate());
        if(!user.getAvatar().isEmpty())
            //handle user avatar with Glide
            Glide.with(viewHolder.itemView)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.user_retangle_placeholder)
                    .error(R.drawable.user_retangle_placeholder)
                    .centerCrop()
                    .into(viewHolder.user_image);
        if(user.getStatus().equals("enabled"))
            viewHolder.user_switch.setChecked(true);
        else
            viewHolder.user_switch.setChecked(false);
        viewHolder.user_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.callDialog(user, viewHolder.user_switch);
            }
        });
        viewHolder.user_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new UserDetail(user));
            }
        });
    }

    @Override
    public int getItemCount(){
        return rvItems.size();
    }
        class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            ImageView user_image;
            TextView user_name;
            TextView user_email;
            TextView user_date;
            SwitchCompat user_switch;


            public ViewHolder(View itemView) {
                super(itemView);
                user_image = itemView.findViewById(R.id.user_item_image_list);
                user_name = itemView.findViewById(R.id.user_item_title_list);
                user_email = itemView.findViewById(R.id.user_item_author_list);
                user_date = itemView.findViewById(R.id.user_item_date_list);
                user_switch = itemView.findViewById(R.id.user_switch_deactive);

                this.itemView = itemView;
            }
    }
    //Build List
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment);
        transaction.addToBackStack("user_list");
        transaction.commit();
    }

    /**
     * set List User to List
     * @param user
     */
    public void setItems(List<UserModel> user) {
        this.rvItems = user;
    }

    /**
     * search User Item in List
     * @param filterllist
     */
    public void filterList (List<UserModel> filterllist) {
        rvItems = filterllist;
        notifyDataSetChanged();
    }
}