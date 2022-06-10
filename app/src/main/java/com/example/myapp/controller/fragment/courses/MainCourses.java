package com.example.myapp.controller.fragment.courses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapp.R;
import com.google.android.material.imageview.ShapeableImageView;


public class MainCourses extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_courses, container, false);
        //call UI object
        ShapeableImageView ib_1 = view.findViewById(R.id.course_card_1);
        ShapeableImageView ib_2 = view.findViewById(R.id.course_card_2);
        ShapeableImageView ib_3 = view.findViewById(R.id.course_card_3);


        // Card on click - to course IELTS
        ib_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                replace(new CourseIelts());
            }
        });
        // Card on click - to course For Kid
        ib_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                replace(new CourseForKid());
            }
        });
        // Card on click - to course For Adult
        ib_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                replace(new CourseForAdult());
            }
        });

        return view;
    }

    /**
     * replace fragment
     * @param fragment
     */
    private void replace(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment,fragment,"tag");
        transaction.addToBackStack("main_course");
        transaction.commit();
    }
}