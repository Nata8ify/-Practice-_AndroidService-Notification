package com.example.pnattawut.practice_androidservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pnattawut.practice_androidservice.service.WordGeneratorService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationCompat.Builder noti = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Simple Notification...")
                .setContentText("Now you See Me");
       /* Intent rsIntent = new Intent(this, Main2Activity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Main2Activity.class);
        stackBuilder.addNextIntent(rsIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        noti.setContentIntent(pendingIntent);*/
        NotificationManager nfm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //nfm.notify(1, noti.build());
    }

    public void start(View v){
        Intent wordService = new Intent(this, WordGeneratorService.class);
        this.startService(wordService);
    }
}
