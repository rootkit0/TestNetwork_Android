package com.example.testnetwork_android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);

        AsyncTaskRunnerNetworkState runner = new AsyncTaskRunnerNetworkState();
        runner.execute();
    }

    private class AsyncTaskRunnerNetworkState extends AsyncTask<String, String, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            String tvContent1 = "", tvContent2 = "";
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    Network net = connectivityManager.getActiveNetwork();
                    NetworkCapabilities netCapabilities = connectivityManager.getNetworkCapabilities(net);
                    if(net != null && netCapabilities != null) {
                        tvContent1 += "Active network:\n" + net.toString() + "\n";
                        tvContent1 += "Network capabilities:\n" + netCapabilities.toString() + "\n";
                        if(netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            tvContent2 = "Wifi connected!";
                        }
                        else if(netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            tvContent2 = "Mobile connected!";
                        }
                    }
                    else {
                        tvContent2 = "No network operating!";
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                    tvContent1 += "Network info:\n" + netInfo.toString();
                    if(netInfo != null && netInfo.isConnected()) {
                        if(netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            tvContent2 = "Wifi connected!";
                        }
                        else if(netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            tvContent2 = "Mobile connected!";
                        }
                    }
                    else {
                        tvContent2 = "No network operating!";
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            String[] tvContent = {tvContent1, tvContent2};
            return tvContent;
        }

        @Override
        protected void onPostExecute(String s[]) {
            super.onPostExecute(s);
            tv1.setText(s[0]);
            tv2.setText(s[1]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTaskRunnerNetworkState runner = new AsyncTaskRunnerNetworkState();
        runner.execute();
    }
}