package com.example.myapp.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapp.R;
import com.example.myapp.model.teacher.TeacherModel;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.model.user.UserModel;
import com.example.myapp.utils.Fetcher;
import com.example.myapp.utils.JSONHandler;
import com.example.myapp.utils.Listener;
import com.example.myapp.utils.MyResult;
import com.example.myapp.utils.ShareIP;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Login Activity
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;
    private ImageButton loginBtn, cancelBtn;
    private AppCompatButton forgetBtn;
    private String apiUrl;
    private int success = 0;
    private JSONArray restfulJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //call UI object
        textInputEditTextUsername = findViewById(R.id.loginUsernameText);
        textInputEditTextPassword = findViewById(R.id.loginPasswordText);
        loginBtn = findViewById(R.id.loginConfirmBtn);
        cancelBtn = findViewById(R.id.loginCancelBtn);
//        forgetBtn = findViewById(R.id.loginForgetBtn);

        apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/LogIn-SignUp/login.php";

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        forgetBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ForgetActivity.class);
//                startActivity(intent);
//            }
//        });

        //login function
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password;

                //check input
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if(!(username.isEmpty()) && !(password.isEmpty())){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData(apiUrl, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                        String currentLink = "http://" + ShareIP.getIp() + "/da1_courseadviser/Account/accountcontroller.php?view=single&username=" + username;
                                        Fetcher.fetchDataFrom(currentLink, new Listener<String>() {
                                            @Override
                                            public void on(String result) {
                                                MyResult temp = JSONHandler.turnToJSONObject("accounts" ,result);
                                                success = temp.getStatus();
                                                restfulJsonArray = temp.getJSONArray();

                                                if (success == 1) {
                                                    if (null != restfulJsonArray) {
                                                        try {
                                                            JSONObject jsonObject = restfulJsonArray.getJSONObject(0);
                                                            UserModel curr = new UserModel(jsonObject);
                                                            CurrentUser.setCurrentUser(curr);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                        //Open Account user homepage
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


