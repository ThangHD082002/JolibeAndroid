package com.example.project1301;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1301.Adapter.RecycleViewMenuAdapter;
import com.example.project1301.model.Category;
import com.example.project1301.model.ItemHomeOne;
import com.example.project1301.spacing.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener , RecycleViewMenuAdapter.ItemListener{
    private Button close;
    private TextView tvName;
    private RecyclerView recyclerView;

    private RecycleViewMenuAdapter adapter;

    private List<Category> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        close = findViewById(R.id.close);
        tvName = findViewById(R.id.tname);
        recyclerView = findViewById(R.id.recycleView);
        Intent intent = getIntent();
        String inputName =  (String) intent.getSerializableExtra("item");
        if(inputName != null){
            tvName.setText(inputName);
        }

        list = new ArrayList<>();
        list.add(new Category(R.drawable.menu_combo, "Combo"));
        list.add(new Category(R.drawable.menu_chicken, "chicken"));
        list.add(new Category(R.drawable.menu_pastas, "Pastas"));
        list.add(new Category(R.drawable.menu_burger, "Burger"));
        list.add(new Category(R.drawable.menu_drink, "Drink"));
        list.add(new Category(R.drawable.menu_fresh, "Fresh"));

        adapter = new RecycleViewMenuAdapter(this, list);
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(30);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        close.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        if(v == close){
            finish();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Category it = adapter.getItem(position);
        if(position == 0){
            String name = it.getName();
            Intent intent = new Intent(this, DetailMenuActivity.class);
            intent.putExtra("item", name);
            startActivity(intent);
        }
    }
}