package com.example.pnattawut.practice_androidservice.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ServiceCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.pnattawut.practice_androidservice.Main2Activity;
import com.example.pnattawut.practice_androidservice.MainActivity;
import com.example.pnattawut.practice_androidservice.R;
import com.example.pnattawut.practice_androidservice.model.Word;

/**
 * Created by PNattawut on 20-Aug-17.
 */

public class WordGeneratorService extends IntentService {

    private Handler naHandler;
    private Runnable runnable;

    private int startId;

    private BroadcastReceiver serviceBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopService(new Intent(WordGeneratorService.this,WordGeneratorService.class));
            Log.d("on","Do or Not");
        }
    };

    public WordGeneratorService() {
        super("TEST GEN");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("on","Create");
        naHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Word.getInstance().genWord();
                Log.d("on", Word.getInstance().toString());
                Toast.makeText(WordGeneratorService.this, "Running... "+Word.getInstance().getIndex(), Toast.LENGTH_SHORT).show();
                naHandler.postDelayed(this, 3000L);
            }
        };
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.d("on","Start");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("on","StartCommand");
        this.startId = startId;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo info : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (info.service.getClassName().equals(WordGeneratorService.class.getName())) {
                //Toast.makeText(this, "Already Started", Toast.LENGTH_LONG).show();
                //return START_STICKY;
            }
        }

        if (Word.getInstance().getWord() == null) {
            naHandler.post(runnable);
        }

        registerReceiver(serviceBroadcastReceiver, new IntentFilter("stopSelf"));
        PendingIntent pdIntent = PendingIntent.getBroadcast(this, 0, new Intent("stopSelf"), PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pdIntent)
                .setContentTitle("Service Running")
                .setContentText("Tap to Stop...").build();
        NotificationManager mng = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mng.notify(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("on","Destroy");
        naHandler.removeCallbacks(runnable);
        unregisterReceiver(serviceBroadcastReceiver);
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("on","Bind");
        return super.onBind(intent);
    }
}
