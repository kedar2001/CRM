package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class checktest extends AppCompatActivity {

    MyDBHelper db = new MyDBHelper(this);
    ByteArrayOutputStream ar = new ByteArrayOutputStream();
    ImageView imageView;
    SharedPreferences preferences = getSharedPreferences("CRM",MODE_PRIVATE);
    Bitmap map;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checktest);

        imageView = findViewById(R.id.imageView007);
        String a = preferences.getString("email_",null);


    }
}