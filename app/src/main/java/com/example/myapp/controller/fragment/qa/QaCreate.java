package com.example.myapp.controller.fragment.qa;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.example.myapp.model.qa.QaModel;
import com.example.myapp.utils.ShareIP;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class QaCreate extends DialogFragment{
    private Context mContext;
    private QaModel qa = new QaModel();
    private Boolean isUpdate = false;

    public QaCreate()
    {
        mContext = getActivity();
    }

    /**
     * set constructor
     * @param _qa
     * @param _isUpdate
     */
    public QaCreate(QaModel _qa, Boolean _isUpdate)
    {
        mContext = getActivity();
        qa = _qa;
        isUpdate = _isUpdate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_qa_dialog_create, new LinearLayout(getActivity()), false);

        //call UI object
        ImageView iv_close = view.findViewById(R.id.qa_create_closeicon);
        Spinner sp_mainSubject = view.findViewById(R.id.qa_create_main_subject);
        Spinner sp_subSubjectA = view.findViewById(R.id.qa_create_sub_subject_a);
        Spinner sp_subSubjectB = view.findViewById(R.id.qa_create_sub_subject_b);
        setUpSpinner(sp_mainSubject,R.array.qa_main_subject);

        TextInputEditText ed_question = view.findViewById(R.id.qa_create_question);
        TextInputEditText ed_answer = view.findViewById(R.id.qa_create_answer);

        AppCompatButton postBtn = view.findViewById(R.id.qa_create_post_btn);
        AppCompatButton updateBtn = view.findViewById(R.id.qa_create_update_btn);

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        //check if updat Qa
        if(isUpdate) {
            postBtn.setVisibility(View.GONE);
            updateBtn.setVisibility(View.VISIBLE);
            sp_mainSubject.setSelection(getIndex(sp_mainSubject, revertInput(qa.getQaMainSub())));
            Log.e("main", revertInput(qa.getQaMainSub()) + " " + getIndex(sp_mainSubject, revertInput(qa.getQaMainSub())));

            ed_question.setText(qa.getQaQ());
            ed_answer.setText(qa.getQaA());
        }
        else {
            postBtn.setVisibility(View.VISIBLE);
            updateBtn.setVisibility(View.GONE);
        }

        // chips imple
        sp_mainSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = sp_mainSubject.getSelectedItem().toString();
                if (spinnerValue.equals("Trung tâm")) {
                    setUpSpinner(sp_subSubjectA,R.array.qa_center_subject_a);
                    if(isUpdate){
                        sp_subSubjectA.setSelection(getIndex(sp_subSubjectA, revertInput(qa.getQaSubSub_a())));
                    }
                    setUpSpinner(sp_subSubjectB,R.array.qa_center_subject_b);
                    sp_subSubjectB.setEnabled(false);
                }else if (spinnerValue.equals("Khóa học")){
                    setUpSpinner(sp_subSubjectA,R.array.qa_course_subject_a);
                    if(isUpdate){
                        sp_subSubjectA.setSelection(getIndex(sp_subSubjectA, revertInput(qa.getQaSubSub_a())));
                    }
                    setUpSpinner(sp_subSubjectB,R.array.qa_course_subject_b);
                    if(isUpdate){
                        sp_subSubjectB.setSelection(getIndex(sp_subSubjectB, revertInput(qa.getQaSubSub_b())));
                    }
                    sp_subSubjectB.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // create Qa function
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mainSubject, subSubjectA, subSubjectB, question, answer;
                mainSubject = refineInput(String.valueOf(sp_mainSubject.getSelectedItem()));
                subSubjectA = refineInput(String.valueOf(sp_subSubjectA.getSelectedItem()));
                subSubjectB = refineInput(String.valueOf(sp_subSubjectB.getSelectedItem()));
                question = String.valueOf(ed_question.getText());
                answer = String.valueOf(ed_answer.getText());

                if(!(mainSubject == "") && !(subSubjectA == "") && !(subSubjectB == "") && !(question == "") && !(answer == "")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "main_subject";
                            field[1] = "sub_subject_a";
                            field[2] = "sub_subject_b";
                            field[3] = "question";
                            field[4] = "answer";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = mainSubject;
                            data[1] = subSubjectA;
                            data[2] = subSubjectB;
                            data[3] = question;
                            data[4] = answer;
                            PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/QandA/qacontroller.php?view=upload", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.contains("Upload Success")){
                                        Toast.makeText(getActivity(), "Đăng tải thành công", Toast.LENGTH_LONG).show();
                                        refresh();
                                        builder.dismiss();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                };
            }
        });

        // update Qa function
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update
                final String mainSubject, subSubjectA, subSubjectB, question, answer, qaId;
                qaId = qa.getQaId();
                mainSubject = refineInput(String.valueOf(sp_mainSubject.getSelectedItem()));
                subSubjectA = refineInput(String.valueOf(sp_subSubjectA.getSelectedItem()));
                subSubjectB = refineInput(String.valueOf(sp_subSubjectB.getSelectedItem()));
                question = String.valueOf(ed_question.getText());
                answer = String.valueOf(ed_answer.getText());

                if(!(qaId.isEmpty()) && !(mainSubject.isEmpty()) && !(subSubjectA.isEmpty()) && !(subSubjectB.isEmpty()) && !(question.isEmpty()) && !(answer.isEmpty())){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "main_subject";
                            field[1] = "sub_subject_a";
                            field[2] = "sub_subject_b";
                            field[3] = "question";
                            field[4] = "answer";
                            field[5] = "qa_id";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = mainSubject;
                            data[1] = subSubjectA;
                            data[2] = subSubjectB;
                            data[3] = question;
                            data[4] = answer;
                            data[5] = qaId;
                            PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/QandA/qacontroller.php?view=update", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.contains("Update Success")){
                                        Toast.makeText(getActivity(), "Cập nhập thành công", Toast.LENGTH_LONG).show();
                                        refresh();
                                        builder.dismiss();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                };
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
        params.height = 1700;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    /**
     * setup Spinner
     */
    private  void setUpSpinner(Spinner sp, int resourse_array) {
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
    private String refineInput(String raw){
        switch ( raw ) {
            case "Trung tâm":
                return "center";
            case "Khóa học":
                return "course";
            case "Liên hệ":
                return "contact";
            case "Giảng dạy":
                return "teaching";
            case "Đăng kí":
                return "register";
            case "Bảo lưu":
                return "reservation";
            case "Tiếng Anh cho bé":
                return "Kid Course";
            case "Tiếng Anh cho người đi làm":
                return "Adult Course";
            case "IELTS":
                return "IELTS";
            case "Học phí":
                return "tuition";
            default:
                return "none";
        }
    }

    /**
     * translate input String from EN to VN
     * @param raw
     * @return
     */
    private String revertInput(String raw){
        switch ( raw ) {
            case "center":
                return "Trung tâm";
            case "course":
                return "Khóa học";
            case "contact":
                return "Liên hệ";
            case "teaching":
                return "Giảng dạy";
            case "register":
                return "Đăng kí";
            case "reservation":
                return "Bảo lưu";
            case "Kid Course":
                return "Tiếng Anh cho bé";
            case "Adult Course":
                return "Tiếng Anh cho người đi làm";
            case "IELTS":
                return "IELTS";
            case "tuition":
                return "Học phí";
            default:
                return "none";
        }
    }

    /**
     * get index Spinner value
     * @param spinner
     * @param myString
     * @return
     */
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    /**
     * refresh fragment
     */
    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, new MainQA());
        transaction.commit();
    }

}