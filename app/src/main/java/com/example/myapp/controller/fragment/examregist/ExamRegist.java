package com.example.myapp.controller.fragment.examregist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapp.R;
import com.example.myapp.utils.InputValidator;
import com.example.myapp.utils.ShareIP;
import com.example.myapp.utils.TextFilter;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


public class ExamRegist extends Fragment {

    final Calendar c = Calendar.getInstance();
    private TextView tv_datepick;
    private TextView tv_timepick;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_exam_regist, container, false);

        //call UI object
        Spinner sp_purpose = view.findViewById(R.id.exam_regist_purpose);
        Spinner sp_course = view.findViewById(R.id.exam_regist_course);
        tv_datepick = view.findViewById(R.id.exam_regist_datepick);
        tv_timepick = view.findViewById(R.id.exam_regist_timepick);

        TextInputEditText ed_name = view.findViewById(R.id.exam_regist_name);
        ed_name.setFilters(new InputFilter[]{TextFilter.getEditTextFilter()});
        TextInputEditText ed_email = view.findViewById(R.id.exam_regist_email);

        AppCompatButton b_confirm = view.findViewById(R.id.exam_regist_confirm_btn);

        ImageView iv = view.findViewById(R.id.exam_regist_backicon);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });

        //setup Spinner
        setUpSpinner(sp_purpose, R.array.exam_rg_purpose_1);
        setUpSpinner(sp_course, R.array.exam_rg_course_1);

        //exam regist function
        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String date, time, senderName, senderEmail, appointPurpose, concern;
                date = String.valueOf(tv_datepick.getText());
                time = String.valueOf(tv_timepick.getText());
                senderName = String.valueOf(ed_name.getText());
                senderEmail = String.valueOf(ed_email.getText());
                appointPurpose = refineInput(String.valueOf(sp_purpose.getSelectedItem()));
                concern = String.valueOf(sp_course.getSelectedItem());

                Log.e("input", appointPurpose + " " + concern + " " + date + " " + time + " " + senderName + " " + senderEmail);

                if (!(date.isEmpty()) && !(time.isEmpty()) && !(senderName.isEmpty()) && !(senderEmail.isEmpty()) && !(appointPurpose.isEmpty()) && !(concern.isEmpty())) {
                    if(InputValidator.validateExtendInput(4, 20, senderName)){
                        if (InputValidator.validateEmail(senderEmail)) {
                            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            try {
                                if (InputValidator.timeAfter(dt.format(new Date().getTime()), date + " " + time + ":00")) {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Starting Write and Read data with URL
                                            //Creating array for parameters
                                            String[] field = new String[6];
                                            field[0] = "date";
                                            field[1] = "time";
                                            field[2] = "appoint_purpose";
                                            field[3] = "concern";
                                            field[4] = "sender_name";
                                            field[5] = "sender_email";
                                            //Creating array for data
                                            String[] data = new String[6];
                                            data[0] = date;
                                            data[1] = time;
                                            data[2] = appointPurpose;
                                            data[3] = concern;
                                            data[4] = senderName;
                                            data[5] = senderEmail;
                                            PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/Appointment/MakeAppointment.php", "POST", field, data);
                                            if (putData.startPut()) {
                                                if (putData.onComplete()) {
                                                    String result = putData.getResult();
                                                    if (result.contains("Upload Success")) {
                                                        Toast.makeText(getActivity(), "Đặt lịch hẹn thành công. Vui lòng kiểm tra email của quý khách", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                } else
                                    Toast.makeText(getActivity(), "Vui lòng chọn thời gian trong tương lai", Toast.LENGTH_SHORT).show();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Email phải có dạng <username@gmail.com>", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Mục Họ Tên phải có tối thiểu 4 kí tự và tối đa 20 kí tự", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_datepick.setOnClickListener(new View.OnClickListener() {
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
                                try {
                                    if (InputValidator.timeWeekdays(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + " 00:00:00"))
                                        tv_datepick.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    else
                                        Toast.makeText(getActivity(), "Chúng tôi chỉ cung cấp dịch vụ từ Thứ Hai đến Thứ Bảy", Toast.LENGTH_SHORT).show();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        tv_timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                try {
                                    if (InputValidator.timeBetween("08:00", "21:00", hourOfDay + ":" + minute)) {
                                        tv_timepick.setText(hourOfDay + ":" + minute);
                                    } else {
                                        Toast.makeText(getActivity(), "Chúng tôi chỉ cung cấp dịch vụ từ 8 đến 21 giờ", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                ;
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });


        return view;
    }

    private void back(View v) {
        getFragmentManager().popBackStack();
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
     * translate input String from VN to EN
     * @param raw
     * @return
     */
    private String refineInput(String raw) {
        switch (raw) {
            case "Thi thử/ Tư vấn":
                return "placement/advise";
            case "Thi xếp lớp":
                return "mock_test";
            case "Chỉ tư vấn":
                return "advise";
            default:
                return "none";
        }
    }

}