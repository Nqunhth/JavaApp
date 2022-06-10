package com.example.myapp.controller.fragment.qa;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/**
 * Qa filter dialog
 */
public class QaFilter extends DialogFragment {
    //private View pic;
    Context mContext;
    Chip c_main_1;
    Chip c_main_2;
    Chip c_sub_11;
    Chip c_sub_12;
    Chip c_sub_13;
    Chip c_sub_14;
    Chip c_sub_15;
    Chip c_sub_21;
    Chip c_sub_22;
    Chip c_sub_23;
    ArrayList<String> filter_arr = new ArrayList<String>();

    public QaFilter() {
        mContext = getActivity();
    }

    public QaFilter(ArrayList<String> check) {
        filter_arr = check;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_qa_dialog_filter, new LinearLayout(getActivity()), false);
        ImageView iv_close = view.findViewById(R.id.qa_filter_closeicon);
        AppCompatButton b_done = view.findViewById(R.id.qa_filter_done);

        //setup chips
        c_main_1 = view.findViewById(R.id.qa_filter_main_1);
        c_main_2 = view.findViewById(R.id.qa_filter_main_2);
        c_sub_11 = view.findViewById(R.id.qa_filter_sub_11);
        c_sub_12 = view.findViewById(R.id.qa_filter_sub_12);
        c_sub_13 = view.findViewById(R.id.qa_filter_sub_13);
        c_sub_14 = view.findViewById(R.id.qa_filter_sub_14);
        c_sub_15 = view.findViewById(R.id.qa_filter_sub_15);
        c_sub_21 = view.findViewById(R.id.qa_filter_sub_21);
        c_sub_22 = view.findViewById(R.id.qa_filter_sub_22);
        c_sub_23 = view.findViewById(R.id.qa_filter_sub_23);
        checkonCreate();

        //chips handle
        c_main_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToArr(c_main_1.isChecked(), "center","main");
            }
        });
        c_main_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToArr(c_main_2.isChecked(), "course","main");
            }
        });
        c_sub_11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_1();
                addToArr(isChecked, "register","sub");
            }
        });
        c_sub_12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_1();
                addToArr(isChecked, "teaching","sub");
            }
        });
        c_sub_13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_1();
                addToArr(isChecked, "reservation","sub");
            }
        });
        c_sub_14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_1();
                addToArr(isChecked, "contact","sub");
            }
        });
        c_sub_15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_1();
                addToArr(isChecked, "other","sub");
            }
        });
        c_sub_21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_2();
                addToArr(isChecked, "IELTS","sub");
            }
        });
        c_sub_22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_2();
                addToArr(isChecked, "Adult Course","sub");
            }
        });
        c_sub_23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMainbySub_2();
                addToArr(isChecked, "Kid Course","sub");
            }
        });

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.dismiss();
            }
        });
        b_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
                builder.dismiss();
            }
        });
        return builder;
    }

    /**
     * refresh fragment
     */
    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, new MainQA(filter_arr));
        transaction.commit();
    }

    /**
     * imple selected chips
     */
    private void checkonCreate(){
        if(filter_arr.contains("center"))
            c_main_1.setChecked(true);
        if(filter_arr.contains("course"))
            c_main_2.setChecked(true);
        if(filter_arr.contains("register") || filter_arr.contains("teaching") || filter_arr.contains("reservation") || filter_arr.contains("contact") || filter_arr.contains("other"))
            c_main_1.setChecked(true);
        if(filter_arr.contains("IELTS") || filter_arr.contains("Adult Course") || filter_arr.contains("Kid Course"))
            c_main_2.setChecked(true);
        if(filter_arr.contains("register"))
            c_sub_11.setChecked(true);
        if(filter_arr.contains("teaching"))
            c_sub_12.setChecked(true);
        if(filter_arr.contains("reservation"))
            c_sub_13.setChecked(true);
        if(filter_arr.contains("contact"))
            c_sub_14.setChecked(true);
        if(filter_arr.contains("other"))
            c_sub_15.setChecked(true);
        if(filter_arr.contains("IELTS"))
            c_sub_21.setChecked(true);
        if(filter_arr.contains("Adult Course"))
            c_sub_22.setChecked(true);
        if(filter_arr.contains("Kid Course"))
            c_sub_23.setChecked(true);
    }
    /**
     * imple selected Main chips
     */
    private void checkMainbySub_2(){
        if(c_sub_21.isChecked() || c_sub_22.isChecked() || c_sub_23.isChecked()){
            c_main_2.setChecked(true);
            c_main_2.setClickable(false);
        }
        else{
            c_main_2.setChecked(false);
            c_main_2.setClickable(true);
        }
    }
    /**
     * imple selected Sub chips 1
     */
    private void checkMainbySub_1(){
        if(c_sub_11.isChecked() || c_sub_12.isChecked() || c_sub_13.isChecked() || c_sub_14.isChecked() || c_sub_15.isChecked()){
            c_main_1.setChecked(true);
            c_main_1.setClickable(false);
        }
        else{
            c_main_1.setChecked(false);
            c_main_1.setClickable(true);
        }
    }

    /**
     * Add selected chips to array
     * @param status
     * @param str
     * @param pos
     */
    private void addToArr(Boolean status, String str, String pos){
        if(status){
            if(pos.equals("main")){
                filter_arr.add(str);
            }
            if(pos.equals("sub")){
                filter_arr.add(str);
                if(filter_arr.contains("course"))
                    filter_arr.remove("course");
                if(filter_arr.contains("center"))
                    filter_arr.remove("center");
            }
        }
        else{
            filter_arr.remove(str);
        }
        Log.d("filter    ", filter_arr.toString() + filter_arr.size());
    }
}