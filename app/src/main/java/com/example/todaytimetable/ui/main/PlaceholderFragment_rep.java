package com.example.todaytimetable.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment_rep extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    View root;
    private PageViewModel pageViewModel;
String finalword;
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
            Bundle savedInstanceState) {root = inflater.inflate(R.layout.fragment_rep, container, false);
        Button button = (Button)root.findViewById(R.id.load);
        CalendarView calendarView = (CalendarView)root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                finalword = String.valueOf(year)+ "-" + String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
            }
        });
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String words = "";
                ArrayList<String> hmt = new ArrayList<>();
                try {
                    String jhour = "";
                    String jminute = "";
                    String jtextline = "";
                SharedPreferences spf = root.getContext().getSharedPreferences("pref",MODE_PRIVATE);
               if(finalword != ""){words = spf.getString(finalword,"");}else{
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                   Date date = new Date(System.currentTimeMillis());
                   words = sdf.format(date);}
                JSONArray jarray = new JSONArray(words);; // JSONArray 생성
                    for(int i=0; i < jarray.length()-1; i++){

                        JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                        jhour = jObject.getString("hour");
                        jminute = jObject.getString("minute");
                        jtextline = jObject.getString("textlines");
                        hmt.add(jhour);
                        hmt.add(jminute);
                        hmt.add(jtextline);
                    }

                pageViewModel.setLists(hmt);
                    Toast.makeText(root.getContext(), "성공", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(root.getContext(),"불러오기 실패: 저장파일 없음",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        return root;
    }
}