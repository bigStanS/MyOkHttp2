package com.example.stan.myokhttp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.stan.myokhttp.MyNetworkIntentService.NETWORK_SERVICE_MESSAGE;
import static com.example.stan.myokhttp.MyNetworkIntentService.NETWORK_SERVICE_PAYLOAD;

public class MainActivity extends AppCompatActivity {

    private TextView output;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Our TextView to see the returned results
        output = (TextView) findViewById(R.id.output);

        // Register the Broadcast Receiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver, new IntentFilter(NETWORK_SERVICE_MESSAGE));
        // don't forget to unregisterReceiver the Broadcast Receiver
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // UnRegister the Broadcast Receiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Don't forget to register this receiver !!!!!
            // Nothing to add to manifest.
            Log.i(TAG, "onReceive: a message was received");
            String message = "null message";
            switch (intent.getAction()) {
                case NETWORK_SERVICE_MESSAGE:
                    Log.i(TAG, "onReceive: Received data from our service.");
                    // print the results from the network service with time-stamp
                    SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
                    String time = localDateFormat.format(new Date());
                    message = intent.getStringExtra(NETWORK_SERVICE_PAYLOAD);
                    output.append(message + " - " + time + "\n");
                    break;
            }
            output.setText(message);
        }
    };

    public void getClickHandler(View view) {
        String url = "https://getmovez.com/";
        Intent intent = new Intent(this, MyNetworkIntentService.class);
        intent.putExtra("THE_URL", url);
        intent.putExtra("THE_METHOD", "GET");
        startService(intent);
    }

    public void postClickHandler(View view) {
        String url = "https://getmovez.com/tlogin";
        String jsonParams = "{\"user\":\"stan1\"," +
                "\"pass\":\"123\"" +
                "}";
        Intent intent = new Intent(this, MyNetworkIntentService.class);
        intent.putExtra("THE_URL", url);
        intent.putExtra("THE_METHOD", "POST");
        intent.putExtra("THE_PAYLOAD", jsonParams);
        startService(intent);
    }

    public void putClickHandler(View view) {
        String url = "https://getmovez.com/tavailable";
        String jsonParams = "{\"trucker\":\"3b7142a1d4e64f750c4af0a91387adc0d13e1c63\"," +
                "\"available\":\"online\"" +
                "}";

        Intent intent = new Intent(this, MyNetworkIntentService.class);
        intent.putExtra("THE_URL", url);
        intent.putExtra("THE_METHOD", "PUT");
        intent.putExtra("THE_PAYLOAD", jsonParams);
        startService(intent);
    }
}
