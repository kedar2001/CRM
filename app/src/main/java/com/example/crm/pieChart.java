package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class pieChart extends AppCompatActivity {

    PieChart pieChart;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        pieChart = findViewById(R.id.pie_chart1);
        entries.add(new PieEntry(500,"Completed"));
        entries.add(new PieEntry(200,"not Completed"));


        PieDataSet pieDataSet = new PieDataSet(entries,"Pie Chart");
        PieData pieData = new PieData(pieDataSet);


        pieDataSet.setColor(R.color.g1);
        pieDataSet.setColor(R.color.g2);

        pieDataSet.setValueTextColor(R.color.g1);
        pieData.setValueTextSize(12f);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}