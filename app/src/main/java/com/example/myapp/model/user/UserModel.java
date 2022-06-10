package com.example.myapp.model.user;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * model for User
 */
public class UserModel {
    String accountId, username, password, position, email, status, created_date, fullname, birthday, location, avatar;

    /**
     * set empty constructor for UserModel
     */
    public UserModel(){
        this.accountId = "";
        this.username =  "";
        this.password = "";
        this.position = "";
        this.email = "";
        this.status = "";
        this.created_date = "";
        this.fullname = "";
        this.birthday = "";
        this.location = "";
        this.avatar = "";
    }

    /**
     * set data constructor for UserModel
     * @param accountId
     * @param username
     * @param password
     * @param position
     * @param email
     * @param status
     * @param created_date
     * @param fullname
     * @param birthday
     * @param location
     * @param avatar
     */
    public UserModel(String accountId, String username, String password, String position, String email, String status, String created_date, String fullname, String birthday, String location, String avatar) {
        this.accountId = accountId;
        this.username =  username;
        this.password = password;
        this.position = position;
        this.email = email;
        this.status = status;
        this.created_date = created_date;
        this.fullname = fullname;
        this.birthday = birthday;
        this.location = location;
        this.avatar = avatar;
    }

    /**
     * set data constructor for UserModel by using jsonObject
     * @param jsonObject
     * @throws JSONException
     */
    public UserModel(JSONObject jsonObject) throws JSONException {
        this.accountId = jsonObject.get("account_id").toString();
        this.username =  jsonObject.get("username").toString();
        this.password = jsonObject.get("password").toString();
        this.position = jsonObject.get("position").toString();
        this.email = jsonObject.get("email").toString();
        this.status = jsonObject.get("status").toString();
        this.created_date = jsonObject.get("created_date").toString();
        this.fullname= jsonObject.get("name").toString();
        this.birthday = jsonObject.get("birthday").toString();formatBirthday();
        this.location = jsonObject.get("location").toString();
        this.avatar = jsonObject.get("avatar").toString();
    }
    public String getAccountId(){
        return this.accountId;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getPosition(){
        return this.position;
    }
    public String getEmail(){
        return this.email;
    }
    public String getStatus(){
        return this.status;
    }
    public String getCreatedDate(){
        return this.created_date;
    }
    public String getFullname(){
        return this.fullname;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public String getLocation(){
        return this.location;
    }
    public String getAvatar(){return this.avatar;}

    public void setFullname(String fullname){this.fullname = fullname;}
    public void setLocation(String location){this.location = location;}
    public void setAvatar(String imageURL){this.avatar = imageURL;}
    public void setBirthday(String date){this.birthday = date;}

    /**
     * format field birthday string to date
     */
    private void formatBirthday(){
        SimpleDateFormat rawFormat = new SimpleDateFormat("yyyy-dd-MM");
        try {
            Date temp = rawFormat.parse(this.birthday);
            SimpleDateFormat refinedFormat = new SimpleDateFormat("dd-MM-yyyy");
            this.birthday = refinedFormat.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
