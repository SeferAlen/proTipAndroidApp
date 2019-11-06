package com.example.protip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.protip.activity.TipActivity;
import com.example.protip.asyncTasks.AsyncLoginAPIResponse;
import com.example.protip.asyncTasks.LoginAPICall;
import com.example.protip.config.ProTipApplicationConfig;
import com.example.protip.model.APIResponse;
import com.example.protip.utility.Animations;
import com.example.protip.utility.DialogType;
import com.example.protip.utility.ProTipDialog;

import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements AsyncLoginAPIResponse {
    private static final int HTTP_STATUS_OK = HttpsURLConnection.HTTP_OK;
    private static final int HTTP_STATUS_BAD_REQUEST = HttpsURLConnection.HTTP_BAD_REQUEST;
    private static final int HTTP_STATUS_UNAUTHORIZED = HttpsURLConnection.HTTP_UNAUTHORIZED;
    private static final int HTTP_STATUS_NOT_FOUND = HttpsURLConnection.HTTP_NOT_FOUND;
    private static final int HTTP_STATUS_INTERNAL_ERROR = HttpsURLConnection.HTTP_INTERNAL_ERROR;
    private static final DialogType INFO = DialogType.INFO;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String WARNING_TITLE = "Warning";
    private static final String EMPTY_USERNAME_PASSWORD_TEXT = "Username and Password must not be empty";
    private static final String INVALID_USERNAME_TITLE = "Login Failed";
    private static final String INVALID_PASSWORD_TITLE = "Login Failed";
    private static final String NETWORK_ERROR = "Network error";
    private static final String APPLICATION_ERROR = "Application error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = (Button) findViewById(R.id.buttonLogin);

        /**
         * Login button click listener for sending POST request and opening Dialog window
         */
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Animations.btnAnimation(v);

                final String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
                final String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    ProTipDialog.openDialog(WARNING_TITLE, EMPTY_USERNAME_PASSWORD_TEXT, INFO, MainActivity.this);
                } else {
                    sendLoginPostRequest(username, password);
                    loginButton.setEnabled(false);
                }
            }
        });
    }

    /**
     * Method for starting asyncAPICall task for logging action
     *
     * @param username {@link String} username
     * @param password {@link String} password
     */
    private void sendLoginPostRequest(final String username, final String password) {

        HashMap<String, String> postData = new HashMap<>();
        postData.put(USERNAME, username);
        postData.put(PASSWORD, password);

        final LoginAPICall loginAPICall = new LoginAPICall(postData, this);
        loginAPICall.execute(ProTipApplicationConfig.getProTipServicesURL());
    }

    /**
     * Method for processing response from proTipServices API after login try
     * @param apiResponse {@link APIResponse} apiResponse object containing data returned from proTipServices API
     */
    @Override
    public void processFinished(final APIResponse apiResponse){
        final Button loginButton = (Button) findViewById(R.id.buttonLogin);

        loginButton.setEnabled(true);

        switch (apiResponse.getResponseCode()) {
            case HTTP_STATUS_OK:
                openTipActivity();
                break;
            case HTTP_STATUS_BAD_REQUEST:
                ProTipDialog.openDialog(INVALID_USERNAME_TITLE, apiResponse.getResponse(), DialogType.INFO, this);
                break;
            case HTTP_STATUS_UNAUTHORIZED:
                ProTipDialog.openDialog(INVALID_PASSWORD_TITLE, apiResponse.getResponse(), DialogType.INFO, this);
                break;
            case HTTP_STATUS_NOT_FOUND:
                ProTipDialog.openDialog(NETWORK_ERROR, apiResponse.getResponse(), DialogType.ERROR, this);
                break;
            case HTTP_STATUS_INTERNAL_ERROR:
                ProTipDialog.openDialog(APPLICATION_ERROR, apiResponse.getResponse(), DialogType.ERROR, this);
                break;
        }
    }

    private void openTipActivity() {
        Intent intent = new Intent(this, TipActivity.class);
        startActivity(intent);
    }
}
