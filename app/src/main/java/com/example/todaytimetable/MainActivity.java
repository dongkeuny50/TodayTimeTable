package com.example.todaytimetable;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.todaytimetable.ui.main.PageViewModel;
import com.example.todaytimetable.ui.main.PlaceholderFragment;
import com.example.todaytimetable.ui.main.PlaceholderFragment_rep;
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
import android.provider.Settings;
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
    SectionsPagerAdapter  sectionsPagerAdapter;
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isWhiteListing = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            assert pm != null;
            isWhiteListing = pm.isIgnoringBatteryOptimizations(this.getPackageName());
        }
if(!isWhiteListing) {
    AlertDialog.Builder setdialog = new AlertDialog.Builder(MainActivity.this);
    setdialog.setTitle("권한이 필요합니다.")
            .setMessage("어플을 사용하기 위해서는 본 어플을 \"배터리 사용량 최적화\" 목록에서 제외하는 권한이 필요합니다. 계속하시겠습니까?")
            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent  = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:"+ context.getPackageName()));
                    context.startActivity(intent);
                }
            })
            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "권한 설정을 취소했습니다.", Toast.LENGTH_SHORT).show();
                }
            })
            .create()
            .show();

}
        context = this;
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
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
    public void refreshview(){
        sectionsPagerAdapter.notifyDataSetChanged();
    }
    }
