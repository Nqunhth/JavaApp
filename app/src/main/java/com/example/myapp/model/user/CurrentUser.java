package com.example.myapp.model.user;

import com.example.myapp.utils.ShareIP;

/**
 * model for Current User
 */
public class CurrentUser {
    private static UserModel currentUser = new UserModel();

    static public void setCurrentUser(UserModel s){
        currentUser = s;
    }

    static public UserModel getCurrentUser(){
        return currentUser;
    }

    static public void setFullname(String fullname){currentUser.setFullname(fullname);}
    static public void setLocation(String location){currentUser.setLocation(location);}
    static public void setAvatar(String imageURL){currentUser.setAvatar(imageURL);}
    static public void setBirthday(String date){currentUser.setBirthday(date);}
}
