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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private static final String ARG_SECTION_NUMBER = "section_number";
View root;
View view;
    ArrayList<String> list;
    static PlaceholderFragment newInstance() {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, 0);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
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


                for(int i=0;i < list.size();i++){
                    View view = recyclerView.findViewHolderForAdapterPosition(i).itemView;

                    EditText hour = view.findViewById(R.id.time);
                    EditText minute = view.findViewById(R.id.minute);
                    EditText textinput = view.findViewById(R.id.textlines);
                    hour.setText("");
                    minute.setText("");
                    textinput.setText("");

                }

              list.clear();
              adapter.notifyDataSetChanged();
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
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        });
        Button textloadbutton = root.findViewById(R.id.load);
        textloadbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str = "";
                String jhour = "";
                String jminute = "";
                String jtextline = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String getTime = sdf.format(date);
                ArrayList<String> hmt = new ArrayList<String>();
                    try {
                        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("pref",MODE_PRIVATE);
                        str = sharedPreferences.getString(getTime,"");
                        JSONArray jarray = new JSONArray(str); // JSONArray 생성
                        for(int i=0; i < jarray.length(); i++){
                            JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                            jhour = jObject.getString("hour");
                            jminute = jObject.getString("minute");
                            jtextline = jObject.getString("textlines");
                            hmt.add(jhour);
                            hmt.add(jminute);
                            hmt.add(jtextline);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                Toast.makeText(root.getContext(), Integer.toString(list.size()), Toast.LENGTH_SHORT).show();
                for(int i=0;i < list.size();i++) {
                    View view = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                    EditText hour = view.findViewById(R.id.time);
                    EditText minute = view.findViewById(R.id.minute);
                    EditText textinput = view.findViewById(R.id.textlines);
                    jhour = hmt.get(i*3);
                    jminute = hmt.get(i*3+1);
                    jtextline= hmt.get(i*3+2);

                    hour.setText(jhour);
                    minute.setText(jminute);
                    textinput.setText(jtextline);
                }

                adapter.notifyDataSetChanged();





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