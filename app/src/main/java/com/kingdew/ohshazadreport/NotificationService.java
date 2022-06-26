package com.kingdew.ohshazadreport;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationService extends Service {

    int count;
    public NotificationService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        count=getSharedPreferences("OHS",MODE_PRIVATE).getInt("count",999);
        startForeground();
        getNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getNotification() {

        FirebaseDatabase.getInstance().getReference("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        int cCount=(int) snapshot.getChildrenCount();

                        if (cCount>count){

                            String title="New Hazad Report";
                            String desc="New Hazad Report Submitted";


                            NotificationCompat.Builder builder=new NotificationCompat.Builder(NotificationService.this,"OHS");
                            builder.setContentText(title);
                            builder.setContentTitle(desc);

                            builder.setPriority(NotificationCompat.PRIORITY_MAX);
                            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                            builder.setAutoCancel(false);


                            Intent intent=new Intent(NotificationService.this,home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            PendingIntent pendingIntent=PendingIntent.getActivity(NotificationService.this,2001,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);


                            NotificationManagerCompat managerCompat= NotificationManagerCompat.from(NotificationService.this);
                            managerCompat.notify(1,builder.build());

                            SharedPreferences.Editor editor=getSharedPreferences("OHS",MODE_PRIVATE).edit();
                            editor.putInt("count",(int) snapshot.getChildrenCount());
                            editor.apply();
                        }





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void startForeground() {
        Intent notificationIntent = new Intent(this, home.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(101, new NotificationCompat.Builder(this,
                "default") // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "Service was stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();

    }
}