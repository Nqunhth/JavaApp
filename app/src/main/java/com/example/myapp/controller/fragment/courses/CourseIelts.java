package com.example.myapp.controller.fragment.courses;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp.R;

public class CourseIelts extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_course_ielts, container, false);
        // go back function
        ImageView iv = view.findViewById(R.id.course_ielts_backicon);
        iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
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