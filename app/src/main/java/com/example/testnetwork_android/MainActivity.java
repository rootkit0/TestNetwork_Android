package com.example.testnetwork_android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
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
        if(!isNetworkAvailabe()) {
            tv1.append("No network operating!");
        }
    }

    private Boolean isNetworkAvailabe() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) {
                return false;
            }
            tv1.append("Active network:\n" + nw.toString() + "\n");
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            if(actNw == null) {
                return false;
            }
            tv1.append("Network capabilities:\n" + actNw.toString() + "\n");
            if(actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                tv2.append("Wifi connected!\n");
            }
            else if(actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                tv2.append("Mobile connected!\n");
            }
            return true;
        }
        else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            if(nwInfo == null) {
                return false;
            }
            tv1.append("Network info:\n" + nwInfo.toString());
            return nwInfo != null && nwInfo.isConnected();
        }
    }
}