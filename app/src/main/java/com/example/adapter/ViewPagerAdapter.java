package com.example.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fragment.FragmentHistory;
import com.example.fragment.FragmentHome;
import com.example.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentHistory();
            case 2:
                return new FragmentSearch();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
