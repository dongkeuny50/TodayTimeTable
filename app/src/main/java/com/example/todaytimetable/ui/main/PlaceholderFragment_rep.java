package com.example.todaytimetable.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment_rep extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    String words = "";
    View root;
    private PageViewModel pageViewModel;
String finalword = "";
    static PlaceholderFragment_rep newInstance(int index) {
        PlaceholderFragment_rep fragment = new PlaceholderFragment_rep();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        int index = 2;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_rep, container, false);
        Button button = (Button)root.findViewById(R.id.load);

        final MaterialCalendarView materialCalendarView = (MaterialCalendarView)root.findViewById(R.id.calendarView);
        materialCalendarView.addDecorators(new onDateDecorator());

                SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("pref",MODE_PRIVATE);

                Map<String,?> keys = sharedPreferences.getAll();

                for(Map.Entry<String,?> entry : keys.entrySet()){
                    try {
                        materialCalendarView.addDecorators(new onSavedDateDecorator(entry.getKey()));
                    } catch (ParseException t) {

                        t.printStackTrace();
                    }
                }

    new Thread(new Runnable() {
        @Override
        public void run() {
            while(true) {
                root.invalidate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener(){
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected){
                finalword = date.getYear()+ "-"+(date.getMonth()+1)+"-"+date.getDay();
            }
                                              });
                button.setOnClickListener(new Button.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v){
                ArrayList<String> hmt = new ArrayList<>();
                try {
                    String jhour = "";
                    String jminute = "";
                    String jtextline = "";
                    String japm = "";
                    words = "";
                SharedPreferences spf = root.getContext().getSharedPreferences("pref",MODE_PRIVATE);
                words = spf.getString(finalword,"");

                JSONArray jarray = new JSONArray(words); // JSONArray 생성
                    for(int i=0; i < jarray.length()-1; i++){

                        JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                        japm = jObject.getString("ampm");
                        jhour = jObject.getString("hour");
                        jminute = jObject.getString("minute");
                        jtextline = jObject.getString("textlines");
                        hmt.add(japm);
                        hmt.add(jhour);
                        hmt.add(jminute);
                        hmt.add(jtextline);
                    }

                    pageViewModel.setLists(new ArrayList<String>());
                    pageViewModel.setLists(hmt);
                    pageViewModel.setDate(finalword);
                    Toast.makeText(root.getContext(), " 불러오기 성공", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("pref",MODE_PRIVATE);

                    Map<String,?> keys = sharedPreferences.getAll();

                            for(Map.Entry<String,?> entry : keys.entrySet()){
                        Log.d("map values",entry.getKey() + ": " +
                                entry.getValue().toString());
                        try {
                            materialCalendarView.addDecorators(new onSavedDateDecorator(entry.getKey()));
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                    }

                    ((MainActivity)getActivity()).moveview();

                } catch (JSONException s) {
                    pageViewModel.setLists(new ArrayList<String>());
                    pageViewModel.setDate(finalword);
                    Toast.makeText(root.getContext(),"불러오기 실패, "+finalword+"에 새롭게 생성",Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("pref",MODE_PRIVATE);
                    Map<String,?> keys = sharedPreferences.getAll();

                    for(Map.Entry<String,?> entry : keys.entrySet()){
                        try {
                            materialCalendarView.addDecorators(new onSavedDateDecorator(entry.getKey()));
                        } catch (ParseException t) {

                            t.printStackTrace();
                        }
                    }
                    s.printStackTrace();
                    ((MainActivity)getActivity()).moveview();
                }

                ((MainActivity)getActivity()).moveview();
            }
        });
    Button delete = (Button)root.findViewById(R.id.delete);
    delete.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {

            SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("pref",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(finalword != "") {

                editor.remove(finalword);
                editor.commit();
                    materialCalendarView.removeDecorators();
                Map<String,?> keys = sharedPreferences.getAll();

                materialCalendarView.addDecorators(new onDateDecorator());
                for(Map.Entry<String,?> entry : keys.entrySet()){
                    if(entry.getKey() == finalword){continue;}
                    Log.d("map values",entry.getKey() + ": " +
                            entry.getValue().toString());
                    try {
                        materialCalendarView.addDecorators(new onSavedDateDecorator(entry.getKey()));
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                }
                    Toast.makeText(root.getContext(), "삭제 완료.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(root.getContext(), "저장 내용이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    });
        return root;
    }

}