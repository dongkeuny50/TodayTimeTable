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
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todaytimetable.ui.main.SectionsPagerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    public static Context mcontext;
AlarmManager[] temp;
Intent[] tempintent;
PendingIntent[] tempending;
    Context context;
    int count=0;
    PageViewModel pageViewModel;
    String date = "";
    ViewPager viewPager;
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        this.context = this;
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

    }
    public void doalarm(ArrayList<String> strlist){
        count = strlist.size()/4;
try{
temp = new AlarmManager[strlist.size()/4];
tempending = new PendingIntent[strlist.size()/4];
tempintent = new Intent[strlist.size()/4];
        AlarmManager alarm_manager =(AlarmManager) getSystemService(ALARM_SERVICE);
        for(int i = 0; i < strlist.size()/4;i++){
            if(strlist.get(i*4+1)!= "" && strlist.get(i*4+2) != ""){
            // 알람매니저 설정
            // 타임피커 설정
            // Calendar 객체 생성
            Calendar calendar = Calendar.getInstance();

            // 알람리시버 intent 생
            Intent my_intent = new Intent(this.context, Alarm_Reciver.class);
        // calendar에 시간 셋팅
        int tempampm = 0;
        if(strlist.get(i * 4).equals("오후")){
            tempampm = 12;
        }
        if(strlist.get(i * 4).equals("오전")){
            if(Integer.parseInt(strlist.get(i*4+1)) == 12){}
                    tempampm = -12;
                }
        int temphour = tempampm + Integer.parseInt(strlist.get(i*4+1));
        Log.d("",String.valueOf(temphour));
        int tempminute = Integer.parseInt(strlist.get(i*4+2));
        calendar.set(Calendar.HOUR_OF_DAY, temphour);
        calendar.set(Calendar.MINUTE, tempminute);

        // 시간 가져옴
            final int intent_id= (int) System.currentTimeMillis();
        // reveiver에 string 값 넘겨주기
        my_intent.putExtra("state","alarm on");
        my_intent.putExtra("ID",i);

        my_intent.putExtra("Time",String.valueOf(strlist.get(i*4+1)) + " : "+strlist.get(i*4+2));
        my_intent.putExtra("Todo",strlist.get(i*4+3)+"");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,   i, my_intent,
                PendingIntent.FLAG_ONE_SHOT);
            // 알람셋팅
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);
            temp[i] = alarm_manager;
            tempintent[i] = my_intent;
            tempending[i] = pendingIntent;

            }
    }
    }catch(NumberFormatException e){
    Toast.makeText(this, "정확한 숫자를 입력하세요", Toast.LENGTH_SHORT).show();
    
}}
    public void cancelall(int i){

        temp[i].cancel(tempending[i]);

        tempintent[i].putExtra("state","alarm off");
        // 알람취소
        sendBroadcast(tempintent[i]);
    }
    public void cancelallOnDelete(){
        if(count != 0){
for(int i = 0; i < count; i++){
        temp[i].cancel(tempending[i]);

        tempintent[i].putExtra("state","alarm off");
        // 알람취소
        sendBroadcast(tempintent[i]);
}
    }}
        
    }
