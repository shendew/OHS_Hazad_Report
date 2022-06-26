package com.kingdew.ohshazadreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class home extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        webView=findViewById(R.id.webview);

        checkNetwork();
        LoadingDialog dialog=new LoadingDialog(this);
        dialog.startDialog();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.stopDialog();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                startActivity(new Intent(getApplicationContext(),NoConnection.class));
                finish();
                super.onReceivedError(view, request, error);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel= new NotificationChannel("OHS","OHS", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationChannel defaultvhannel= new NotificationChannel("default","Default", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager defaultmanager =getSystemService(NotificationManager.class);
            defaultmanager.createNotificationChannel(defaultvhannel);

        }

        if (getSharedPreferences("OHS",MODE_PRIVATE).getInt("count",999) ==999){
            SharedPreferences.Editor editor=getSharedPreferences("OHS",MODE_PRIVATE).edit();
            editor.putInt("count",0);
            editor.apply();
        }


        webView.loadUrl("https://dtsw.lk/bffmr/adminpanel.php");


    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {

        if (!isMyServiceRunning(NotificationService.class)){
            try {
                startService(new Intent(this, NotificationService.class));

            }catch (Exception e){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

            }
        }
        super.onStart();
    }

    private void checkNetwork() {

        NetworkUtils networkUtils=new NetworkUtils();
        if (networkUtils.isNetworkConnected(this)){

        }else{
            startActivity(new Intent(getApplicationContext(),NoConnection.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }

    }

}