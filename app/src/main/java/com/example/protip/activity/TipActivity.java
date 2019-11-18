package com.example.protip.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.protip.R;

public class TipActivity extends AppCompatActivity{

    private Button buttonProTipUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        buttonProTipUser = (Button) findViewById(R.id.buttonProTipUser);

        buttonProTipUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTipActivity();
            }
        });
    }

    /**
     * Method for opening ProUserActivity
     */
    private void openTipActivity() {
        Intent intent = new Intent(this, ProUserActivity.class);
        startActivity(intent);
    }
}
