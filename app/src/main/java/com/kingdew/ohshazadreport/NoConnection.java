package com.kingdew.ohshazadreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NoConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtils networkUtils=new NetworkUtils();
                if (networkUtils.isNetworkConnected(getApplicationContext())){
                    startActivity(new Intent(getApplicationContext(),home.class));
                    finish();
                }
            }
        });
    }
}