package com.example.myapp.controller.fragment.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.myapp.R;
import com.example.myapp.controller.fragment.qa.MainQA;
import com.example.myapp.controller.fragment.courses.MainCourses;
import com.example.myapp.controller.fragment.home.MainHome;
import com.example.myapp.controller.fragment.intro.MainIntro;
import com.example.myapp.controller.fragment.news.MainNews;

public class BottomNavigation extends Fragment {

    private final int ID_Home = 1;
    private final int ID_News = 2;
    private final int ID_Intro = 3;
    private final int ID_Course = 4;
    private final int ID_QA = 5;
    public MeowBottomNavigation bottomNavigation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        //setup BottomNavigation item
        bottomNavigation = view.findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Home, R.drawable.ic_home_solid));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_News, R.drawable.ic_newspaper));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Intro, R.drawable.ic_school));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Course, R.drawable.ic_course));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_QA, R.drawable.ic_quora));
        replace(new MainHome());

        //handle onItemClick
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case ID_Home:
                        bottomNavigation.show(ID_Home,true);
                        Toast.makeText(getContext(), "Trang chủ",Toast.LENGTH_SHORT).show();
                        replace(new MainHome());
                        break;
                    case ID_News:
                        bottomNavigation.show(ID_News,true);
                        Toast.makeText(getContext(), "Trang tin tức",Toast.LENGTH_SHORT).show();
                        replace(new MainNews());
                        break;
                    case ID_Intro:
                        bottomNavigation.show(ID_Intro,true);
                        Toast.makeText(getContext(), "Trang giới thiệu trung tâm",Toast.LENGTH_SHORT).show();
                        replace(new MainIntro());
                        break;
                    case ID_Course:
                        bottomNavigation.show(ID_Course,true);
                        Toast.makeText(getContext(), "Trang giới thiệu khóa học",Toast.LENGTH_SHORT).show();
                        replace(new MainCourses());
                        break;
                    case ID_QA:
                        bottomNavigation.show(ID_QA,true);
                        Toast.makeText(getContext(), "Trang hỏi đáp",Toast.LENGTH_SHORT).show();
                        replace(new MainQA());
                        break;
                }
            }
        });
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) { }
        });
        bottomNavigation.show(ID_Home, true);
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
                Toast.makeText(getContext(), item.getId()+" is reselected",Toast.LENGTH_SHORT).show();
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
        transaction.replace(R.id.host_fragment,fragment);
        transaction.commit();
    }
}
