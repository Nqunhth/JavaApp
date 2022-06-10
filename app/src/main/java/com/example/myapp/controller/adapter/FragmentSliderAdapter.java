package com.example.myapp.controller.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


public class FragmentSliderAdapter extends FragmentStatePagerAdapter {

    //setup Fragment Slider
    private List<Fragment> fragmentList;
    public FragmentSliderAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int positon){
        return fragmentList.get(positon);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }
}