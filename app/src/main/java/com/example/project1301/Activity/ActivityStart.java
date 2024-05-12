package com.example.project1301.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.example.project1301.Adapter.FragmentStartAdapter;
import com.example.project1301.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityStart extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        handler = new Handler();

        FragmentStartAdapter adapter = new FragmentStartAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < navigationView.getMenu().size(); i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }

                // Chọn mục tương ứng với vị trí của trang hiện tại trong ViewPager
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mHistory).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = viewPager.getAdapter().getCount();
                int nextItem = (currentItem + 1) % totalItems;
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 4000); // Đặt thời gian chuyển đổi ở đây (2 giây)
            }
        };

        // Bắt đầu chuyển đổi tự động khi Activity được tạo
        handler.postDelayed(runnable, 2000); // Bắt đầu sau 2 giây

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.mHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mHistory:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mSearch:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return  true;
            }
        });

//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            // Dừng chạy Runnable nếu nó đang chạy
//            handler.removeCallbacks(runnable);
//        }
    }
}