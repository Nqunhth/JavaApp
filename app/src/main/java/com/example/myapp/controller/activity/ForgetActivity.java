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
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.model.user.UserModel;
import com.example.myapp.utils.Fetcher;
import com.example.myapp.utils.InputValidator;
import com.example.myapp.utils.JSONHandler;
import com.example.myapp.utils.Listener;
import com.example.myapp.utils.MyResult;
import com.example.myapp.utils.ShareIP;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ForgetPassword Activity
 */
public class ForgetActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private TextInputEditText emailText;
    private AppCompatButton sendBtn;
    private String apiUrl = "http://" + ShareIP.getIp() + "/da1_courseadviser/LogIn-SignUp/forgetpassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        //call UI object
        backBtn = findViewById(R.id.forgetBackBtn);
        emailText = findViewById(R.id.forgetEmailText);
        sendBtn = findViewById(R.id.forgetConfirmBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // send mail to recovery password function
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email;

                email = String.valueOf(emailText.getText());

                if (!(email.isEmpty())) {
                    if (InputValidator.validateEmail(email)) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[1];
                                field[0] = "email";
                                String[] data = new String[1];
                                data[0] = email;
                                PutData putData = new PutData(apiUrl, "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.contains("Password recovery link sent")) {
                                            Toast.makeText(getApplicationContext(), "Liên kết khôi phục đã được gửi đến mail của bạn", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Email phải có dạng <username@gmail.com>", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
