package com.example.todaytimetable.ui.main;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

@SuppressLint("Registered")
public class serviceD extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }
}
