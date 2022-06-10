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
import com.example.myapp.model.student.StudentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Intro ExStudent list
 */
public class IntroStudentRVAdapter extends RecyclerView.Adapter<IntroStudentRVAdapter.ViewHolder> {

    private Context context;
    private List<StudentModel> rvItems;

    //Constructor
    public IntroStudentRVAdapter(Context context, ArrayList<StudentModel> rvItems){
        this.context = context;
        this.rvItems = rvItems;
    }
    //Constructor
    public IntroStudentRVAdapter(Context context){
        this.context = context;
    }

    //Build List
    @Override
    public IntroStudentRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intro_student, parent, false);
        return new IntroStudentRVAdapter.ViewHolder(inflate);
    }

    //Build List
    @Override
    public void onBindViewHolder(IntroStudentRVAdapter.ViewHolder viewHolder, final int position) {
        StudentModel student = rvItems.get(position);

        //setup Item color based on Item Position
        if(position %2 == 1)
        {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFDF8"));
            viewHolder.student_avatar.setBackgroundResource(R.drawable.student_placeholder_odd);
            viewHolder.student_title.setTextColor(Color.parseColor("#FFAB6F"));
            if(!student.getExstImg().isEmpty())
                //Handle image with Glide
                Glide.with(viewHolder.itemView)
                        .load(student.getExstImg())
                        .placeholder(R.drawable.student_placeholder_odd)
                        .error(R.drawable.student_placeholder_odd)
                        .centerCrop()
                        .into(viewHolder.student_avatar);
        }
        else {
            viewHolder.student_avatar.setBackgroundResource(R.drawable.student_placeholder_even);
            //implement student image
            if(!student.getExstImg().isEmpty())
                //Handle image with Glide
                Glide.with(viewHolder.itemView)
                        .load(student.getExstImg())
                        .placeholder(R.drawable.student_placeholder_even)
                        .error(R.drawable.student_placeholder_even)
                        .centerCrop()
                        .into(viewHolder.student_avatar);
        }
        viewHolder.student_name.setText(student.getExstName());
        viewHolder.student_title.setText(student.getExstAchieve());
        viewHolder.student_content.setText(student.getExstQuote());



    }

    @Override
    public int getItemCount() {
        return rvItems.size();
    }

    //Build List
    class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView student_avatar;
        TextView student_name;
        TextView student_title;
        TextView student_content;

        public ViewHolder(View itemView) {
            super(itemView);
            student_avatar = itemView.findViewById(R.id.intro_student_avatar);
            student_name = itemView.findViewById(R.id.intro_student_name);
            student_title = itemView.findViewById(R.id.intro_student_title);
            student_content = itemView.findViewById(R.id.intro_student_content);
            this.itemView = itemView;
        }

    }

    /**
     * set ListStudent for List
     * @param students
     */
    public void setItems(List<StudentModel> students) {
        this.rvItems = students;
    }
}
