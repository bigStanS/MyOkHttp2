package com.example.stan.myokhttp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyNetworkIntentService extends IntentService {
    public static final String TAG = "MyService";
    public static final String NETWORK_SERVICE_MESSAGE = "networkServiceMessage";
    public static final String NETWORK_SERVICE_PAYLOAD = "networkServicePayload";

    public MyNetworkIntentService() {
        super("MyNetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "onHandleIntent: Entry");

        String method = intent.getStringExtra("THE_METHOD");
        String url = intent.getStringExtra("THE_URL");
        String jsonParams = intent.getStringExtra("THE_PAYLOAD");

        switch (method) {
            case "GET":
                get(url);
                break;
            case "PUT":
                put(url, jsonParams);
                break;
            default:
//                "POST";
                post(url, jsonParams);
        }
    }

    private void get(String url) {
        Get get = new Get();
        try {
            String response = get.run(url);
            // Now return the response via a local Broadcast
            if (response != null) {
                Intent messageIntent = new Intent(NETWORK_SERVICE_MESSAGE); // the action
                messageIntent.putExtra(NETWORK_SERVICE_PAYLOAD, response);  // the extra
                LocalBroadcastManager LBM = LocalBroadcastManager.getInstance(getApplicationContext());    // Get reference to the LocalBroadcastManager
                LBM.sendBroadcast(messageIntent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void put(String url, String jsonParams) {
        Put example = new Put();
        try {
            String response = example.post(url, jsonParams);
            // Now return the response via a local Broadcast
            if (response != null) {
                Intent messageIntent = new Intent(NETWORK_SERVICE_MESSAGE); // the action
                messageIntent.putExtra(NETWORK_SERVICE_PAYLOAD, response);  // the extra
                LocalBroadcastManager LBM = LocalBroadcastManager.getInstance(getApplicationContext());    // Get reference to the LocalBroadcastManager
                LBM.sendBroadcast(messageIntent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void post(String url, String jsonParams) {
        Post example = new Post();
        try {
            String response = example.post(url, jsonParams);
            // Now return the response via a local Broadcast
            if (response != null) {
                Intent messageIntent = new Intent(NETWORK_SERVICE_MESSAGE); // the action
                messageIntent.putExtra(NETWORK_SERVICE_PAYLOAD, response);  // the extra
                LocalBroadcastManager LBM = LocalBroadcastManager.getInstance(getApplicationContext());    // Get reference to the LocalBroadcastManager
                LBM.sendBroadcast(messageIntent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    public class Get {
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    public class Put {
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String post(String url, String jsonPayload) throws IOException {
            RequestBody body = RequestBody.create(JSON, jsonPayload);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }

    public class Post {
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String post(String url, String jsonPayload) throws IOException {
            RequestBody body = RequestBody.create(JSON, jsonPayload);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }

}
