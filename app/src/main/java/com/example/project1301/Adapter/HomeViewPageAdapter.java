package com.example.project1301.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project1301.fragment.FragmentHome;
import com.example.project1301.fragment.FragmentHomeFour;
import com.example.project1301.fragment.FragmentHomeOne;
import com.example.project1301.fragment.FragmentHomeTwo;
import com.example.project1301.fragment.FragmentHomethree;
import com.example.project1301.fragment.FragmentOne;
import com.example.project1301.fragment.FragmentThree;
import com.example.project1301.fragment.FragmentTwo;

public class HomeViewPageAdapter extends FragmentStatePagerAdapter {


    public HomeViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentHomeOne();
            case 1:
                return new FragmentHomeTwo();
            case 2:
                return new FragmentHomethree();
            case 3:
                return new FragmentHomeFour();
            default:return new FragmentOne();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
