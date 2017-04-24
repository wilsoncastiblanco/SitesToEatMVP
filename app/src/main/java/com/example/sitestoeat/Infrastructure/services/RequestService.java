package com.example.sitestoeat.Infrastructure.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;

import com.example.sitestoeat.data.net.RestConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wilsoncastiblanco on 8/20/16.
 */
public class RequestService extends IntentService {

    public static final String ENDPOINT_KEY = "endpoint_key";
    public static final String REQUEST_METHOD_KEY  = "request_method_key";

    public static final String ACTION_SEND_RESTAURANTS = "com.example.sitestoeat.SEND_RESTAURANTS";
    public static final String RESTAURANTS_KEY = "restaurants_key";

    public RequestService() {
        super("RequestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String endpoint = intent.getStringExtra(ENDPOINT_KEY);
            String requestMethod = intent.getStringExtra(REQUEST_METHOD_KEY);

            Uri.Builder builderUrl = new Uri.Builder();

            builderUrl.scheme(RestConstants.PROTOCOL)
                    .authority(RestConstants.BASE_URL)
                    .appendPath(RestConstants.API_VERSION)
                    .appendPath(endpoint);

            String foodUrl = builderUrl.build().toString();
            URL url = new URL(foodUrl);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setReadTimeout(10000);
            client.setConnectTimeout(15000);
            client.setRequestMethod(requestMethod);
            client.setDoInput(true);
            client.setDoOutput(true);


            client.connect();

            InputStream inputStream = client.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }


            String response = stringBuilder.toString();

            sendResponse(response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(String response) {
        Intent intent = new Intent();
        intent.setAction(ACTION_SEND_RESTAURANTS);
        intent.putExtra(RESTAURANTS_KEY, response);
        sendBroadcast(intent);
    }
}
