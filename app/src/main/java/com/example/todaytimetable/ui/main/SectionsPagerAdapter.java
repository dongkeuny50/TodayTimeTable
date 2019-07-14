package com.example.todaytimetable.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.todaytimetable.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.timetable, R.string.repository,R.string.setting};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0:
                return new PlaceholderFragment();
            case 1:
                return PlaceholderFragment_rep.newInstance(1);
            case 2:
                return PlaceholderFragment_setting.newInstance(2);
            default:
                return null;
        }
    }
    @Override
    public int getItemPosition(Object object){
        if(object.getClass() == PlaceholderFragment_rep.class){
            return POSITION_NONE;
        }
        else{
            return super.getItemPosition(object);

            }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

}