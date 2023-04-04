package com.example.crm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class formAdapter extends RecyclerView.Adapter<formAdapter.ViewHolder> {
    Context context;
    ArrayList name_arr, number_arr;
    //ArrayList<byte[]> img_arr = new ArrayList<>();
    ArrayList<String > task_no = new ArrayList<>();

    public formAdapter(Context context, ArrayList name_arr, ArrayList number_arr,ArrayList task_no) {
        this.context = context;
        this.name_arr = name_arr;
        this.number_arr = number_arr;
        this.task_no = task_no;
        //this.img_arr = img_arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.task_data,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.nam.setText(String.valueOf(name_arr.get(position)));
        holder.num.setText(String.valueOf(number_arr.get(position)));
        byte[] a = {0};
        //Log.e("bitmapImg", String.valueOf(img_arr.get(position)));
        //Bitmap bp = BitmapFactory.decodeByteArray(a, 0, a.length);
        holder.imagge.setImageResource(R.drawable.arrow_graph);
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, getData_RCview.class);
                String n = task_no.get(position);
                SharedPreferences pref = context.getSharedPreferences("CRM",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_",n);
                editor.apply();
                context.startActivity(i);
                /*String k =pref.getString("id_",null);
                Log.e("current id",k);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return name_arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nam,num;
        ImageView imagge;
        Button edit_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nam = itemView.findViewById(R.id.RC_name);
            num = itemView.findViewById(R.id.RC_number);
            imagge = itemView.findViewById(R.id.imagge);
            edit_btn = itemView.findViewById(R.id.edit_task_data);
        }
    }

}
