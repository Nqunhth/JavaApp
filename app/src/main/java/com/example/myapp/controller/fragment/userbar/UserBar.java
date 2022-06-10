package com.example.myapp.controller.fragment.userbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.controller.activity.LoginActivity;
import com.example.myapp.controller.fragment.user.UserDetail;
import com.example.myapp.controller.fragment.user.UserMenu;
import com.example.myapp.model.user.CurrentUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class UserBar extends Fragment{
    private View view;

    TextView tv_greeting ;
    LinearLayout l_userContainer ;
    TextView tv_name ;

    ShapeableImageView b_avatar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_user_bar, container, false);
        MaterialButton b_menu = view.findViewById(R.id.userbar_menu);
        b_avatar = view.findViewById(R.id.userbar_avatar);
        tv_greeting = view.findViewById(R.id.userbar_greeting);
        l_userContainer = view.findViewById(R.id.userbar_usercontain);
        tv_name = view.findViewById(R.id.userbar_display_name);

        // Handle avatar with Glide
        if(!CurrentUser.getCurrentUser().getAvatar().isEmpty())
            Glide.with(view)
                    .load(CurrentUser.getCurrentUser().getAvatar())
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
                    .centerCrop()
                    .circleCrop()
                    .into(b_avatar);
        if(!CurrentUser.getCurrentUser().getFullname().isEmpty()){
            tv_greeting.setVisibility(View.GONE);
            l_userContainer.setVisibility(View.VISIBLE);
            tv_name.setText(CurrentUser.getCurrentUser().getFullname());
        }

        b_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUser.getCurrentUser().getPosition() != ""){
                    UserMenu dialogFragment = new UserMenu();
                    //dialogFragment.setListener(this);
                    dialogFragment.show(getActivity().getSupportFragmentManager(),"simple dialog");
                }
                else
                    Toast.makeText(getActivity(),"Vui lòng đăng nhập để thực hiện tính năng này", Toast.LENGTH_SHORT).show();
            }
        });

        b_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUser.getCurrentUser().getUsername().isEmpty()) {
                    openLoginActivity();
                }
                else{
                    replace(new UserDetail(true));
                }
            }
        });

        return view;
    }

    /**
     * replace fragment
     * @param fragment
     */
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment);
        transaction.addToBackStack("main_user");
        transaction.commit();
    }

    /**
     * open login Activity
     */
    private void openLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


}
