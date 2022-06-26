package com.kingdew.ohshazadreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkNetwork();


    }

    private void checkNetwork() {

        NetworkUtils networkUtils=new NetworkUtils();
        if (networkUtils.isNetworkConnected(this)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),home.class));
                    finish();
                }
            },1000);
        }else{
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }

    }
}