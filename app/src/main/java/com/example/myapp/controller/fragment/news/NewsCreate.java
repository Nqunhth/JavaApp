package com.example.myapp.controller.fragment.news;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapp.R;
import com.example.myapp.controller.activity.MainActivity;
import com.example.myapp.model.news.NewsModel;
import com.example.myapp.model.user.CurrentUser;
import com.example.myapp.utils.ImgUploader;
import com.example.myapp.utils.ShareIP;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.concurrent.ExecutionException;

public class NewsCreate extends Fragment {
    int SELECT_PICTURE = 200;
    private ImageView iv_thumpnail;
    private ActivityResultLauncher<Intent> register;
    private String thumpnail = "";
    private Uri imgUri;
    private Boolean isNewImg = false;
    private String header = "";
    //setup NewsModel
    private NewsModel news = new NewsModel("", "", "", "", "", "", "", "");

    public NewsCreate(String _header, NewsModel _news) {
        this.header = _header;
        news = _news;
    }

    public NewsCreate() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news_create, container, false);
        ImageView iv = view.findViewById(R.id.news_create_backicon);

        TextView tv_header = view.findViewById(R.id.news_create_header);
        if (this.header != "")
            tv_header.setText(this.header);

        TextInputEditText ed_title = view.findViewById(R.id.news_create_title);
        ed_title.setText(this.news.getPostTitle());

        TextInputEditText ed_content = view.findViewById(R.id.news_create_content);
        ed_content.setText(this.news.getPostContent());

        iv_thumpnail = view.findViewById(R.id.news_create_thumpnail);
        //handle News image with Glide
        if (!news.getPostImg().isEmpty())
            Glide.with(view)
                    .load(news.getPostImg())
                    .placeholder(R.drawable.news_detail_placeholder)
                    .error(R.drawable.news_detail_placeholder)
                    .centerCrop()
                    .into(iv_thumpnail);

        Spinner sp_displayPlace = view.findViewById(R.id.news_create_display_place);
        setUpSpinner(sp_displayPlace, R.array.news_display_place);

        // setup Spinner
        if (this.news.getDisplay_place().equals("List")) {
            sp_displayPlace.setSelection(1);
            Log.e("selected", sp_displayPlace.getSelectedItem().toString());
        } else {
            sp_displayPlace.setSelection(0);
            Log.e("selected", sp_displayPlace.getSelectedItem().toString());
        }

        AppCompatButton postBtn = view.findViewById(R.id.news_create_post_btn);
        AppCompatButton updateBtn = view.findViewById(R.id.news_create_update_btn);
        if (this.header != "") {
            postBtn.setVisibility(View.GONE);
            updateBtn.setVisibility(View.VISIBLE);
        } else {
            postBtn.setVisibility(View.VISIBLE);
            updateBtn.setVisibility(View.GONE);
        }

        //create News function
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title, displayPlace, content, authorId;
                title = String.valueOf(ed_title.getText());
                displayPlace = String.valueOf(sp_displayPlace.getSelectedItem());
                content = String.valueOf(ed_content.getText());
                authorId = CurrentUser.getCurrentUser().getAccountId();

                if (imgUri == null) {
                    Log.e("null", "null");
                }
                else
                    try {
                        thumpnail = new ImgUploader(imgUri, requireActivity()).execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                if (!(title.isEmpty()) && !(displayPlace.isEmpty()) && !(content.isEmpty()) && !(authorId.isEmpty()) && !(thumpnail.isEmpty())) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "post_title";
                            field[1] = "post_content";
                            field[2] = "display_place";
                            field[3] = "author_id";
                            field[4] = "post_img";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = title;
                            data[1] = content;
                            data[2] = displayPlace;
                            data[3] = authorId;
                            data[4] = thumpnail;
                            PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/News/newscontroller.php?view=upload", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.contains("Upload Success")) {
                                        Toast.makeText(getActivity(), "Đăng tải thành công", Toast.LENGTH_LONG).show();
                                        back(view);
                                    } else {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                }
                ;
            }
        });

        //update News function
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title, displayPlace, content, postId;
                title = String.valueOf(ed_title.getText());
                displayPlace = String.valueOf(sp_displayPlace.getSelectedItem());
                content = String.valueOf(ed_content.getText());
                postId = String.valueOf(news.getPostId());
                if (!isNewImg)
                    thumpnail = news.getPostImg();
                else {
                    try {
                        thumpnail = new ImgUploader(imgUri, requireActivity()).execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!(title.isEmpty()) && !(displayPlace.isEmpty()) && !(content.isEmpty()) && !(postId.isEmpty()) && !(thumpnail.isEmpty())) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "post_title";
                            field[1] = "post_content";
                            field[2] = "display_place";
                            field[3] = "post_id";
                            field[4] = "post_img";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = title;
                            data[1] = content;
                            data[2] = displayPlace;
                            data[3] = postId;
                            data[4] = thumpnail;
                            PutData putData = new PutData("http://" + ShareIP.getIp() + "/da1_courseadviser/News/newscontroller.php?view=update", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.contains("Update Success")) {
                                        Toast.makeText(getActivity(), "Cập nhập thành công", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                    back(view);
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các mục", Toast.LENGTH_SHORT).show();
                }
                ;
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(view);
            }
        });

        register = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            imgUri = data.getData();
                            iv_thumpnail.setImageURI(imgUri);
                            isNewImg = true;
                        }
                    }
                });

        iv_thumpnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
                if (result == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    register.launch(i);
                } else {
                    Toast.makeText(getActivity(), "Vui lòng cho phép ứng dụng truy cập vào bộ nhớ để sử dụng tính năng này", Toast.LENGTH_LONG).show();
                }

            }
        });

        //set scrollable for input
        ed_content.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (ed_content.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
        return view;
    }

    /**
     * back to previous fragment
     * @param v
     */
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}