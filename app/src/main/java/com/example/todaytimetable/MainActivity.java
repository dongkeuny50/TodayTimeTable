package com.example.todaytimetable;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.todaytimetable.ui.main.PageViewModel;
import com.example.todaytimetable.ui.main.PlaceholderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todaytimetable.ui.main.SectionsPagerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    PageViewModel pageViewModel;
    String date = "";
    ViewPager viewPager;
    private ArrayList<String> timelist = new ArrayList<String>();
    public AsyncTask<Void,Void,Void> task;
    private static int ONE_MINUTE = 5626;
    private int sizeOfTime;
String nowtime;
private boolean setalarm = false;
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        final TextView textView = (TextView)findViewById(R.id.title);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        tabs.setupWithViewPager(viewPager);
        pageViewModel.getdate.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                date = s;
                textView.setText(date + " 오늘의 할일");
            }
        });
        if(date==""){
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                Date Today = new Date(System.currentTimeMillis());
                date = sdf.format(Today);
                textView.setText(date + "오늘의 할일");
        }

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
   public void moveview(){
        viewPager.setCurrentItem(0);

    }/*
    @SuppressLint("StaticFieldLeak")
    public void ShowTime(){
        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                while(true){
                    try{
                        publishProgress();
                        Thread.sleep(1000);

                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            @SuppressLint("SimpleDateFormat")
            @Override
            protected void onProgressUpdate(Void... progress){
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("H:m");
                nowtime = format.format(now);
                if(setalarm){
                    Log.d(this.getClass().getName(),"sucksexs");
                for(int i = 0; i <timelist.size()/4;i++){
                    if(timelist.get(i*4) == "오후"){
                        timelist.set(i * 4 + 1, String.valueOf((12+Integer.parseInt(timelist.get(i*4+1)
                        )
                        )
                        )
                        );}
                    else if(timelist.get(i*4) == "오전" && Integer.parseInt(timelist.get(i*4+1))== 12){
                        timelist.set(i * 4 + 1, String.valueOf((Integer.parseInt(timelist.get(i*4+1))-12)));
                    }
                    }
                sizeOfTime = timelist.size()/4;
                setalarm = false;
                }

                    for(int i = 0; i <sizeOfTime;i++){
                    if(nowtime.equals(timelist.get(i*4+1)+":"+timelist.get(i*4+2))){
                        notification(timelist.get(i*4+1)+"",timelist.get(i*4+2)+"",timelist.get(i*4+3)+"");
                        timelist.set(i*4,"");
                        timelist.set(i*4+1,"");
                        timelist.set(i*4+2,"");
                        timelist.set(i*4+3,"");

                        Log.d(this.getClass().getName(),"sucksexxx");
                    }


                }
            }
        };
        task.execute();
    }
public void notification(String hour, String minute, String textline){//푸시 알림을 보내기위해 시스템에 권한을 요청하여 생성
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setContentTitle("현재시각 :  " + hour + " : " + minute);
    builder.setContentText(textline + "을(를)하실 시간입니다.");
// 알림 표시
    NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
    }

// id값은
// 정의해야하는 각 알림의 고유한 int값
    notificationManager.notify(1, builder.build());
    }


public void setlists(ArrayList<String> strlist){
        timelist = new ArrayList<String>();
        timelist =strlist;
setalarm = true;
    Toast.makeText(this, "sibal", Toast.LENGTH_SHORT).show();
    }*/

}