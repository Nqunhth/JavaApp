package com.example.myapp.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.model.teacher.TeacherModel;

import java.util.ArrayList;
import java.util.List;
/**
 * Adapter for Intro Teacher list
 */
public class IntroTeacherRVAdapter extends RecyclerView.Adapter<IntroTeacherRVAdapter.ViewHolder> {

    private Context context;
    private List<TeacherModel> rvItems;

    //Constructor
    public IntroTeacherRVAdapter(Context context, ArrayList<TeacherModel> rvItems){
        this.context = context;
        this.rvItems = rvItems;
    }
    //Constructor
    public IntroTeacherRVAdapter(Context context){
        this.context = context;
    }

    //Build List
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intro_teacher, parent, false);
        return new ViewHolder(inflate);
    }
    //Build List
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TeacherModel teacher = rvItems.get(position);

        //setup Item color based on Item Position
        if(position %2 == 1) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.teacher_avatar.setBackgroundResource(R.drawable.teacher_placeholder_odd);
            viewHolder.teacher_title.setTextColor(Color.parseColor("#A7A7A7"));
            if(!teacher.getTeacherImg().isEmpty())
                // handle image with Glide
                Glide.with(viewHolder.itemView)
                    .load(teacher.getTeacherImg())
                    .placeholder(R.drawable.teacher_placeholder_odd)
                    .error(R.drawable.teacher_placeholder_odd)
                    .centerCrop()
                    .into(viewHolder.teacher_avatar);
        }
        else {
            viewHolder.teacher_avatar.setBackgroundResource(R.drawable.teacher_placeholder_even);
            if(!teacher.getTeacherImg().isEmpty())
                // handle image with Glide
                Glide.with(viewHolder.itemView)
                        .load(teacher.getTeacherImg())
                        .placeholder(R.drawable.teacher_placeholder_even)
                        .error(R.drawable.teacher_placeholder_even)
                        .centerCrop()
                        .into(viewHolder.teacher_avatar);
        }

        if(teacher.getTeacherGender().equals("female")) {
            viewHolder.teacher_gender.setText("Mrs. ");
        }
        else {
            viewHolder.teacher_gender.setText("Mr. ");
        };

        viewHolder.teacher_name.setText(teacher.getTeacherName());
        viewHolder.teacher_title.setText(teacher.getTeacherAchieve());
        viewHolder.teacher_field.setText(teacher.getChargeOf());
        viewHolder.teacher_content.setText(teacher.getTeacherDescription());

    }


    @Override
    public int getItemCount() {
        return rvItems.size();
    }
    //Build List
    class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView teacher_gender;
        ImageView teacher_avatar;
        TextView teacher_name;
        TextView teacher_title;
        TextView teacher_field;
        TextView teacher_content;

        public ViewHolder(View itemView) {
            super(itemView);
            teacher_gender = itemView.findViewById(R.id.intro_teacher_gender);
            teacher_avatar = itemView.findViewById(R.id.intro_teacher_avatar);
            teacher_name = itemView.findViewById(R.id.intro_teacher_name);
            teacher_title = itemView.findViewById(R.id.intro_teacher_title);
            teacher_field = itemView.findViewById(R.id.intro_teacher_field);
            teacher_content = itemView.findViewById(R.id.intro_teacher_content);
            this.itemView = itemView;
        }

    }

    /**
     * set ListTeacher for List
     * @param teachers
     */
    public void setItems(List<TeacherModel> teachers) {
        this.rvItems = teachers;
    }
}
