package com.example.dncatui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dncatui.Layout_NoMain.DocterAdapter2;
import com.example.dncatui.Layout_NoMain.DoctorAdapter;
import com.example.dncatui.Layout_NoMain.Model;
import com.example.dncatui.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class UpdateBacsi extends AppCompatActivity {


    FloatingActionButton fab;
    private RecyclerView  RecyclerView;

    private DocterAdapter2 docterAdapter2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bacsi);


       RecyclerView=findViewById(R.id.RECYCLEVIEWADMIN);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor"), Model.class)
                        .build();




        docterAdapter2 = new DocterAdapter2(options);
        RecyclerView.setAdapter(docterAdapter2);




         fab=findViewById(R.id.fab);

         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(UpdateBacsi.this, AddBacSi.class));
             }
         });


    }




    @Override
    protected void onStart() {
        super.onStart();
        docterAdapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        docterAdapter2.stopListening();
    }
}