package com.example.todaytimetable.ui.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class recyclerview extends RecyclerView.Adapter<recyclerview.ViewHolder> {
    private ArrayList<String> mData = null ;
int num;
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            itemView.findViewById(R.id.LinearLayout);
        }

    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    recyclerview(ArrayList<String> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public recyclerview.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        final View view = (View) inflater.inflate(R.layout.item_layout, parent, false);
        final EditText editText = (EditText) view.findViewById(R.id.textlines);
        final EditText hour = (EditText)view.findViewById(R.id.time);
        final EditText minute = (EditText)view.findViewById(R.id.minute);
        final TextView ampm = (TextView)view.findViewById(R.id.ampm);
        ampm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ampm.getText().toString() == "오전"){
                ampm.setText("오후");}
                else{ampm.setText("오전");}
            }
        });
        hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int h;
                boolean changer = true;
                if(hour.getText()!=null) {
                    try{
                    h = Integer.parseInt(hour.getText().toString());
                    if (h > 12 && changer) {
                        ampm.setText("오후");
                        hour.setText(String.valueOf(h - 12));
                        changer = false;
                        minute.requestFocus();
                    }
                    else if(hour.length() == 2){
                        if(h<10){
                        hour.setText(hour.getText().toString().substring(1));
                            ampm.setText("오전");
                            minute.requestFocus();}
                        else{
                            ampm.setText("오후");
                            minute.requestFocus();}

                    }
                    }catch (NumberFormatException e){
                    }
                }

            }

        });

        hour.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    try{
                   int h = Integer.parseInt(hour.getText().toString());

                        if(h <= 12){
                            ampm.setText("오전");
                            minute.requestFocus();
                        }
                    }
                    catch (NumberFormatException e){

                        minute.requestFocus();
                    }
                    ampm.setText("오전");
                    return true;
                }
                return false;
            }
        });
        minute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(minute.length()==2){
                    editText.requestFocus();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            boolean trigger = true;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText.length() == 8 && trigger){
                    editText.append("\n");
                    trigger = false;
                }
                if(editText.length() <8 && !trigger){
                    trigger = true;
                }

            }
        });
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        Switch alarmswitch = (Switch)view.findViewById(R.id.selectalarm);
        alarmswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AlarmManager am = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(view.getContext(), broadcastD.class);
                    int hours = 0;
                    int minutes = 0;
                    int ampmplus = 0;
                    String strhour = String.valueOf(hour.getText());
                    String strminute = String.valueOf(minute.getText());
                    String strampm = String.valueOf(ampm.getText());
                    String strtextline = String.valueOf(editText.getText());
                    intent.putExtra("hours",strhour);
                    intent.putExtra("minutes",strminute);
                    intent.putExtra("ampm",strampm);
                    intent.putExtra("textline",strtextline);
                    if(ampm.getText().toString().equals("오후")){if(Integer.parseInt(String.valueOf(hour.getText())) != 12){ampmplus = 12;}
                    else if(Integer.parseInt(String.valueOf(hour.getText())) == 12){ampmplus = -12;} }
                    if(ampm.getText().toString().equals("오전")){if(Integer.parseInt(String.valueOf(hour.getText())) == 12){ampmplus = -12;} }
                    try{
                    hours = Integer.parseInt(String.valueOf(hour.getText()))+ampmplus;
                    minutes = Integer.parseInt(String.valueOf(minute.getText()));

                        PendingIntent sender = PendingIntent.getBroadcast(view.getContext(), num, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hours, minutes, 0);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                            assert am != null;
                            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                            assert am != null;
                            am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                        } else {
                            assert am != null;
                            am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                        }
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(context, "올바른 시간을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    AlarmManager am = (AlarmManager)view.getContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(view.getContext(),broadcastD.class);
                    PendingIntent sender = PendingIntent.getBroadcast(
                            view.getContext(), num, intent, PendingIntent.FLAG_NO_CREATE);
                    if (sender != null) {
                        am.cancel(sender);
                        sender.cancel();}
                    Log.d(TAG, "onCheckedChanged: "+num);
                }
            }
        });
        return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull recyclerview.ViewHolder holder, int position) {
        String text = mData.get(position);
        num = position;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }




}
