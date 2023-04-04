package com.example.crm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class taskFrag extends Fragment {
    View view;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> number = new ArrayList<>();
    ArrayList<String> task_no = new ArrayList<>();
    ArrayList<byte[]> img_arr = new ArrayList<>();
    formAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task, container, false);
        RecyclerView rc = view.findViewById(R.id.task_recyclerview);
        FloatingActionButton floatingBTN = view.findViewById(R.id.floatingBTN);
        RecyclerView.LayoutManager LinerVertical = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rc.setLayoutManager(LinerVertical);
        MyDBHelper db = new MyDBHelper(getActivity());

        SharedPreferences pref = getActivity().getSharedPreferences("CRM", Context.MODE_PRIVATE);
        String email = pref.getString("email_",null);

        Cursor c1 = db.dataName(email);
        while (c1.moveToNext()){
            name.add(c1.getString(0));
        }
        Cursor c2 = db.dataPhone(email);
        while (c2.moveToNext()){
            number.add(c2.getString(0));
        }
        Cursor c3 = db.dataImg(email);
        while (c3.moveToNext()){
            img_arr.add(c3.getBlob(0));
        }
        Cursor c4 = db.data_task_no(email);
        while (c4.moveToNext()){
            task_no.add(c4.getString(0));
            Log.e("task_no = ", String.valueOf(c4.getString(0)));

        }

        adapter = new formAdapter(getContext(),name,number,task_no);
        rc.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        floatingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),formDetails_activity.class);
                startActivity(i);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}