package com.example.myapp.controller.fragment.user;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp.R;
import com.example.myapp.utils.InputValidator;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.utils.TextFilter;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class UserRegist extends DialogFragment {
    //private View pic;
    Context mContext;

    public UserRegist() {
        this.mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_user_dialog_regist, new LinearLayout(getActivity()), false);
        ImageView iv = view.findViewById(R.id.user_regist_backicon);
        Spinner sp_pos = view.findViewById(R.id.user_regist_spinner);
        setUpSpinner(sp_pos, R.array.user_rg_position_1);

        //call UI object
        AppCompatButton confirmBtn = view.findViewById(R.id.mn_regist_confirm_btn);
        TextInputEditText textInputEditTextUsername = view.findViewById(R.id.mn_regist_username_text);
        textInputEditTextUsername.setFilters(new InputFilter[]{TextFilter.getNoSpaceFilter()});
        TextInputEditText textInputEditTextPassword = view.findViewById(R.id.mn_regist_password_text);
        TextInputEditText textInputEditTextEmail = view.findViewById(R.id.mn_regist_email_text);
        Spinner textSpinnerPosition = view.findViewById(R.id.user_regist_spinner);

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        // User regist function
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password, position, email, status;
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());

                // translate User position
                if (String.valueOf(textSpinnerPosition.getSelectedItem()).compareTo("Nhân viên") == 0) {
                    position = "employee";
                } else {
                    position = "manager";
                }
                if (!(username.isEmpty()) && !(password.isEmpty()) && !(email.isEmpty()) && !(position.isEmpty())) {
                    if (InputValidator.validateEmail(email)) {
                        if (InputValidator.validatePassword(password)) {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[4];
                                    field[0] = "username";
                                    field[1] = "password";
                                    field[2] = "position";
                                    field[3] = "email";
                                    //Creating array for data
                                    String[] data = new String[4];
                                    data[0] = username;
                                    data[1] = password;
                                    data[2] = position;
                                    data[3] = email;
                                    PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/LogIn-SignUp/signup.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if (result.contains("Account saved. Confirmation sent")) {
                                                Toast.makeText(getActivity(), "Tạo tài khoản mới thành công.", Toast.LENGTH_LONG).show();
                                                refresh();
                                                builder.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Mật khẩu phải có ít nhất 8 kí tự với ít nhất 1 chữ số, 1 chữ cái hoa, 1 chữ cái thường và không kí tự trắng hay kí tự đặt biệt", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Email phải có dạng <username@gmail.com>", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                }
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
        params.width = 1000;
        params.height = 1600;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    /**
     * setup Spinner
     * @param sp
     * @param resourse_array
     */
    private void setUpSpinner(Spinner sp, int resourse_array) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                resourse_array, android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * refresh fragment
     */
    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, new MainUser());
        transaction.commit();
    }
}