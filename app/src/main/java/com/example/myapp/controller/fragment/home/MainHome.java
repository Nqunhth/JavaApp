package com.example.myapp.controller.fragment.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;
import com.example.myapp.controller.fragment.examregist.ExamRegist;
import com.example.myapp.controller.fragment.userbar.UserBar;
import com.google.android.material.button.MaterialButton;

public class MainHome extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

//        MaterialButton b = view.findViewById(R.id.regist_exam_button);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replace(new ExamRegist());
//            }
//        });

        return view;
    }

    /**
     * replace fragment
     * @param fragment
     */
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment);
        transaction.addToBackStack("exam_regist");
        transaction.commit();
    }
}