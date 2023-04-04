package com.example.crm;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;

public class getData_RCview extends AppCompatActivity {

    TextInputEditText get_data_name,get_data_number,get_data_remark,get_task_no;
    ImageView get_data_image;
    MyDBHelper db = new MyDBHelper(this);
    Button update_data;
    byte[] arr = {0};
    Bitmap bp;

    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            get_data_image.setImageURI(result);
            bp = ((BitmapDrawable)get_data_image.getDrawable()).getBitmap();
            ByteArrayOutputStream bytee = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG,50,bytee);
            arr = bytee.toByteArray();
            get_data_image.setImageBitmap(bp);

        }
    });
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_rcview);
        //get id's
        get_data_name = findViewById(R.id.get_data_name);
        get_data_number = findViewById(R.id.get_data_number);
        get_data_image = findViewById(R.id.get_data_image);
        get_data_remark = findViewById(R.id.get_data_remarks);
        get_task_no = findViewById(R.id.get_data_task_no);
        update_data = findViewById(R.id.update_data);

        SharedPreferences pref = getSharedPreferences("CRM",MODE_PRIVATE);
        String email = pref.getString("email_",null);
        String taskNo = pref.getString("id_",null);

        String nam,num,remark;
        Cursor c = db.get_data_image(email,taskNo);
        while (c.moveToNext()){
            arr = c.getBlob(0);
        }

        Bitmap bmp = BitmapFactory.decodeByteArray(arr,0,arr.length);
        get_data_image.setImageBitmap(bmp);
        Intent i = new Intent();
        nam = db.get_data_name(email,taskNo);
        num = db.get_data_num(email,taskNo);
        remark = db.get_data_remarks(email,taskNo);

        get_task_no.setText(taskNo);
        get_data_number.setText(num);
        get_data_name.setText(nam);
        get_data_remark.setText(remark);

        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nw_TN,nw_name,nw_num,nw_remark;
                nw_TN = get_task_no.getText().toString();
                nw_name = get_data_name.getText().toString();
                nw_num = get_data_number.getText().toString();
                nw_remark = get_data_remark.getText().toString();

                if(!nw_TN.equals("") && !nw_name.equals("") && !nw_num.equals("") ){
                    db.update_data_name(nw_name,email,taskNo);
                    db.update_data_Phone(nw_num,email,taskNo);
                    db.update_data_task_no(nw_TN,email,taskNo);
                    db.update_data_remarks(nw_remark,email,taskNo);

                    if(imageInByArr(get_data_image)!=null){
                        db.update_data_image(imageInByArr(get_data_image),email,taskNo);
                    }
                    else {
                        db.update_data_image(new byte[]{0,0},email,taskNo);
                    }

                    Intent i = new Intent(getData_RCview.this,Dashboard.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getData_RCview.this, "Enter Mandatory Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        get_data_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launcher.launch("image/*");
            }
        });


    }
        public byte[] imageInByArr(ImageView img){
            bp = ((BitmapDrawable)img.getDrawable()).getBitmap();
            ByteArrayOutputStream bytee = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG,50,bytee);
            arr = bytee.toByteArray();
            return arr;
        }
        public byte[] imageInByArrFromBitmap(Bitmap bmp){
            ByteArrayOutputStream bytee = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,50,bytee);
            arr = bytee.toByteArray();
            return arr;
        }
}