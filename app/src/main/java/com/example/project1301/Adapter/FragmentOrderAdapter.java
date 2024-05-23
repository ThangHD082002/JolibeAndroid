package com.example.project1301.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project1301.fragment.FragmentCart;
import com.example.project1301.fragment.FragmentHome;
import com.example.project1301.fragment.FragmentNotify;
import com.example.project1301.fragment.FragmentOrderCancel;
import com.example.project1301.fragment.FragmentOrderDelivered;
import com.example.project1301.fragment.FragmentOrderPrepare;
import com.example.project1301.fragment.FragmentOrderWaitConfirm;
import com.example.project1301.fragment.FragmentOrderWaitDelivery;

public class FragmentOrderAdapter extends FragmentStatePagerAdapter {

    public FragmentOrderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentOrderWaitConfirm();
            case 1:
                return new FragmentOrderPrepare();
            case 2:
                return new FragmentOrderWaitDelivery();
            case 3:
                return new FragmentOrderDelivered();
            case 4:
                return new FragmentOrderCancel();
            default:return new FragmentOrderWaitConfirm();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Chờ xác nhận";
            case 1:return "Chuẩn bị";
            case 2:return "Chờ giao hàng";
            case 3:return "Đã giao";
            case 4:return "Đã hủy";
            default: return "Chờ xác nhận";
        }
    }
}
