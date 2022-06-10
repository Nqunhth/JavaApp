package com.example.myapp.controller.fragment.intro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.myapp.R;
import com.example.myapp.controller.adapter.FragmentSliderAdapter;
import com.example.myapp.controller.fragment.courses.MainCourses;
import com.example.myapp.controller.fragment.navigation.BottomNavigation;
import com.google.android.material.button.MaterialButton;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;


public class MainIntro extends Fragment {

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private LinearLayout dots_scrollbar_holder;
    private List<Fragment> list;
    TextView[] dots;
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_intro, container, false);
        MaterialButton b_next = view.findViewById(R.id.intro_next_button);

        layout = view.findViewById(R.id.dots_container);
        dots = new TextView[5];

        //setup List Fragment
        list = new ArrayList<>();
        list.add(new IntroWho());
        list.add(new IntroWhy());
        list.add(new IntroTeacher());
        list.add(new IntroFacility());
        list.add(new IntroStudent());
        mPager = (ViewPager) view.findViewById(R.id.fragment_container);
        //Build Adapter
        pagerAdapter = new FragmentSliderAdapter(getFragmentManager() ,list);
        setIndicators();
        selectedDots(0);
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        //imple onChange Item in List
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) { }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                selectedDots(position);
                if(position == 4){
                    b_next.setText(R.string.intro_end);
                }
            }
        });
        b_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mPager.getCurrentItem() == 4){
                    replace(new MainCourses());
                } else {
                    mPager.setCurrentItem(getItem(+1), true);
                }
            }
        });
        return view;
    }

    /**
     * handle selected Dots
     * @param position
     */
    private void selectedDots(int position) {
        for (int i = 0; i < dots.length; i++) {
            if (i == position) {
                dots[i].setTextColor(getResources().getColor(R.color.sunrise_1));
            } else {
                dots[i].setTextColor(getResources().getColor(R.color.normal_indicator));
            }
        }
    }

    /**
     * set Indicators (Dots) length
     */
    private void setIndicators() {
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this.getContext());
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(28);
            layout.addView(dots[i]);
        }
    }

    /**
     * get Item in List
     * @param i
     * @return
     */
    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
    }

    /**
     * Replace fragment
     * @param fragment
     */
    private void replace(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment,fragment);
        transaction.commit();
    }
}