package com.example.protip.asyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LoginAPICall extends AsyncTask<String, String, String> {
    private HashMap<String, String> postDataParams;
    private String response;

    public LoginAPICall(HashMap<String, String> postDataParams){
        this.postDataParams = postDataParams;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        final String url = params[0];

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept","application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            JSONObject jsonData = new JSONObject(postDataParams);

            DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            outputStream.writeBytes(jsonData.toString());

            outputStream.flush();
            outputStream.close();

            int responseCode = httpURLConnection.getResponseCode();

            if(responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                while((line = reader.readLine()) != null) {
                    response = response + line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            System.out.println("Exception is " + e.getMessage());
        }

        return response;
    }
}
