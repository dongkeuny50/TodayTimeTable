package com.example.todaytimetable.ui.main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class broadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        this.context = context;
        // intent로부터 전달받은 string
        String get_yout_string = intent.getExtras().getString("state");
        // RingtonePlayingService 서비스 intent 생성
        Intent service_intent = new Intent(context, serviceD.class);

        // RingtonePlayinService로 extra string값 보내기
        service_intent.putExtra("state", get_yout_string);
        service_intent.putExtra("hours",intent.getExtras().getString("hours"));
        service_intent.putExtra("minutes",intent.getExtras().getString("minutes"));
        service_intent.putExtra("ampm",intent.getExtras().getString("ampm"));
        service_intent.putExtra("textline",intent.getExtras().getString("textline"));
        // start the ringtone service

       // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
         //   this.context.startForegroundService(service_intent);
       // }else{
            this.context.startService(service_intent);
        //}



    }
}