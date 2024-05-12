package com.example.project1301;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1301.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivitySignIn extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail, etPass;
    private Button btSignIn;
    private TextView tvRegister;

    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;

    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.etmail);
        etPass = findViewById(R.id.etpass);
        btSignIn = findViewById(R.id.btn);
        tvRegister = findViewById(R.id.tvsign1);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        btSignIn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btSignIn){
            String mail=etEmail.getText().toString();
            String pass=etPass.getText().toString();
            firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        String uuid = user.getUid();
                        DatabaseReference userRef = firebaseDatabase.getReference().child("Registered User").child(uuid);
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    userData = snapshot.getValue(UserData.class);
                                    Toast.makeText(ActivitySignIn.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent intent1=new Intent(ActivitySignIn.this, MainActivity.class);
                                    intent1.putExtra("item", userData);
                                    startActivity(intent1);
                                } else {
                                    // Xử lý trường hợp không tìm thấy dữ liệu của người dùng
                                    Toast.makeText(ActivitySignIn.this, "User data not found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else {
                        Toast.makeText(ActivitySignIn.this, "Login failed", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ActivitySignIn.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(view == tvRegister){
            Intent intent=new Intent(ActivitySignIn.this, RegisActivity.class);
            startActivity(intent);
        }
    }

}