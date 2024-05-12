package com.example.project1301.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project1301.fragment.FragmentCart;
import com.example.project1301.fragment.FragmentHome;
import com.example.project1301.fragment.FragmentNotify;
import com.example.project1301.fragment.FragmentOne;
import com.example.project1301.fragment.FragmentThree;
import com.example.project1301.fragment.FragmentTwo;

public class FragmentMainAdapter extends FragmentStatePagerAdapter {

    public FragmentMainAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentCart();
            case 2:
                return new FragmentNotify();
            default:return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
