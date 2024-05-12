package com.example.project1301.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project1301.fragment.FragmentOne;
import com.example.project1301.fragment.FragmentThree;
import com.example.project1301.fragment.FragmentTwo;

public class FragmentStartAdapter extends FragmentStatePagerAdapter {

    public FragmentStartAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            default:return new FragmentOne();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
