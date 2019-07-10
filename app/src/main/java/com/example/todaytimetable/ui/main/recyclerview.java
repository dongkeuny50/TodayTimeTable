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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todaytimetable.MainActivity;
import com.example.todaytimetable.R;

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
                    PendingIntent sender = PendingIntent.getBroadcast(view.getContext(), num, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.clear();
                    int hours = Integer.parseInt(String.valueOf(hour.getText()));
                    int minutes = Integer.parseInt(String.valueOf(minute.getText()));
                    Log.d(TAG, "onCheckedChanged: " + hours + minutes);
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hours, minutes, 0);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                    } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                        am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                    } else {
                        am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
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
