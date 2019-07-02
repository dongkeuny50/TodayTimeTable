package com.example.todaytimetable.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });
    private MutableLiveData<ArrayList<String>> lists = new MutableLiveData<>();

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public void setLists(ArrayList<String> arr){
        lists.postValue(arr);

    }
    public LiveData<ArrayList<String>> getlists = Transformations.map(lists, new Function<ArrayList<String>, ArrayList<String>>() {
        @Override
        public ArrayList<String> apply(ArrayList<String> input) {
            return input;
        }
    });
}