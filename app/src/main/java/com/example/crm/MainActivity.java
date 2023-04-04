package com.example.crm;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

                                            // REGISTER PAGE //

public class MainActivity extends AppCompatActivity {
    TextInputEditText name,email,password,phone;
    Button b1;
    ImageView insertImage;
    TextView Login;
    Bitmap imageToStore;
    MyDBHelper db = new MyDBHelper(this);

    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {

            insertImage.setImageURI(result);

             imageToStore = ((BitmapDrawable) insertImage.getDrawable()).getBitmap();

            ByteArrayOutputStream by = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.PNG,100,by);
            //byte[] ar = by.toByteArray();

            //Bitmap kk = BitmapFactory.decodeByteArray(ar, 0, ar.length);
            insertImage.setImageBitmap(imageToStore);

        }
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        name = findViewById(R.id.TIET_name_RP);
        email = findViewById(R.id.TIET_email_RP);
        password = findViewById(R.id.TIET_password_RP);
        phone = findViewById(R.id.TIET_phone_RP);
        insertImage = findViewById(R.id.insertImage_RP);

        b1 = findViewById(R.id.Register_btn_RP);
        Login = findViewById(R.id.Login_txt_RP);
       // db.datapass("namee","phonee","emaill","passwordd");
//        db.DRop();

        /* String namee,emaill,passwordd;
               // int ph = (int) Objects.requireNonNull(phone.getText()).toString();
                namee = name.getText().toString();
                emaill = email.getText().toString();
                passwordd = password.getText().toString();*/


        insertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee,emaill,passwordd,phonee;
                // int ph = Integer.parseInt(phone.getText().toString());
                namee = name.getText().toString();
                emaill = email.getText().toString();
                passwordd = password.getText().toString();
                phonee = phone.getText().toString();

                
                if (!namee.equals("") && !emaill.equals("") && !passwordd.equals("") && !phonee.equals("")){

                    if (db.alreadyRegisteredEmail(emaill)==true){
                        if(imageToBy(insertImage)!=null) {
                            db.datapass(namee, phonee, emaill, passwordd, imageToBy(insertImage));
                        }
                        else {
                            db.datapass(namee, phonee, emaill, passwordd, null);

                        }
                        Intent ij = new Intent(MainActivity.this,login_act.class);
                        Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(ij);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Already Registered with this email", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter Remaining Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,login_act.class);
                startActivity(i);
            }
        });
    }
    public byte[] imageToBy(ImageView img){
        Bitmap bitmap= ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,arr);
        byte[] bytes = arr.toByteArray();
        return bytes;
    }
}