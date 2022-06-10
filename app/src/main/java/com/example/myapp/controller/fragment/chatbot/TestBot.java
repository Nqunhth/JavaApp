package com.example.myapp.controller.fragment.chatbot;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.controller.adapter.ChatAdapter;
import com.example.myapp.controller.fragment.examregist.ExamRegist;
import com.example.myapp.utils.BotReply;

import com.example.myapp.model.message.Message;
import com.example.myapp.utils.SendMessageInBg;
import com.google.android.material.chip.Chip;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TestBot extends DialogFragment implements BotReply {
    RecyclerView chatView;
    ChatAdapter chatAdapter;
    List<Message> messageList = new ArrayList<>();
    EditText editMessage;
    ImageButton btnSend;
    HorizontalScrollView layoutSuggest;
    Chip suggest1;
    Chip suggest2;
    Chip suggest3;
    Chip suggest4;
    Chip suggest5;
    //dialogFlow
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private String uuid = UUID.randomUUID().toString();
    private String TAG = "mainactivity";

    Context mContext;
    final Handler handler = new Handler();

    Dialog builder;

    public TestBot() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_test_bot, new LinearLayout(getActivity()), false);

        // Build dialog
        builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_test_bot, container, false);
        chatView = view.findViewById(R.id.chatView);
        editMessage = view.findViewById(R.id.editMessage);
        btnSend = view.findViewById(R.id.btnSend);
        layoutSuggest = view.findViewById(R.id.bot_suggest_layout);
        suggest1 = view.findViewById(R.id.bot_suggest_chip1);
        suggest2 = view.findViewById(R.id.bot_suggest_chip2);
        suggest3 = view.findViewById(R.id.bot_suggest_chip3);
        suggest4 = view.findViewById(R.id.bot_suggest_chip4);
        suggest5 = view.findViewById(R.id.bot_suggest_chip5);
        impleChipsOnClick(suggest1);
        impleChipsOnClick(suggest2);
        impleChipsOnClick(suggest3);
        impleChipsOnClick(suggest4);
        impleChipsOnClick(suggest5);

        chatAdapter = new ChatAdapter(messageList, this.getActivity());
        chatView.setAdapter(chatAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editMessage.getText().toString();
                if (!message.isEmpty()) {
                    messageList.add(new Message(message, false));
                    editMessage.setText("");
                    sendMessageToBot(message);
                    Objects.requireNonNull(chatView.getAdapter()).notifyDataSetChanged();
                    Objects.requireNonNull(chatView.getLayoutManager())
                            .scrollToPosition(messageList.size() - 1);
                } else {
                    Toast.makeText(getContext(), "Please enter text!", Toast.LENGTH_SHORT).show();
                }
                if (layoutSuggest.getVisibility() == View.VISIBLE) {
                    suggest1.setChecked(false);
                    suggest2.setChecked(false);
                    suggest3.setChecked(false);
                    suggest4.setChecked(false);
                    suggest5.setChecked(false);
                    suggest1.setVisibility(View.GONE);
                    suggest2.setVisibility(View.GONE);
                    suggest3.setVisibility(View.GONE);
                    suggest4.setVisibility(View.GONE);
                    suggest5.setVisibility(View.GONE);
                    layoutSuggest.setVisibility(View.INVISIBLE);
                }
            }
        });

        setUpBot();
        return view;
    }

    //Made chatbot ready to communicate with the user
    private void setUpBot() {
        try {
            InputStream stream = this.getResources().openRawResource(R.raw.credential);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);

            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }

    private void sendMessageToBot(String message) {
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("vi-VN")).build();
        new SendMessageInBg(this, sessionName, sessionsClient, input).execute();
    }

    @Override
    public void callback(DetectIntentResponse returnResponse) {
        if (returnResponse != null) {
            String botReply = returnResponse.getQueryResult().getFulfillmentText();
            if (!botReply.isEmpty()) {
                if (botReply.contains("Đề xuất:")) {
                    int pos = botReply.indexOf("Đề xuất: ");
                    Log.d("chat", "position" + String.valueOf(pos));
                    Log.d("chat", "content" + botReply.substring(pos + 8));
                    String forChips = botReply.substring(pos + 8);
                    ArrayList<String> content = getArray(forChips);
                    layoutSuggest.setVisibility(View.VISIBLE);
                    switch (content.size()) {
                        case 1:
                            setChips(suggest1, content.get(0));
                            break;
                        case 2:
                            setChips(suggest1, content.get(0));
                            setChips(suggest2, content.get(1));
                            break;
                        case 3:
                            setChips(suggest1, content.get(0));
                            setChips(suggest2, content.get(1));
                            setChips(suggest3, content.get(2));
                            break;
                        case 4:
                            setChips(suggest1, content.get(0));
                            setChips(suggest2, content.get(1));
                            setChips(suggest3, content.get(2));
                            setChips(suggest4, content.get(3));
                            break;
                        case 5:
                            setChips(suggest1, content.get(0));
                            setChips(suggest2, content.get(1));
                            setChips(suggest3, content.get(2));
                            setChips(suggest4, content.get(3));
                            setChips(suggest5, content.get(4));
                            break;
                    }
                }
                messageList.add(new Message(botReply, true));
                chatAdapter.notifyDataSetChanged();
                Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);
                if (botReply.contains("Xin cảm ơn")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            builder.dismiss();
                            replace(new ExamRegist());
                        }
                    }, 2000);
                }
            } else {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "failed to connect!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTextSuggest(String text) {
        this.editMessage.setText(text);
    }

    private ArrayList<String> getArray(String str) {
        ArrayList<String> content = new ArrayList<String>();
        while (str.contains(",")) {
            int divide = str.indexOf(", ");
            if (divide != -1) {
                content.add(str.substring(1, divide));
                str = str.substring(divide + 1);
            }
        }
        content.add(str.substring(1));
        Log.d("chat", "final" + String.valueOf(content));
        return content;
    }

    private void setChips(Chip c, String str) {
        c.setVisibility(View.VISIBLE);
        c.setText(str);
    }

    private void impleChipsOnClick(Chip c) {
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextSuggest((String) c.getText());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = 1820;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment, fragment);
        transaction.addToBackStack("exam_regist");
        transaction.commit();
    }

}
