package com.example.myapp.controller.fragment.user;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapp.R;
import com.example.myapp.controller.fragment.news.NewsList;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.model.user.UserModel;

public class UserMenu extends DialogFragment{
    Context mContext;
    public UserMenu()
    {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_user_dialog_menu, new LinearLayout(getActivity()), false);

        // call UI object
        AppCompatButton b_personal = view.findViewById(R.id.user_menu_personal);
        AppCompatButton b_listuser = view.findViewById(R.id.user_menu_listuser);
        b_listuser.setVisibility(View.GONE);
        AppCompatButton b_listnews = view.findViewById(R.id.user_menu_listnews);
        b_listnews.setVisibility(View.GONE);
        ImageView iv_back = view.findViewById(R.id.user_menu_backicon);
        AppCompatButton b_logout = view.findViewById(R.id.user_menu_logout);

        ImageView iv_avatar = view.findViewById(R.id.user_menu_avatar);
        TextView tv_position = view.findViewById(R.id.user_menu_position);
        TextView tv_username = view.findViewById(R.id.user_menu_username);

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);

        //check position
        if(CurrentUser.getCurrentUser().getPosition().equals("manager")){
            b_listuser.setVisibility(View.VISIBLE);
            b_listnews.setVisibility(View.GONE);
        }
        if(CurrentUser.getCurrentUser().getPosition().equals("employee")) {
            b_listnews.setVisibility(View.VISIBLE);
            b_listuser.setVisibility(View.GONE);
        }
        tv_username.setText(CurrentUser.getCurrentUser().getUsername());
        String tempPosition = CurrentUser.getCurrentUser().getPosition();
        if(tempPosition.equals("manager"))
            tv_position.setText("Quản lý");
        else
            tv_position.setText("Nhân viên");
            // handle avater with Glide
            Glide.with(view)
                    .load(CurrentUser.getCurrentUser().getAvatar())
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .circleCrop()
                    .into(iv_avatar);

        b_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replace(new UserDetail(true));
                builder.dismiss();
            }
        });
        b_listuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replace(new MainUser());
                builder.dismiss();
            }
        });
        b_listnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replace(new NewsList());
                builder.dismiss();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.setCurrentUser(new UserModel());
                builder.dismiss();
                mContext = getActivity();
                ((Activity) mContext).finish();
            }
        });
        return builder;
    }

    /**
     * setup dialog
     */
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        getDialog().getWindow().setGravity(Gravity.START);
    }

    /**
     * replace fragment
     * @param fragment
     */
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment);
        transaction.addToBackStack("user_menu");
        transaction.commit();
    }

}
