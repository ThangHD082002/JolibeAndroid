package com.example.project1301;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UploadImage extends AppCompatActivity implements View.OnClickListener {

    private Button btUp, btSelect;
    private ImageView img;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        btUp = findViewById(R.id.btUp);
        btSelect = findViewById(R.id.btSelect);
        img = findViewById(R.id.img);
        btSelect.setOnClickListener(this);
        btUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btSelect){
            selectImage();
        }
    }

    private void selectImage() {

    }
}