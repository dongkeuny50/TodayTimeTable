package com.example.todaytimetable.ui.main;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

@SuppressLint("Registered")
public class serviceD extends Service {
    MediaPlayer mediaPlayer;
    int startId;
    PowerManager.WakeLock wakeLock;
    boolean isRunning;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Build.VERSION.SDK_INT >= 26) {

            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            assert pm != null;
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "todaytimetable:WAKELOCK");
            wakeLock.acquire(); // WakeLock 깨우기
            //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
            Log.d(TAG, "onReceive: "+"Error");
            String hour = intent.getExtras().getString("hours");
            String minute = intent.getExtras().getString("minutes");
            String ampm = intent.getExtras().getString("ampm");
            String textline = intent.getExtras().getString("textline");
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notifilayout);
            remoteViews.setImageViewResource(R.id.create, R.mipmap.ic_launcher);
            remoteViews.setTextViewText(R.id.selectalarm, ampm + "  " + hour + ":"+minute);
            remoteViews.setTextViewText(R.id.alarmtextline, textline);

            NotificationManager notificationmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.parseInt(hour+minute), new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(this);
            builder
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setTicker("HETT")
                    .setNumber(1)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!")
                    .setCustomBigContentView(remoteViews)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationmanager != null;
            notificationmanager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
            notificationmanager.notify(Integer.parseInt(hour+minute), builder.build());}

        String getState = intent.getExtras().getString("state");

        assert getState != null;
        switch (getState) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        // 알람음 재생 X , 알람음 시작 클릭
        if(!this.isRunning && startId == 1) {

            mediaPlayer = MediaPlayer.create(this,R.raw.ouu);
            mediaPlayer.start();

            this.isRunning = true;
            this.startId = 0;
        }

        // 알람음 재생 O , 알람음 종료 버튼 클릭
        else if(this.isRunning && startId == 0) {

            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            this.isRunning = false;
            this.startId = 0;
        }

        // 알람음 재생 X , 알람음 종료 버튼 클릭
        else if(!this.isRunning && startId == 0) {

            this.isRunning = false;
            this.startId = 0;

        }

        // 알람음 재생 O , 알람음 시작 버튼 클릭
        else if(this.isRunning && startId == 1){

            this.isRunning = true;
            this.startId = 1;
        }

        else {
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        wakeLock.release(); // WakeLock 해제
        Log.d("onDestory() 실행", "서비스 파괴");

    }
}
