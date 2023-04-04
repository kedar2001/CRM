package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Stack;

public class Dashboard extends AppCompatActivity {
    BottomNavigationView bnavbar;


    ArrayList<Constructor> arr = new ArrayList<>();
    SharedPreferences pref;
    Constructor c = new Constructor();
    MyDBHelper db = new MyDBHelper(this);

    Stack<Integer> STACK = new Stack<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        //ID's
        bnavbar = findViewById(R.id.bnavbar);
       /* logout = findViewById(R.id.Logout_Db);
        user_name = findViewById(R.id.user_name);*/ /*pref = getSharedPreferences("CRM",MODE_PRIVATE);
        String eml = pref.getString("email_",null);
        String naam = db.getNameOfCurrentUser(eml);
        user_name.setText(naam);*/ /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",false);
                editor.apply();

                Intent ijk = new Intent(Dashboard.this,login_act.class);
                startActivity(ijk);
            }
        });*/
        //default Frag

        STACK.push(R.id.home);
        getSupportFragmentManager().beginTransaction().add(R.id.fl1, new homeFrag()).commit();

        bnavbar.setOnItemSelectedListener(item -> {

            if(item.getItemId()==R.id.home){
                Frag(R.id.fl1,new homeFrag());
                STACK.push(R.id.home);
            }
            else if(item.getItemId()==R.id.task){
                Frag(R.id.fl1,new taskFrag());
                STACK.push(R.id.task);
            }
            else if(item.getItemId()==R.id.profile){
                Frag(R.id.fl1,new profileFrag());
                STACK.push(R.id.profile);
            }
            return true;});
    }
    public void Frag(int a, Fragment b){
        //STACK.push(a);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(a,b);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (bnavbar.getSelectedItemId()==R.id.home || STACK.empty()){
            finish();
            STACK.clear();
            super.onBackPressed();
        }
        else if (bnavbar.getSelectedItemId()==STACK.peek()){
            STACK.pop();
            bnavbar.setSelectedItemId(STACK.peek());
            STACK.pop();
        }
    }
}