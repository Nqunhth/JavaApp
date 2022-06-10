package com.example.myapp.utils;

import androidx.appcompat.widget.SwitchCompat;

import com.example.myapp.model.news.NewsModel;
import com.example.myapp.model.qa.QaModel;
import com.example.myapp.model.user.UserModel;

public interface FragMethodToAdapter {
    public void updateQa(QaModel qa);
    public void callDialog(String itemId);
    public void updateNews(NewsModel news);
    public void callDialog(UserModel item, SwitchCompat object);
}
