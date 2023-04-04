package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class login_act extends AppCompatActivity {
    TextView Register;
    TextInputEditText email,password;
    Button Login;

    MyDBHelper db = new MyDBHelper(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        Register = findViewById(R.id.Register_txt_LP);
        email = findViewById(R.id.TIET_email_LP);
        password = findViewById(R.id.TIET_password_LP);
        Login = findViewById(R.id.Login_btn_LP);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login_act.this,MainActivity.class);
                startActivity(i);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eml,pas;
                eml = email.getText().toString();
                pas = password.getText().toString();
                if (!eml.equals("")|| !pas.equals("")) {
                    if (db.loginDataFetch(eml, pas)==true) {
                        SharedPreferences pref = getSharedPreferences("CRM",MODE_PRIVATE);
                        SharedPreferences.Editor editor= pref.edit();
                        editor.putBoolean("flag",true);
                        editor.putString("email_",eml);
                        editor.apply();
                        /*String naam = db.nameOfCurrentUser(eml);
                        Toast.makeText(login_act.this, naam , Toast.LENGTH_SHORT).show();*/

                        Intent i = new Intent(login_act.this, Dashboard.class);
                        Toast.makeText(login_act.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(login_act.this, "incorrect Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(login_act.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}