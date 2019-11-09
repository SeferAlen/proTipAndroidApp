package com.example.protip.asyncTasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.protip.model.APIResponse;
import com.example.protip.utility.InputStreamResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class extending AsyncTask for making HTTP request to API for login
 */
public class LoginAPICall extends AsyncTask<String, Void, APIResponse> {
    private static final int HTTP_STATUS_OK = HttpsURLConnection.HTTP_OK;
    private static final int HTTP_STATUS_BAD_REQUEST = HttpsURLConnection.HTTP_BAD_REQUEST;
    private static final int HTTP_STATUS_UNAUTHORIZED = HttpsURLConnection.HTTP_UNAUTHORIZED;
    private static final int HTTP_STATUS_NOT_FOUND = HttpsURLConnection.HTTP_NOT_FOUND;
    private static final int HTTP_STATUS_INTERNAL_ERROR = HttpsURLConnection.HTTP_INTERNAL_ERROR;
    private static final String POST_METHOD = "POST";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    private static final String APPLICATION_JSON = "application/json";
    private static final String NETWORK_ERROR = "Network error, check network connection";
    private static final String ERROR = "Error";
    private static final String TOKEN = "token";
    private static final String MESSAGE = "message";

    public AsyncLoginAPIResponse delegate;

    private HashMap<String, String> postDataParams;
    private int responseCode;
    private String message;
    private JSONObject jsonObject;;
    private InputStreamResponse inputStreamResponse = InputStreamResponse.UNKNOWN;

    public LoginAPICall(final HashMap<String, String> postDataParams, final AsyncLoginAPIResponse delegate){
        this.postDataParams = postDataParams;
        this.delegate = delegate;
    }

    /**
     * Method for calling proTipServieces API on seperate thread
     *
     * @param params {@link String...} params for API call
     * @return {@link APIResponse} representing status of reading input status
     */
    @Override
    protected APIResponse doInBackground(String... params) {

        final String url = params[0];

        try {
            final InputStream inputStream;
            final JSONObject jsonData = new JSONObject(postDataParams);

            HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());

            httpURLConnection.setRequestMethod(POST_METHOD);
            httpURLConnection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_UTF8);
            httpURLConnection.setRequestProperty(ACCEPT, APPLICATION_JSON);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            final BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
            writer.write(jsonData.toString());
            writer.flush();
            writer.close();

            responseCode = httpURLConnection.getResponseCode();

            switch (responseCode) {
                case HTTP_STATUS_OK:
                    inputStream = httpURLConnection.getInputStream();
                    inputStreamResponse = getResponse(inputStream);
                    break;
                case HTTP_STATUS_BAD_REQUEST:
                    inputStream = httpURLConnection.getErrorStream();
                    inputStreamResponse = getResponse(inputStream);
                    break;
                case HTTP_STATUS_UNAUTHORIZED:
                    inputStream = httpURLConnection.getErrorStream();
                    inputStreamResponse = getResponse(inputStream);
                    break;
            }

            if (inputStreamResponse.equals(InputStreamResponse.ERROR)) {
                return new APIResponse(ERROR, HTTP_STATUS_INTERNAL_ERROR);
            }
        } catch (final ConnectException r) {
            return new APIResponse(NETWORK_ERROR, HTTP_STATUS_NOT_FOUND);
        } catch (final Exception e) {
            return new APIResponse(ERROR, HTTP_STATUS_INTERNAL_ERROR);
        }

        try {
            if (responseCode == HTTP_STATUS_OK) {
                message = jsonObject.getString(TOKEN);
            } else {
                message = jsonObject.getString(MESSAGE);
            }
        } catch (final JSONException e) {
            return new APIResponse(ERROR, HttpsURLConnection.HTTP_INTERNAL_ERROR);
        }
        return new APIResponse(message, responseCode);
    }

    /**
     * Method after execute is over
     *
     * @param apiResponse {@link APIResponse} apiResponse representing data sent by API
     */
    @Override
    protected void onPostExecute(final APIResponse apiResponse) {
        delegate.processFinished(apiResponse);
    }

    /**
     * Method for reading input stream data and setting jsonObject values from it
     *
     * @param inputStream {@link InputStream} input stream for reading data
     * @return {@link InputStreamResponse} representing status of reading input status
     */
    private InputStreamResponse getResponse(final InputStream inputStream) {

        try{
            final BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            final StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            inputStream.close();

            final String response = stringBuilder.toString();
            jsonObject = new JSONObject(response);

            return InputStreamResponse.OK;
        } catch (final Exception e) {
            return InputStreamResponse.ERROR;
        }
    }
}
