package com.example.protip;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.protip.asyncTasks.LoginAPICall;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = (Button) findViewById(R.id.buttonLogin);


        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                btnAnimation(v);

                final String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
                final String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

                if(username.isEmpty() || password.isEmpty()) {
                    openDialog();
                } else {
                    sendLoginPostRequest(username, password);
                    loginButton.setEnabled(false);
                }

            }
        });
    }

    private void btnAnimation(View v) {
        Animator scale = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.12f, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.12f, 1)
        );
        scale.setDuration(300);
        scale.start();
    }

    private void sendLoginPostRequest(final String username, final String password) {
        HashMap<String, String> postData = new HashMap<>();
        postData.put("username:", username);
        postData.put("password:", password);

        final LoginAPICall loginAPICall = new LoginAPICall(postData);
        loginAPICall.execute("http://192.168.42.129:9090/login");
    }

    private void openDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_info, viewGroup, false);

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        final Button buttonOK = (Button) dialogView.findViewById(R.id.buttonOk);
        TextView textViewTitle = (TextView) dialogView.findViewById(R.id.editTextTitle);
        TextView textViewMessage = (TextView) dialogView.findViewById(R.id.editTextText);

        textViewTitle.setText("Warning!");
        textViewMessage.setText("Username and Password must not be empty");

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }
}
