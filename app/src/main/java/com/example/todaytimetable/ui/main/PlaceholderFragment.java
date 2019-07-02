package com.example.todaytimetable.ui.main;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class PlaceholderFragment extends Fragment {

    PageViewModel pageViewModel;
    ArrayList<String> when = new ArrayList<String>();
    private static final String ARG_SECTION_NUMBER = "section_number";
View root;
View view;
    ArrayList<String> list;
    String jh = "";
    String jm = "";
    String jt = "";
    ArrayList<String> hm = new ArrayList<String>();



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
        list= new ArrayList<>();
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        final RecyclerView recyclerView = root.findViewById(R.id.recycler) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext())) ;
        final recyclerview adapter = new recyclerview(list) ;
        recyclerView.setAdapter(adapter) ;
        if(when.size() != 0){
        Toast.makeText(root.getContext(), when.get(0), Toast.LENGTH_SHORT).show();}

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
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i < list.size();i++){
                            View view = recyclerView.findViewHolderForAdapterPosition(i).itemView;

                            EditText hour = view.findViewById(R.id.time);
                            EditText minute = view.findViewById(R.id.minute);
                            EditText textinput = view.findViewById(R.id.textlines);
                            hour.setText("");
                            minute.setText("");
                            textinput.setText("");

                        }

                    }
                },200);

recyclerView.postDelayed(new Runnable() {
    @Override
    public void run() {
        for(int i=list.size()-1;i >=0 ;i--){
            list.remove(i);
            adapter.notifyItemRemoved(i);
        }

    }
},100);
            }
        });
        Button textdeletebutton = root.findViewById(R.id.text_delete);
        textdeletebutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                for(int i=0;i < list.size();i++){
                    View view = recyclerView.findViewHolderForAdapterPosition(i).itemView;

                    EditText hour = view.findViewById(R.id.time);
                    EditText minute = view.findViewById(R.id.minute);
                    EditText textinput = view.findViewById(R.id.textlines);
                    hour.setText("");
                    minute.setText("");
                    textinput.setText("");

                }
                adapter.notifyDataSetChanged();
            }
        });






        Button textsavebutton = root.findViewById(R.id.save);
        textsavebutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                        Date date = new Date(System.currentTimeMillis());
                        String getTime = sdf.format(date);

                        String savedString = "[";
                        for(int i=0;i < list.size();i++) {

                            View view = recyclerView.findViewHolderForAdapterPosition(i).itemView;

                            EditText hour = view.findViewById(R.id.time);
                            EditText minute = view.findViewById(R.id.minute);
                            EditText textinput = view.findViewById(R.id.textlines);
                            savedString += "{'hour':'" +hour.getText().toString()+"','minute':'"+ minute.getText().toString()+"','textlines':'" + textinput.getText().toString()+"'},";
                        }
                        savedString += "]";
                        SharedPreferences sharedPreferences =root.getContext().getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getTime,savedString);
                        editor.commit();
                        Toast.makeText(root.getContext(), getTime + "날짜로 저장되었습니다.", Toast.LENGTH_SHORT).show();


                    }
                },50);
            }
        });
        pageViewModel.getlists.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                when = strings;
                if(when != null){
                    list.clear();
                    adapter.notifyDataSetChanged();
                    hm = when;
                    for(int i=0;i<hm.size()/3;i++) {
                        list.add("");
                        adapter.notifyItemInserted(list.size());
                    }
                    jh = "";
                    jm = "";
                    jt = "";
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < list.size(); i++) {
                                View view = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                EditText hour = view.findViewById(R.id.time);
                                EditText minute = view.findViewById(R.id.minute);
                                EditText textinput = view.findViewById(R.id.textlines);
                                jh = hm.get(i * 3);
                                jm = hm.get(i * 3 + 1);
                                jt = hm.get(i * 3 + 2);

                                hour.setText(jh);
                                minute.setText(jm);
                                textinput.setText(jt);
                            }

                        }
                    },50);


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