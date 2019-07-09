package com.example.todaytimetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Alarm_Reciver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        // intent로부터 전달받은 string
        String get_your_string = intent.getExtras().getString("state");
int get_your_id = intent.getExtras().getInt("ID");
        String gettime = intent.getExtras().getString("Time"+get_your_id);
        String gettodo = intent.getExtras().getString("Todo"+get_your_id);
        // RingtonePlayingService 서비스 intent 생성
        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        // RingtonePlayinService로 extra string값 보내기
        service_intent.putExtra("state", get_your_string);
        service_intent.putExtra("ID",get_your_id);
        service_intent.putExtra("Time"+get_your_id,gettime);
        service_intent.putExtra("Todo"+get_your_id,gettodo);
        // start the ringtone service

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        //    this.context.startForegroundService(service_intent);
       // }else{
           this.context.startService(service_intent);
       // }
    }
}
