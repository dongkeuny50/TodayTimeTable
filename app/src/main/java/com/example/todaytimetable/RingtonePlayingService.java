package com.example.todaytimetable;

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
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer;
    int startId;
    boolean isRunning;

    Context context;

    String gettime="def";

    String gettodo="nothing";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    int deal = 0;
    int id = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String getState = intent.getExtras().getString("state");
        id = intent.getIntExtra("ID",0);
        gettime= intent.getExtras().getString("Time");
        gettodo= intent.getExtras().getString("Todo");
        // intent로부터 전달받은 string
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "default";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "알림",
                    NotificationManager.IMPORTANCE_DEFAULT);
            PendingIntent notifyintent= PendingIntent.getActivity(getApplicationContext(),0,new Intent(),0);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            //알림바 클릭시 이동을 위한 Intent
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(gettime)
                    .setContentText(gettodo)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(notifyintent)
                    .setAutoCancel(true)
                    .build();
            deal += 1;
            startForeground(deal, notification);
        }
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
            Random generator = new Random();
            int num= generator.nextInt(2);
            switch (num){
                case 0:
                    mediaPlayer = MediaPlayer.create(this,R.raw.sound2_hap);
                    break;
                case 1:
                    mediaPlayer = MediaPlayer.create(this,R.raw.sound3_hap);
                    break;
                    default:

                        mediaPlayer = MediaPlayer.create(this,R.raw.sound2_hap);
                        break;
            }
            mediaPlayer.start();

            this.isRunning = true;
            this.startId = 0;

            stopSelf();

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


        /*new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ((MainActivity)MainActivity.mcontext).cancelall(id);
                final NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
                mNotificationManager.cancel(0);
            }
        });*/
        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)MainActivity.mcontext).cancelall(id);
        Log.d("onDestory() 실행", "서비스 파괴");

    }
}