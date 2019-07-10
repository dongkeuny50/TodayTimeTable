package com.example.todaytimetable.ui.main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class broadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        Log.d(TAG, "onReceive: "+"Error");
        String hour = intent.getExtras().getString("hours");
        String minute = intent.getExtras().getString("minutes");
        String ampm = intent.getExtras().getString("ampm");
        String textline = intent.getExtras().getString("textline");
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setTicker("HETT")
                .setNumber(1)
                .setContentTitle(ampm + "  " + hour + ":"+minute)
                .setContentText(textline)
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
        notificationmanager.notify(1, builder.build());

    }
}