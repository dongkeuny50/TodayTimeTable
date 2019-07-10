package com.example.todaytimetable.ui.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private PageViewModel pageViewModel;
    private ArrayList<String> strarray = new ArrayList<String>();
    private View root;
    private ArrayList<String> list;
    private String jh = "";
    private String jm = "";
    private String jt = "";
    private String apm = "";
    String date = "";
    private String getTime;
    private ArrayList<String> hm = new ArrayList<String>();
    RecyclerView recyclerView;
    recyclerview adapter;

    static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);


    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = root.findViewById(R.id.recycler) ;
        list= new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext())) ;
        adapter = new recyclerview(list) ;
        recyclerView.setAdapter(adapter) ;

        Button create = root.findViewById(R.id.create);
        create.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                list.add("");
                adapter.notifyItemInserted(list.size());
            }
        });
        Button deletebutton = root.findViewById(R.id.all_delete);
        deletebutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){


                list= new ArrayList<>();
                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext())) ;
                adapter = new recyclerview(list) ;
                recyclerView.setAdapter(adapter) ;
            }
        });
        Button textdeletebutton = root.findViewById(R.id.text_delete);
        textdeletebutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                for (int i = 0; i < list.size(); i++) {
                    RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder)
                            recyclerView.findViewHolderForAdapterPosition(i);
                    if (null != holder) {
                        holder.itemView.setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.ampm).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.time).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.minute).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.textlines).setVisibility(View.VISIBLE);
                        View view = holder.itemView;
                        TextView ampm = view.findViewById(R.id.ampm);
                        EditText hour = view.findViewById(R.id.time);
                        EditText minute = view.findViewById(R.id.minute);
                        EditText textinput = view.findViewById(R.id.textlines);
                        ampm.setText("");
                        hour.setText("");
                        minute.setText("");
                        textinput.setText("");
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });






        Button textsavebutton = root.findViewById(R.id.save);
        textsavebutton.setOnClickListener(new Button.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v){


                recyclerView.postDelayed(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {

                        ArrayList<String> lists = new ArrayList<String>();
                        final String[] savedString = new String[1];
                        if(date == "") {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                            Date date = new Date(System.currentTimeMillis());
                            getTime = sdf.format(date);
                        }
                        else{getTime = date;}
                        savedString[0] = "[";

    String jampm = "";
    String jhour = "";
    String jminute = "";
    String jtextinput = "";
                        for (int i = 0; i < list.size(); i++) {
                            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder)
                                    recyclerView.findViewHolderForAdapterPosition(i);
                            if (null != holder) {
                                holder.itemView.setVisibility(View.VISIBLE);
                                holder.itemView.findViewById(R.id.ampm).setVisibility(View.VISIBLE);
                                holder.itemView.findViewById(R.id.time).setVisibility(View.VISIBLE);
                                holder.itemView.findViewById(R.id.minute).setVisibility(View.VISIBLE);
                                holder.itemView.findViewById(R.id.textlines).setVisibility(View.VISIBLE);
                                View view = holder.itemView;
                                TextView ampm = view.findViewById(R.id.ampm);
                                EditText hour = view.findViewById(R.id.time);
                                EditText minute = view.findViewById(R.id.minute);
                                EditText textinput = view.findViewById(R.id.textlines);
                                jampm = ampm.getText().toString();
                                jhour = hour.getText().toString();
                                jminute = minute.getText().toString();
                                jtextinput = textinput.getText().toString();
                                savedString[0] += "{'ampm':'" + jampm + "','hour':'" + jhour + "','minute':'" + jminute + "','textlines':'" + jtextinput + "'},";

                                lists.add(jampm);
                                lists.add(jhour);
                                lists.add(jminute);
                                lists.add(jtextinput);

                            }
                        }
                        savedString[0] += "]";
                        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getTime, savedString[0]);
                        editor.putString("date",getTime);
                        editor.commit();
                       Toast.makeText(root.getContext(), getTime + "날짜로 저장되었습니다.", Toast.LENGTH_SHORT).show();

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                        Date date = new Date(System.currentTimeMillis());


                    }
                },200);

            }

        });

        pageViewModel.getdate.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                date = s;
            }
        });//오늘날짜
        pageViewModel.getlists.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {//오늘시간표
                strarray = strings;
                if(strarray != null){
                    list.clear();
                    adapter.notifyDataSetChanged();

                    list= new ArrayList<>();
                    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext())) ;
                    adapter = new recyclerview(list) ;
                    recyclerView.setAdapter(adapter) ;
                    hm = strarray;
                    for(int i=0;i<hm.size()/4;i++) {
                        list.add("");
                        adapter.notifyItemInserted(list.size());
                    }
apm = "";
                    jh = "";
                    jm = "";
                    jt = "";
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < list.size(); i++) {
                                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder)
                                        recyclerView.findViewHolderForAdapterPosition(i);
                                if (null != holder) {
                                    holder.itemView.setVisibility(View.VISIBLE);
                                    holder.itemView.findViewById(R.id.ampm).setVisibility(View.VISIBLE);
                                    holder.itemView.findViewById(R.id.time).setVisibility(View.VISIBLE);
                                    holder.itemView.findViewById(R.id.minute).setVisibility(View.VISIBLE);
                                    holder.itemView.findViewById(R.id.textlines).setVisibility(View.VISIBLE);
                                    View view = holder.itemView;
                                    TextView ampm = view.findViewById(R.id.ampm);
                                    EditText hour = view.findViewById(R.id.time);
                                    EditText minute = view.findViewById(R.id.minute);
                                    EditText textinput = view.findViewById(R.id.textlines);
                                    apm = hm.get( i * 4);
                                    jh = hm.get(i * 4 + 1);
                                    jm = hm.get(i * 4 + 2);
                                    jt = hm.get(i * 4 + 3);
                                    ampm.setText(apm);
                                    hour.setText(jh);
                                    minute.setText(jm);
                                    textinput.setText(jt);
                                }}

                        }
                    },100);


                    adapter.notifyDataSetChanged();
                }
            }
        });
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = viewHolder.getAdapterPosition();
//                    // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                list.remove(viewHolder.getLayoutPosition());
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }
}