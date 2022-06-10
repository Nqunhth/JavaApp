package com.example.myapp.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.controller.fragment.qa.QaCreate;
import com.example.myapp.controller.fragment.qa.QaFilter;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.model.qa.QaModel;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.utils.FragMethodToAdapter;
import com.example.myapp.utils.ShareIP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Adapter for All Qa list
 */
public class QaListRVAdapter extends RecyclerView.Adapter<QaListRVAdapter.ViewHolder>{
    private Context context;
    private List<QaModel> rvItems;
    private FragMethodToAdapter listener;

    //Constructor
    public QaListRVAdapter(Context context, ArrayList<QaModel> rvItems, FragMethodToAdapter listener){
        this.context=context;
        this.rvItems=rvItems;
        this.listener = listener;
    }

    //Build List
    @Override
    public QaListRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qa_list, parent,false);
        AppCompatButton edittv = inflate.findViewById(R.id.qa_item_edit);
        AppCompatButton deltv = inflate.findViewById(R.id.qa_item_del);
        if(!CurrentUser.getCurrentUser().getPosition().equals("employee")){
            edittv.setVisibility(GONE);
            deltv.setVisibility(GONE);
        }
        else {
            edittv.setVisibility(View.VISIBLE);
            deltv.setVisibility(View.VISIBLE);
        }

        return new QaListRVAdapter.ViewHolder(inflate);
    }

    //Build List
    @Override
    public void onBindViewHolder(QaListRVAdapter.ViewHolder viewHolder, final int position){
        QaModel qa = rvItems.get(position);
        viewHolder.q_content.setText(qa.getQaQ());
        viewHolder.a_content.setText(qa.getQaA());
        viewHolder.del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.callDialog(qa.getQaId());
            }
        });

        viewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.updateQa(qa);
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
        TextView q_content, a_content;
        AppCompatButton edit_btn, del_btn;

        public ViewHolder(View itemView) {
            super(itemView);

            q_content = itemView.findViewById(R.id.qa_item_q_content);
            a_content = itemView.findViewById(R.id.qa_item_a_content);
            edit_btn = itemView.findViewById(R.id.qa_item_edit);
            del_btn = itemView.findViewById(R.id.qa_item_del);


            //Enabling scrolling on TextView.
            a_content.setMovementMethod(new ScrollingMovementMethod());
            q_content.setMovementMethod(new ScrollingMovementMethod());

            itemView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    itemView.findViewById(R.id.qa_item_a_content).getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            });

            //Enabling Scrolling on Input
            a_content.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            q_content.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            this.itemView = itemView;
        }
    }

    /**
     * set List QA to List
     * @param qa
     */
    public void setItems(List<QaModel> qa) {
        this.rvItems = qa;
    }

    /**
     * search QA Item in List
     * @param filterllist
     */
    public void filterList (List<QaModel> filterllist) {
        rvItems = filterllist;
        notifyDataSetChanged();
    }
}
