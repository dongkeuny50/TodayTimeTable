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
import com.example.todaytimetable.ui.main.broadcastD;
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

import android.os.PowerManager;
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


import static androidx.constraintlayout.widget.Constraints.TAG;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    int count=0;
    PageViewModel pageViewModel;
public Context context;
    static PowerManager.WakeLock wakeLock;

    String date = "";
    ViewPager viewPager;
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
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

    }

    }
