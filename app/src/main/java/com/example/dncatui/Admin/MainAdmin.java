package com.example.dncatui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dncatui.Layout_NoMain.UserProfileActivity;
import com.example.dncatui.R;

public class MainAdmin extends AppCompatActivity {

    CardView QLBS,QLPK;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        QLBS= findViewById(R.id.QLBS);
        QLPK=findViewById(R.id.QLPK);
        ImageView img_profile_admin;
        img_profile_admin=findViewById(R.id.img_profile_admin);
        img_profile_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmin.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });


        QLPK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainAdmin.this, QLPK_Recyclerview_Activity.class);
                startActivity(intent);
            }
        });



        QLBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmin.this, UpdateBacsi.class);
                startActivity(intent);
            }
        });
    }
}