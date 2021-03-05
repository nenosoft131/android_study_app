package com.cyberdesignz.studyup.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {
    JSONArray jArray;
    JSONObject jObj;
    private boolean isJArray = false;
    private String _feedUrl;
    private String TAG = "Json Parser";

    private int RetryCount;

    public JsonParser() {
    }

    public JSONArray GetJasonArray(String url, boolean ishttpPost) {
        _feedUrl = url;
        isJArray = true;
        if (ishttpPost)
            MakeAJasonPostCall();
        else
            MakeAJasonGetCall();
        return jArray;
    }

    public JSONObject GetJasonObject(String url, boolean ishttpPost) {
        _feedUrl = url;
        isJArray = false;
        if (ishttpPost)
            MakeAJasonPostCall();
        else
            MakeAJasonGetCall();
        return jObj;
    }

    public boolean UploadJSONObject(JSONObject data, String URL) {
        boolean status = false;
        HttpPost httpost = null;
        HttpClient httpClient = null;
        HttpResponse response = null;
        StringEntity se = null;

        httpost = new HttpPost(URL);

        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        try {
            se = new StringEntity(data.toString());
            httpost.setEntity(se);
            httpClient = new DefaultHttpClient();
            response = httpClient.execute(httpost);

            HttpEntity responseEntity = response.getEntity();
            char[] buffer = new char[(int) responseEntity.getContentLength()];
            InputStream stream = responseEntity.getContent();
            InputStreamReader reader = new InputStreamReader(stream);
            reader.read(buffer);
            stream.close();

            new JSONObject(new String(buffer));

            status = true;

        } catch (NumberFormatException e) {
            Log.d("Exception: ", "Number Format Exception.");
            status = true;
        } catch (JSONException e) {
            Log.d("Exception: ", "JSON Exception.");
            status = true;
        } catch (UnsupportedEncodingException e) {
            Log.d("Exception: ", "Unsupported Encoding Exception.");
            status = true;
        } catch (ClientProtocolException e) {
            Log.d("Exception: ", "Client Protocol Exception.");
            status = true;
        } catch (IOException e) {
            Log.d("Exception: ", "IO Exception.");
            status = true;
        }

        return status;
    }

    public boolean uploadjsonPHP(JSONObject data, String URL) {

        boolean status = false;

        try {

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, RetryCount);
            HttpConnectionParams.setSoTimeout(httpParams, RetryCount);
            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost request = new HttpPost(URL);
            request.setEntity(new ByteArrayEntity(data.toString().getBytes(
                    "UTF8")));
            request.setHeader("json", data.toString());
            HttpResponse response = client.execute(request);
            HttpEntity responseEntity = response.getEntity();
            char[] buffer = new char[(int) responseEntity.getContentLength()];
            InputStream stream = responseEntity.getContent();
            InputStreamReader reader = new InputStreamReader(stream);
            reader.read(buffer);
            stream.close();

            new JSONObject(new String(buffer));

            status = true;

        } catch (NumberFormatException e) {
            Log.d("Exception: ", "Number Format Exception.");
            status = true;
        } catch (JSONException e) {
            Log.d("Exception: ", "JSON Exception.");
            status = true;
        } catch (UnsupportedEncodingException e) {
            Log.d("Exception: ", "Unsupported Encoding Exception.");
            status = true;
        } catch (ClientProtocolException e) {
            Log.d("Exception: ", "Client Protocol Exception.");
            status = true;
        } catch (IOException e) {
            Log.d("Exception: ", "IO Exception.");
            status = true;
        }
        return status;

    }

    private void MakeAJasonPostCall() {
        for (RetryCount = 0; RetryCount < 3; RetryCount++) {
            try {

                Log.d(TAG, "Service prior to call..");
                DefaultHttpClient httpClient = new DefaultHttpClient();

                StringBuilder builder = new StringBuilder();

                HttpPost request = new HttpPost(_feedUrl);
                request.setHeader("Accept", "application/json");
                request.setHeader("Content-type", "application/json");
                HttpResponse response;
                Log.d(TAG, "Service Call Starts...");
                try {
                    response = httpClient.execute(request);

                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(content));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }

                    } else {
                        Log.e("JSON Prser", "Failed to download file");
                    }

                    try {
                        // if JSON returns array in response
                        if (isJArray) {
                            jArray = new JSONArray();
                            jArray = new JSONArray(new String(builder));
                            if (jArray != null)
                                break;
                        } else {
                            jObj = new JSONObject();
                            jObj = new JSONObject(new String(builder));
                            if (jObj != null)
                                break;
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "Service Call Inner Exception...");

                        HandlerException(e);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Service Call Outer Exception...");
                    HandlerException(e);
                }
            } catch (Exception e) {
                Log.d(TAG, "Service Call v Outer Exception...");
                HandlerException(e);
            }

        }
    }

    private void MakeAJasonGetCall() {
        for (RetryCount = 0; RetryCount < 3; RetryCount++) {
            try {

                Log.d(TAG, "Service prior to call..");
                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpGet request = new HttpGet(_feedUrl);
                request.setHeader("Accept", "application/json");
                request.setHeader("Content-type", "application/json");
                HttpResponse response;

                Log.d(TAG, "Service Call Starts...");
                try {
                    response = httpClient.execute(request);// Ecception
                    HttpEntity responseEntity = response.getEntity();
                    InputStream stream = responseEntity.getContent();

					/*
                     * // Read response data into buffer char[] buffer = new
					 * char[(int)responseEntity.getContentLength()];
					 * InputStreamReader reader = new InputStreamReader(stream);
					 * reader.read(buffer); stream.close();
					 */

                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(stream));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }

                    try {
                        // if JSON returns array in response
                        if (isJArray) {
                            jArray = new JSONArray();
                            jArray = new JSONArray(new String(total));
                            if (jArray != null)
                                break;
                        } else {
                            jObj = new JSONObject();
                            jObj = new JSONObject(new String(total));
                            if (jObj != null)
                                break;
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "Service Call Inner Exception...");

                        HandlerException(e);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Service Call Outer Exception...");
                    HandlerException(e);
                }
            } catch (Exception e) {
                Log.d(TAG, "Service Call v Outer Exception...");
                HandlerException(e);
            }

        }
    }

    private void HandlerException(Exception e) {
        Log.d(TAG, e.getMessage());
    }

}
