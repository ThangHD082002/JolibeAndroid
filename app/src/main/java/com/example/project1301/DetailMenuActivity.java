package com.example.project1301;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1301.Adapter.RecycleViewDetailMenuAdapter;
import com.example.project1301.model.Food;
import com.example.project1301.spacing.SpacingItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailMenuActivity extends AppCompatActivity implements View.OnClickListener, RecycleViewDetailMenuAdapter.ItemListener{
    private RecycleViewDetailMenuAdapter adapter;
    private TextView title;
    private Button close;
    private RecyclerView recyclerView;

    private FirebaseDatabase firebaseDatabase;

    private List<Food> listFinal;


    private List<Food> list;

    private String inputName = "";

    private Food foodf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);
        list = new ArrayList<>();
        listFinal = new ArrayList<>();

        title = findViewById(R.id.tname);

        recyclerView = findViewById(R.id.recycleView);
        close = findViewById(R.id.close);

        Intent intent = getIntent();
        inputName =  (String) intent.getSerializableExtra("item");
        if(inputName != null){
            title.setText(inputName);
        }
        getFoodFireBase();




//        list.add(new Food(R.drawable.c_one, "COMBO 322K", "322K", "2 MIẾNG GÀ GIÒN + 2 MÌ Ý + 2 CƠM GÀ GIÒN...", "combo"));
//        list.add(new Food(R.drawable.c_two, "COMBO 499K", "499K", "TIỆC KIỂU MỚI, QUÀ CHUẨN GU 499K...","combo"));
//        list.add(new Food(R.drawable.c_three, "COMBO 599K", "599K", "TIỆC KIỂU MỚI, QUÀ CHUẨN GU 599K...", "combo"));
//        list.add(new Food(R.drawable.c_four, "COMBO 699K", "699K", "TIỆC KIỂU MỚI, QUÀ CHUẨN GU 699K...", "combo"));

        System.out.println("list sie: "+list.size());




    }

    private void checkCategory() {
        if(inputName.toLowerCase().equals("combo")){
            for(Food f: list){
                if(f.getCategoy().toLowerCase().equals("combo")){
                    listFinal.add(f);
                }
            }
            Toast.makeText(DetailMenuActivity.this, inputName +"", Toast.LENGTH_SHORT).show();

        }
        else if(inputName.toString().toLowerCase().equals("chicken")){
            for(Food f: list){
                if(f.getCategoy().toLowerCase().equals("chicken")){
                    listFinal.add(f);
                }
            }
            Toast.makeText(DetailMenuActivity.this, inputName+"", Toast.LENGTH_SHORT).show();
        }
        else if(inputName.toString().toLowerCase().equals("pastas")){
            for(Food f: list){
                if(f.getCategoy().toLowerCase().equals("pastas")){
                    listFinal.add(f);
                }
            }
            Toast.makeText(DetailMenuActivity.this, inputName+"" , Toast.LENGTH_SHORT).show();
        }
        else if(inputName.toString().toLowerCase().equals("burger")){
            for(Food f: list){
                if(f.getCategoy().toLowerCase().equals("burger")){
                    listFinal.add(f);
                }
            }
            Toast.makeText(DetailMenuActivity.this, inputName+"" , Toast.LENGTH_SHORT).show();
        }
        else if(inputName.toString().toLowerCase().equals("drink")){
            for(Food f: list){
                if(f.getCategoy().toLowerCase().equals("drink")){
                    listFinal.add(f);
                }
            }
            Toast.makeText(DetailMenuActivity.this, inputName+"", Toast.LENGTH_SHORT).show();
        }
        else{
            for(Food f: list){
                if(f.getCategoy().toLowerCase().equals("fresh")){
                    listFinal.add(f);
                }
            }
            Toast.makeText(DetailMenuActivity.this, inputName+"" , Toast.LENGTH_SHORT).show();

        }
    }

    private void getFoodFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("Food");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot .exists()){

                    for(DataSnapshot snap: snapshot.getChildren()){
                        foodf = snap.getValue(Food.class);
                        list.add(foodf);
                    }
                }
                checkCategory();
                adapter = new RecycleViewDetailMenuAdapter(DetailMenuActivity.this, listFinal);
                SpacingItemDecoration itemDecoration = new SpacingItemDecoration(30);
                LinearLayoutManager manager = new LinearLayoutManager(DetailMenuActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(itemDecoration);
                recyclerView.setAdapter(adapter);
                close.setOnClickListener(DetailMenuActivity.this);
                adapter.setItemListener(DetailMenuActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching food items: " + error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == close){
            finish();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Food fd = adapter.getItem(position);
        Intent intent1 = new Intent(this, DetailFoodActivity.class);
        intent1.putExtra("item", fd);
        startActivity(intent1);
    }
}