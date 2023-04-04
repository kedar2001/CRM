package com.example.crm;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;

public class formDetails_activity extends AppCompatActivity {

    TextInputEditText data_name,data_number,data_remarks,data_task_no;
    Button add_data;
    MyDBHelper db = new MyDBHelper(this);
    byte[] arr;
    Bitmap bmp;
    ImageView data_image;

    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            data_image.setImageURI(result);
            bmp = ((BitmapDrawable)data_image.getDrawable()).getBitmap();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,50,b);
            arr = b.toByteArray();
            data_image.setImageBitmap(bmp);
        }
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_details);
        data_name = findViewById(R.id.data_name);
        data_number = findViewById(R.id.data_number);
        add_data = findViewById(R.id.add_data);
        data_task_no = findViewById(R.id.data_task_no);
        data_remarks = findViewById(R.id.data_remarks);
        data_image = findViewById(R.id.data_image);
        SharedPreferences preferences = getSharedPreferences("CRM",MODE_PRIVATE);
        String email = preferences.getString("email_",null);

        data_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(formDetails_activity.this,Dashboard.class);
                String naam = data_name.getText().toString();
                String numm = data_number.getText().toString();
                String task_no = data_task_no.getText().toString();
                String remarks = data_remarks.getText().toString();

                if (naam.equals("") && numm.equals("")&& task_no.equals("")){
                    Toast.makeText(formDetails_activity.this, "Enter all Required Details", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.insert_t3(email,task_no,naam,numm,imageToByArr(data_image),remarks);
                   // Log.d("byteArrayIs", String.valueOf(imageToByArr(data_image)));
                    Toast.makeText(formDetails_activity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }

            }
        });
        

    }
        public byte[] imageToByArr(ImageView img){
            Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
            arr = byteArrayOutputStream.toByteArray();
            return arr;
        }
}