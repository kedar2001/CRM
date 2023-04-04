package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("CRM",MODE_PRIVATE);
                Boolean check = pref.getBoolean("flag",false);

                Intent i;
                if (check){ //user has logged in
                    i = new Intent(SplashScreen.this,Dashboard.class);
                    startActivity(i);
                    finish();
                }
                else if(check==false) {
                    i = new Intent(SplashScreen.this,login_act.class);
                    startActivity(i);
                    finish();
                }
            }
        },750);
    }
}