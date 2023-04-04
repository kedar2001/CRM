package com.example.crm;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class editProfile extends AppCompatActivity {

    EditText name,email,phone;
    Button update;
    ImageView userImage;
    MyDBHelper db = new MyDBHelper(this);
    Bitmap imageToStore;
    Bitmap kk;

    byte[] ar;

    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            userImage.setImageURI(result);

            imageToStore=((BitmapDrawable)userImage.getDrawable()).getBitmap();

            ByteArrayOutputStream by = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.PNG,100,by);
            ar = by.toByteArray();

            kk = BitmapFactory.decodeByteArray(ar,0,ar.length);
            userImage.setImageBitmap(kk);


        }
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        update = findViewById(R.id.update);
        userImage = findViewById(R.id.userImage_edit);

       
        SharedPreferences pref = getSharedPreferences("CRM",MODE_PRIVATE);

        String UserEmail = pref.getString("email_",null);

        byte[] imageInByteArray = db.getImageOfCurrentUser(UserEmail);
        if(imageInByteArray!=null) {
            imageToStore = BitmapFactory.decodeByteArray(imageInByteArray, 0, imageInByteArray.length);
            userImage.setImageBitmap(imageToStore);
            Log.d("Passed In","Entered the if condition");
            Toast.makeText(this, "imageInByteArray is not null", Toast.LENGTH_SHORT).show();
        }
        //userImage.setImageBitmap(imageToStore);

//        imageToStore=((BitmapDrawable)userImage.getDrawable()).getBitmap();
//        ByteArrayOutputStream by = new ByteArrayOutputStream();
//        ar = by.toByteArray();


        name.setText(db.getNameOfCurrentUser(UserEmail));
        email.setText(UserEmail);
        phone.setText(db.getPhoneOfCurrentUser(UserEmail));
        Constructor c = new Constructor();
        c.email=UserEmail;




        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent ij = new Intent();
                launcher.launch("image/*");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // db.addImage(kk,UserEmail);
                if(ar != null) {
                    db.Updateimage(ar, UserEmail);
                }

                //db.Updateimage(ar,UserEmail);
                String newName,newPhone,newEmail;
                newName = name.getText().toString();
                newPhone = phone.getText().toString();
                newEmail = email.getText().toString();


                if(newEmail.equals(UserEmail)){
                    db.updateName(newName,UserEmail);
                    db.updatePhone(newPhone,UserEmail);
                }
                else {
                    db.updateEmail(newEmail,UserEmail);
                    db.updateName(newName,newEmail);
                    db.updatePhone(newPhone,newEmail);
                }
//                db.updateEmail(newEmail,UserEmail);
//                db.updateName(newName,newEmail);
//                db.updatePhone(newPhone,newEmail);

                SharedPreferences pref = getSharedPreferences("CRM",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("email_",newEmail);
                editor.apply();
                Log.d("values on edit Activity",pref.getString("email_",null));
                Log.d("values on edit Activity",db.getNameOfCurrentUser(newEmail)+ " ");
                Log.d("values on edit Activity",db.getPhoneOfCurrentUser(newEmail)+ " ");


                //update method baki che add it
               /* byte[] imageInByteArray = db.getImageOfCurrentUser(UserEmail);
                Toast.makeText(editProfile.this, UserEmail, Toast.LENGTH_SHORT).show();

                if(imageInByteArray!=null) {
                    imageToStore = BitmapFactory.decodeByteArray(imageInByteArray, 0, imageInByteArray.length);
                    resultImg.setImageBitmap(imageToStore);
                    Log.d("Passed In","Entered the if condition");
                }*/
                Intent i = new Intent(editProfile.this,Dashboard.class);
                startActivity(i);
            }
        });
    }
}