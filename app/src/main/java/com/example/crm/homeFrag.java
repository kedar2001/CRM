package com.example.crm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import com.github.mikephil.charting.*;

import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crm.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashSet;


public class homeFrag extends Fragment {
    View view;
    PieChart pieChart;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<PieEntry> entries = new ArrayList<>();
        pieChart = view.findViewById(R.id.pie_chart);
        SharedPreferences pref = getActivity().getSharedPreferences("CRM", Context.MODE_PRIVATE);
        String email = pref.getString("email_",null);
        MyDBHelper db = new MyDBHelper(getActivity());
        Cursor c = db.dataName(email);
        int i = 0;
        //String a = null;
        ArrayList<String > a = new ArrayList<>();
        while (c.moveToNext()){
            a.add(c.getString(0));
            i++;
        }
        int j = 100-i;

        entries.add(new PieEntry((int)i,"Completed"));
        entries.add(new PieEntry((int)j,"not Completed"));


        PieDataSet pieDataSet = new PieDataSet(entries,"Pie Chart");
        PieData pieData = new PieData(pieDataSet);


        pieDataSet.setColor(R.color.g1);

        pieDataSet.setValueTextColor(R.color.black);
        pieData.setValueTextSize(12f);
        pieChart.setData(pieData);
        pieChart.invalidate();

        // Inflate the layout for this fragment


        return view;
    }
}