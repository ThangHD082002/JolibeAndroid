package com.example.project1301.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.project1301.Adapter.HomeViewPageAdapter;
import com.example.project1301.Adapter.RecycleViewHomOneAdapter;
import com.example.project1301.Adapter.RecycleViewHomTwoAdapter;
import com.example.project1301.DiscountActivity;
import com.example.project1301.MenuActivity;
import com.example.project1301.OrderActivity;
import com.example.project1301.R;
import com.example.project1301.StoreActivity;
import com.example.project1301.model.Food;
import com.example.project1301.model.ItemHomeOne;
import com.example.project1301.spacing.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewHomOneAdapter.ItemListener{
    RecyclerView recyclerView, recyclerViewCombo;

    RecycleViewHomOneAdapter rhAdapter;

    RecycleViewHomTwoAdapter rhtAdapter;

    List<ItemHomeOne> list;

    List<Food> listCombo;
    private ViewPager viewPager;
    private Handler handler;
    private Runnable runnable;

    public FragmentHome() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        handler = new Handler();
        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getChildFragmentManager(), 4);
        viewPager.setAdapter(adapter);

        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = viewPager.getAdapter().getCount();
                int nextItem = (currentItem + 1) % totalItems;
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 2000); // Đặt thời gian chuyển đổi ở đây (2 giây)
            }
        };

        // Bắt đầu chuyển đổi tự động khi Activity được tạo
        handler.postDelayed(runnable, 2000); // Bắt đầu sau 2 giây

        list = new ArrayList<>();
        listCombo = new ArrayList<>();
        list.add(new ItemHomeOne(R.drawable.khuyen_mai, "Khuyển Mãi"));
        list.add(new ItemHomeOne(R.drawable.thuc_don, "Thực Đơn"));
        list.add(new ItemHomeOne(R.drawable.don_hang, "Đơn Hàng"));
        list.add(new ItemHomeOne(R.drawable.store, "Cửa Hàng"));

        listCombo.add(new Food(R.drawable.c_one, "COMBO 322K", "322K", "2 MIẾNG GÀ GIÒN + 2 MÌ Ý + 2 CƠM GÀ GIÒN...", "combo"));
        listCombo.add(new Food(R.drawable.c_two, "COMBO 499K", "499K", "TIỆC KIỂU MỚI, QUÀ CHUẨN GU 499K...","combo"));
        listCombo.add(new Food(R.drawable.c_three, "COMBO 599K", "599K", "TIỆC KIỂU MỚI, QUÀ CHUẨN GU 599K...", "combo"));
        listCombo.add(new Food(R.drawable.c_four, "COMBO 699K", "699K", "TIỆC KIỂU MỚI, QUÀ CHUẨN GU 699K...", "combo"));

        recyclerView =view.findViewById(R.id.recycleView);
        recyclerViewCombo = view.findViewById(R.id.recycleViewTwo);
        rhAdapter = new RecycleViewHomOneAdapter(view.getContext(), list);
        rhtAdapter = new RecycleViewHomTwoAdapter(view.getContext(), listCombo);
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(30);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager managerCombo = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerViewCombo.setLayoutManager(managerCombo);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerViewCombo.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(rhAdapter);
        recyclerViewCombo.setAdapter(rhtAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rhAdapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        ItemHomeOne it = rhAdapter.getItem(position);
        if(position == 0){
            String name = it.getName();
            Intent intent = new Intent(getActivity(), DiscountActivity.class);
            intent.putExtra("item", name);
            startActivity(intent);
        }
        if(position == 1){
            String name = it.getName();
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            intent.putExtra("item", name);
            startActivity(intent);
        }
        if(position == 2){
            String name = it.getName();
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            intent.putExtra("item", name);
            startActivity(intent);
        }
        if(position == 3){
            String name = it.getName();
            Intent intent = new Intent(getActivity(), StoreActivity.class);
            intent.putExtra("item", name);
            startActivity(intent);
        }
    }
}
