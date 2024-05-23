package com.example.project1301;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1301.Adapter.FragmentMainAdapter;
import com.example.project1301.model.Data;
import com.example.project1301.model.Food;
import com.example.project1301.model.UserData;
import com.example.project1301.model.UserOrder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tName;


    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    private UserData userData;

    private UserOrder userOrder;

    private String name="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        pushFood();
        tName = findViewById(R.id.tname);
        navigationView = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPager);
        Intent intent = getIntent();
        userOrder = (UserOrder) intent.getSerializableExtra("item");
        if(userOrder != null){
            name = userOrder.getName();
            tName.setText(name);
        }
        FragmentMainAdapter adapter = new FragmentMainAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
                        navigationView.getMenu().findItem(R.id.mCart).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mNotify).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.mHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mCart:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mNotify:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return  true;
            }
        });
        tName.setOnClickListener(this);
    }

    private void pushFood() {

    }


    @Override
    public void onClick(View v) {
        if(v == tName){
            Intent intent2 = new Intent(this, AccountActivity.class);
            intent2.putExtra("item", name);
            startActivity(intent2);
        }
    }
}