package com.example.crm;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crm.R;

import java.io.IOException;


public class profileFrag extends Fragment {

    View view;
    Button Logout,Edit;
    TextView name,email,phone;
    ImageView userImage;
    MyDBHelper db;
    Bitmap imageToStore;
    byte[] imageInByteArray;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Logout=view.findViewById(R.id.Logout_F3);
        Edit =view.findViewById(R.id.edit_profileFrag);
        name = view.findViewById(R.id.name_profile);
        email = view.findViewById(R.id.email_profile);
        phone = view.findViewById(R.id.phone_profileFrag);
        userImage = view.findViewById(R.id.userImage);


        db = new MyDBHelper(getActivity());

        SharedPreferences  pref = getActivity().getSharedPreferences("CRM",Context.MODE_PRIVATE);
        String UserEmail = pref.getString("email_",null);
        String UserName = db.getNameOfCurrentUser(UserEmail);
        String UserPhone = db.getPhoneOfCurrentUser(UserEmail);

        Log.d("emaillll on frag",pref.getString("email_",null));
        Log.d("namee on frag",db.getNameOfCurrentUser(UserEmail)+" ");
        Log.d("phone on frag",db.getPhoneOfCurrentUser(UserEmail)+" ");

        name.setText(UserName);
        email.setText(UserEmail);
        phone.setText(UserPhone);

        imageInByteArray = db.getImageOfCurrentUser(UserEmail);

        if(imageInByteArray!=null) {
            imageToStore = BitmapFactory.decodeByteArray(imageInByteArray, 0, imageInByteArray.length);
            userImage.setImageBitmap(imageToStore);
            Log.d("Passed In","Entered the if condition");
        }


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",false);
                editor.apply();

                Intent i = new Intent(getContext(),login_act.class);
                startActivity(i);
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),editProfile.class);
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}