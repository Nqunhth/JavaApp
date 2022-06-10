package com.example.myapp.controller.fragment.user;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.controller.fragment.qa.MainQA;
import com.example.myapp.controller.fragment.userbar.UserBar;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.model.user.UserModel;
import com.example.myapp.utils.ImgUploader;
import com.example.myapp.utils.InputValidator;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.utils.TextFilter;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class UserDetail extends Fragment {
    int SELECT_PICTURE = 200;
    private ImageView b_avatar;
    private Uri path;
    private ActivityResultLauncher<Intent> register;
    private Boolean isNewAvatar = false;
    private String imageURL;
    private Boolean isOwner = false;
    private UserModel user;

    private View view;

    private TextView b_username;
    private TextView b_position;
    private TextInputEditText b_name;
    private TextInputEditText b_birthday;
    private TextInputEditText b_place;
    private TextInputEditText b_password;

    private LinearLayout layout_pass;
    private TextView passConfirm;
    private TextInputEditText pwConfirm;

    private AppCompatButton confirmAvChange, cancelAvChange;

    final Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay;

    public UserDetail(Boolean _isOwner) {
        setIsOwner(_isOwner);
    }

    public UserDetail(UserModel _user) {
        user = _user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ImageView iv = view.findViewById(R.id.user_detail_backicon);

        layout_pass = view.findViewById(R.id.user_detail_password_layout);

        // call UI object
        b_username = view.findViewById(R.id.user_detail_username);
        b_position = view.findViewById(R.id.user_detail_position);
        b_name = view.findViewById(R.id.user_detail_name);
        b_name.setFilters(new InputFilter[]{TextFilter.getEditTextFilter()});
        b_birthday = view.findViewById(R.id.user_detail_birthday);
        b_place = view.findViewById(R.id.user_detail_place);
        b_password = view.findViewById(R.id.user_detail_password);

        passConfirm = view.findViewById(R.id.textView4);
        passConfirm.setVisibility(View.GONE);
        pwConfirm = view.findViewById(R.id.user_detail_confirm_password);
        pwConfirm.setVisibility(View.GONE);

        b_avatar = view.findViewById(R.id.user_detail_avatar);

        View v = view.findViewById(R.id.user_detail_view);
        AppCompatButton update = view.findViewById(R.id.user_detail_update_btn);
        AppCompatButton upload = view.findViewById(R.id.user_detail_upload_btn);
        AppCompatButton changePw = view.findViewById(R.id.user_detail_change_pw_btn);
        AppCompatButton confirmPwChange = view.findViewById(R.id.user_detail_confirm_pw_change_btn);
        confirmPwChange.setVisibility(View.GONE);
        AppCompatButton cancelPwChange = view.findViewById(R.id.user_detail_cancel_pw_change_btn);
        cancelPwChange.setVisibility(View.GONE);

        confirmAvChange = view.findViewById(R.id.user_detail_avatar_check_btn);
        cancelAvChange = view.findViewById(R.id.user_detail_avatar_cancel_btn);

        initInfo();

        register = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            path = data.getData();
                            b_avatar.setImageURI(path);
                            isNewAvatar = true;
                            confirmAvChange.setVisibility(View.VISIBLE);
                            cancelAvChange.setVisibility(View.VISIBLE);
                        }
                    }
                });


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(view);
            }
        });

        b_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOwner) {
                    int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        register.launch(i);
                    } else {
                        Toast.makeText(getActivity(), "Vui lòng cho phép ứng dụng truy cập vào bộ nhớ để sử dụng tính năng này", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusable(true);
                update.setVisibility(View.GONE);
            }
        });
        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set visibility or invisibility
                passConfirm.setVisibility(View.VISIBLE);
                pwConfirm.setVisibility(View.VISIBLE);
                changePw.setVisibility(View.GONE);
                confirmPwChange.setVisibility(View.VISIBLE);
                cancelPwChange.setVisibility(View.VISIBLE);

                b_password.setFocusable(true);
                b_password.setFocusableInTouchMode(true);
            }
        });

        // change password function
        confirmPwChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newPass, confirmPass, accountId;

                newPass = String.valueOf(b_password.getText());
                confirmPass = String.valueOf(pwConfirm.getText());
                accountId = CurrentUser.getCurrentUser().getAccountId();

                if (!(newPass.isEmpty()) && !(confirmPass.isEmpty()) && !(accountId.isEmpty())) {
                    if (InputValidator.validatePassword(newPass)) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[3];
                                field[0] = "password";
                                field[1] = "confirm";
                                field[2] = "account_id";
                                String[] data = new String[3];
                                data[0] = newPass;
                                data[1] = confirmPass;
                                data[2] = accountId;
                                PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/Account/accountcontroller.php?view=change_password", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.contains("Update Success")) {
                                            Toast.makeText(getActivity(), "Cập nhập thành công", Toast.LENGTH_SHORT).show();

                                            //set invisibility
                                            passConfirm.setVisibility(View.GONE);
                                            pwConfirm.setVisibility(View.GONE);
                                            changePw.setVisibility(View.VISIBLE);
                                            confirmPwChange.setVisibility(View.GONE);
                                            cancelPwChange.setVisibility(View.GONE);

                                            //reset input
                                            pwConfirm.setText("");
                                            b_password.setText("");
                                            b_password.setFocusable(false);
                                            b_password.setFocusableInTouchMode(false);
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
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelPwChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set invisibility
                passConfirm.setVisibility(View.GONE);
                pwConfirm.setVisibility(View.GONE);
                changePw.setVisibility(View.VISIBLE);
                confirmPwChange.setVisibility(View.GONE);
                cancelPwChange.setVisibility(View.GONE);

                //reset input
                pwConfirm.setText("");
                b_password.setText("");
                b_password.setFocusable(false);
                b_password.setFocusableInTouchMode(false);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Upload data to database
                final String name, birthday, location, userId;
                name = String.valueOf(b_name.getText());
                birthday = String.valueOf(b_birthday.getText());
                location = String.valueOf(b_place.getText());
                if (isOwner)
                    userId = CurrentUser.getCurrentUser().getAccountId();
                else
                    userId = user.getAccountId();
                uploadInfo(name, birthday, location, userId);

                setFocusable(false);
                update.setVisibility(View.VISIBLE);
            }
        });
        confirmAvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get link from imgur
                try {
                    imageURL = new ImgUploader(path, requireActivity()).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Up new link to database
                if (!(imageURL.isEmpty()) && !(CurrentUser.getCurrentUser().getAccountId().isEmpty())) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "avatar";
                            field[1] = "account_id";
                            String[] data = new String[2];
                            data[0] = imageURL;
                            data[1] = CurrentUser.getCurrentUser().getAccountId();
                            PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/Account/accountcontroller.php?view=avatar", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.contains("Update Success")) {
                                        CurrentUser.setAvatar(imageURL);
                                        confirmAvChange.setVisibility(view.GONE);
                                        cancelAvChange.setVisibility(view.GONE);
                                        resetUserbar();
                                        Toast.makeText(getActivity(), "Cập nhập thành công", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Không tìm thấy hình ảnh", Toast.LENGTH_SHORT).show();
                };
            }
        });

        cancelAvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAvChange.setVisibility(view.GONE);
                cancelAvChange.setVisibility(view.GONE);
                // handle avatar with Glide
                if (!CurrentUser.getCurrentUser().getAvatar().isEmpty())
                    Glide.with(view)
                            .load(CurrentUser.getCurrentUser().getAvatar())
                            .placeholder(R.drawable.user_placeholder)
                            .error(R.drawable.user_placeholder)
                            .centerCrop()
                            .circleCrop()
                            .into(b_avatar);
                else
                    b_avatar.setImageResource(R.drawable.user_placeholder);
            }
        });


        return view;
    }

    /**
     * Upload User Info to database
     * @param name
     * @param birthday
     * @param location
     * @param userId
     */
    private void uploadInfo(String name, String birthday, String location, String userId) {
        if (!(name.isEmpty()) && !(birthday.isEmpty()) && !(location.isEmpty()) && !(userId.isEmpty())) {
            if(InputValidator.validateExtendInput(4, 20, name)){
                if(InputValidator.validateExtendInput(0, 20, location)){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "name";
                            field[1] = "birthday";
                            field[2] = "location";
                            field[3] = "account_id";
                            String[] data = new String[4];
                            data[0] = name;
                            data[1] = birthday;
                            data[2] = location;
                            data[3] = userId;
                            PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/Account/accountcontroller.php?view=update", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.contains("Update Success")) {
                                        if (isOwner) {
                                            CurrentUser.setFullname(name);
                                            CurrentUser.setBirthday(birthday);
                                            CurrentUser.setLocation(location);
                                            resetUserbar();
                                        }
                                        Toast.makeText(getActivity(), "Cập nhập thành công", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Mục Nơi Ở chỉ cho phép tối đa 20 kí tự", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getActivity(), "Mục Họ Tên phải có tối thiểu 4 kí tự và tối đa 20 kí tự", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * back to previous fragment
     * @param v
     */
    private void back(View v) {
        getFragmentManager().popBackStack();
    }

    /**
     * format user position
     * @param _position
     * @return
     */
    private String refinePosition(String _position) {
        if (_position.equals("manager"))
            return "Quản lý";
        else
            return "Nhân viên";
    }

    /**
     * unlock input fields
     * @param b
     */
    private void setFocusable(Boolean b) {
        b_name.setFocusable(b);
        b_name.setFocusableInTouchMode(b);
        b_birthday.setFocusable(false);
        b_birthday.setFocusableInTouchMode(false);
        b_place.setFocusable(b);
        b_place.setFocusableInTouchMode(b);
        if (b) {
            b_birthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    if (LocalDate.now().getYear() - year >= 20)
                                        b_birthday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    else
                                        Toast.makeText(getActivity(), "Ngày sinh không hợp lệ. Người dùng phải từ 20 tuổi trở lên", Toast.LENGTH_LONG).show();
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        }
    }

    /**
     * initialize info
     */
    private void initInfo() {
        if (!isOwner) {
            layout_pass.setVisibility(View.GONE);
            setInfo(user);
        } else {
            setInfo(CurrentUser.getCurrentUser());
        }
    }

    /**
     * set User Info
     * @param user
     */
    private void setInfo(UserModel user) {
        b_username.setText(user.getUsername());
        b_position.setText(refinePosition(user.getPosition()));
        b_name.setText(user.getFullname());
        b_birthday.setText(user.getBirthday());
        b_place.setText(user.getLocation());
        if (!user.getAvatar().isEmpty())
            Glide.with(view)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
                    .centerCrop()
                    .circleCrop()
                    .into(b_avatar);
    }

    boolean isOpened = false;

    /**
     * set is Owner
     * @param b
     */
    public void setIsOwner(Boolean b) {
        this.isOwner = b;
    }

    /**
     * reset the UserBar
     */
    private void resetUserbar() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.userbar, new UserBar());
        transaction.commit();
    }
}