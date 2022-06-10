package com.example.myapp.controller.fragment.chatbot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatBot extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);
//        FloatingActionButton b = view.findViewById(R.id.chatbot_button);
//        b.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                TestBot bottomSheetDialogFragment = new TestBot();
//                bottomSheetDialogFragment.show(getFragmentManager(),"");
//            }
//        });

        return view;
    }
}