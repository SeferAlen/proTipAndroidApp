package com.example.protip.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.protip.R;
import com.example.protip.asyncTasks.AsyncGetMessagesAPIResponse;
import com.example.protip.asyncTasks.GetMessagesAPICall;
import com.example.protip.asyncTasks.LoginAPICall;
import com.example.protip.config.ProTipApplicationConfig;
import com.example.protip.model.APIResponseMessages;
import com.example.protip.rabbitMQ.RabbitMQMessaging;

import java.util.HashMap;

public class ProUserActivity extends AppCompatActivity implements AsyncGetMessagesAPIResponse {
    private static final String TOKEN = "token";
    private static final String SHARED_PREF_DEFAULT_VALUE = "No name defined";

    private RabbitMQMessaging rabbitMQMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_user);

        rabbitMQMessaging = new RabbitMQMessaging();

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString("msg");
                sendLoginPostRequest(getToken());
            }
        };
        rabbitMQMessaging.subscribe(incomingMessageHandler);
    }

    @Override
    public void processFinished(final APIResponseMessages apiResponseMessages) {

    }

    /**
     * Method for starting asyncAPICall task for get messages action
     *
     * @param token {@link String} the token
     */
    private void sendLoginPostRequest(final String token) {

        HashMap<String, String> postData = new HashMap<>();
        postData.put(TOKEN, token);

        final GetMessagesAPICall getMessagesAPICall = new GetMessagesAPICall(postData, this);
        getMessagesAPICall.execute(ProTipApplicationConfig.getProTipServicesTest2URL());
    }

    /**
     * Method for retrieve token from Shared preferences
     *
     * @return token {@link String} the token
     */
    private String getToken() {

        final SharedPreferences sharedPreferences = getSharedPreferences(ProTipApplicationConfig.getPreferencesName(),
                MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, SHARED_PREF_DEFAULT_VALUE);

        return token;
    }
}
