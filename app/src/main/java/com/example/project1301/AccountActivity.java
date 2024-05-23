package com.example.project1301;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project1301.Activity.ActivityStart;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView logout, tname;

    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        logout = findViewById(R.id.out);
        tname = findViewById(R.id.tname);

        Intent intent = getIntent();
        name =  (String) intent.getSerializableExtra("item");
        if(name != null){
            tname.setText(name);
        }

        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AccountActivity.this, ActivityStart.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();

    }
}