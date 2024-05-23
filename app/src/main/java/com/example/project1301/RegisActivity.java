package com.example.project1301;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project1301.model.Cart;
import com.example.project1301.model.Food;
import com.example.project1301.model.ItemCart;
import com.example.project1301.model.ItemOrder;
import com.example.project1301.model.Order;
import com.example.project1301.model.Payment;
import com.example.project1301.model.Shipment;
import com.example.project1301.model.UserData;
import com.example.project1301.model.UserOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etPhone, etMail, etPass, etAddr;
    private Button btRegis;

    FirebaseAuth firebaseAuth;
    private List<ItemOrder> listItemOrder;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        etName = findViewById(R.id.etname);
        etPhone = findViewById(R.id.etphone);
        etMail = findViewById(R.id.etmail);
        etPass = findViewById(R.id.etpass);
        etAddr = findViewById(R.id.etadd);
        btRegis = findViewById(R.id.btn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        btRegis.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btRegis) {

            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            String mail = etMail.getText().toString();
            String pass = etPass.getText().toString();
            String addr = etAddr.getText().toString();


            Food f = new Food("",R.drawable.menu_fresh, "" , "", "" ,"");
            ItemCart ic = new ItemCart("", f,0);
            Payment p = new Payment("", "");
            List<ItemCart> lic = new ArrayList<>();
            lic.add(ic);
            Shipment shipment = new Shipment("","","","");
            ItemOrder io = new ItemOrder("", lic, 0, p, "", "", shipment);
            List<ItemOrder> lo = new ArrayList<>();
            lo.add(io);
            Order o = new Order(lo);


            boolean numeric = true;
            numeric = name.matches("-?\\d+(\\.\\d+)?");
            if (!numeric) {
                firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisActivity.this, "Congratulations! You are Successfully Registered", Toast.LENGTH_SHORT).show();

                            saveUser(name, phone, mail, pass, addr, o);
                            Intent intent = new Intent(RegisActivity.this, ActivitySignIn.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisActivity.this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else
                Toast.makeText(RegisActivity.this, "Username is Invalid", Toast.LENGTH_LONG).show();
        }
    }
    private void saveUser(String name, String phone, String mail, String pass, String addr, Order o) {
        String uuid = firebaseAuth.getCurrentUser().getUid();
        UserOrder data1 = new UserOrder(uuid, name, phone, mail, pass, addr,  o);
        firebaseDatabase.getReference().child("Registered User").child(uuid).setValue(data1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisActivity.this, "User Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisActivity.this, ActivitySignIn.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(RegisActivity.this, "User not Saved", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}