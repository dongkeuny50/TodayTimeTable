package com.example.todaytimetable.ui.main;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todaytimetable.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private PageViewModel pageViewModel;
    private ArrayList<String> strarray = new ArrayList<String>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View root;
    View view;
    private ArrayList<String> list;
    private String jh = "";
    private String jm = "";
    private String jt = "";
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
                adapter.notifyDataSetChanged();
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
                        final String[] savedString = new String[1];
                        if(date == "") {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                            Date date = new Date(System.currentTimeMillis());
                            getTime = sdf.format(date);
                        }
                        else{getTime = date;}
                        savedString[0] = "[";


                        for (int i = 0; i < list.size(); i++) {
                            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder)
                                    recyclerView.findViewHolderForAdapterPosition(i);
                            if (null != holder) {
                                holder.itemView.setVisibility(View.VISIBLE);
                                holder.itemView.findViewById(R.id.time).setVisibility(View.VISIBLE);
                                holder.itemView.findViewById(R.id.minute).setVisibility(View.VISIBLE);
                                holder.itemView.findViewById(R.id.textlines).setVisibility(View.VISIBLE);
                                View view = holder.itemView;
                                EditText hour = view.findViewById(R.id.time);
                                EditText minute = view.findViewById(R.id.minute);
                                EditText textinput = view.findViewById(R.id.textlines);
                                savedString[0] += "{'hour':'" + hour.getText().toString() + "','minute':'" + minute.getText().toString() + "','textlines':'" + textinput.getText().toString() + "'},";
                            }
                        }
                        savedString[0] += "]";
                        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getTime, savedString[0]);
                        editor.putString("date",getTime);
                        editor.commit();
                        Toast.makeText(root.getContext(), getTime + "날짜로 저장되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                },200);

            }
        });
        pageViewModel.getdate.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                date = s;
            }
        });
        pageViewModel.getlists.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                strarray = strings;
                if(strarray != null){
                    list.clear();
                    adapter.notifyDataSetChanged();

                    list= new ArrayList<>();
                    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext())) ;
                    adapter = new recyclerview(list) ;
                    recyclerView.setAdapter(adapter) ;
                    hm = strarray;
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
                                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder)
                                        recyclerView.findViewHolderForAdapterPosition(i);
                                if (null != holder) {
                                    holder.itemView.setVisibility(View.VISIBLE);
                                    holder.itemView.findViewById(R.id.time).setVisibility(View.VISIBLE);
                                    holder.itemView.findViewById(R.id.minute).setVisibility(View.VISIBLE);
                                    holder.itemView.findViewById(R.id.textlines).setVisibility(View.VISIBLE);
                                    View view = holder.itemView;
                                    EditText hour = view.findViewById(R.id.time);
                                    EditText minute = view.findViewById(R.id.minute);
                                    EditText textinput = view.findViewById(R.id.textlines);
                                    jh = hm.get(i * 3);
                                    jm = hm.get(i * 3 + 1);
                                    jt = hm.get(i * 3 + 2);

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