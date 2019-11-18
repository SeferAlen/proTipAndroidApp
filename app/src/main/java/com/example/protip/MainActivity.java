package com.example.protip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.protip.activity.TipActivity;
import com.example.protip.asyncTasks.AsyncLoginAPIResponse;
import com.example.protip.asyncTasks.LoginAPICall;
import com.example.protip.config.ProTipApplicationConfig;
import com.example.protip.model.APIResponseLogin;
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
    private static final DialogType ERROR = DialogType.ERROR;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String WARNING_TITLE = "Warning";
    private static final String EMPTY_USERNAME_PASSWORD_TEXT = "Username and Password must not be empty";
    private static final String INVALID_USERNAME_TITLE = "Login Failed";
    private static final String INVALID_PASSWORD_TITLE = "Login Failed";
    private static final String NETWORK_ERROR_TITLE = "Network error";
    private static final String NETWORK_ERROR = "Connecting with server error has occurred";
    private static final String NETWORK_ERROR_NO_INTERNET = "Check internet connection";
    private static final String APPLICATION_ERROR_TITLE = "Application error";

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.buttonLogin);

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
                    if (isNetworkConnected()) {
                        sendLoginPostRequest(username, password);
                        loginButton.setEnabled(false);
                    } else {
                        ProTipDialog.openDialog(NETWORK_ERROR_TITLE, NETWORK_ERROR_NO_INTERNET, ERROR, MainActivity.this);
                    }
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
        loginAPICall.execute(ProTipApplicationConfig.getProTipServicesTestURL());
    }

    /**
     * Method for processing response from proTipServices API after login try
     * @param apiResponseLogin {@link APIResponseLogin} apiResponseLogin object containing data returned from proTipServices API
     */
    @Override
    public void processFinished(final APIResponseLogin apiResponseLogin){
        final Button loginButton = (Button) findViewById(R.id.buttonLogin);

        loginButton.setEnabled(true);

        switch (apiResponseLogin.getResponseCode()) {
            case HTTP_STATUS_OK:
                setToken(apiResponseLogin.getResponse());
                openTipActivity();
                break;
            case HTTP_STATUS_BAD_REQUEST:
                ProTipDialog.openDialog(INVALID_USERNAME_TITLE, apiResponseLogin.getResponse(), INFO, this);
                break;
            case HTTP_STATUS_UNAUTHORIZED:
                ProTipDialog.openDialog(INVALID_PASSWORD_TITLE, apiResponseLogin.getResponse(), INFO, this);
                break;
            case HTTP_STATUS_NOT_FOUND:
                ProTipDialog.openDialog(NETWORK_ERROR_TITLE, apiResponseLogin.getResponse(), ERROR, this);
                break;
            case HTTP_STATUS_INTERNAL_ERROR:
                ProTipDialog.openDialog(APPLICATION_ERROR_TITLE, apiResponseLogin.getResponse(), ERROR, this);
                break;
        }
    }

    /**
     * Method for opening TipActivity
     */
    private void openTipActivity() {
        Intent intent = new Intent(this, TipActivity.class);
        startActivity(intent);
    }

    /**
     * Method for checking network connection status
     * @return {@link APIResponseLogin} apiResponse object containing data returned from proTipServices API
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void setToken(final String token) {

        final SharedPreferences.Editor editor = getSharedPreferences(ProTipApplicationConfig.getPreferencesName(), MODE_PRIVATE).edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }
}
