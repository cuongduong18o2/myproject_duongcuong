package com.example.dncatui.Admin;

import static com.example.dncatui.R.id.addFaculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dncatui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddBacSi extends AppCompatActivity {

private EditText addBSName,addBSEmail,addBSFaculty,addURLimg;
private Button btnAddBS,btnBACK;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bac_si);


        addBSName=findViewById(R.id.addBSName);
        addBSEmail=findViewById(R.id.addBSEmail);
        addURLimg=findViewById(R.id.edtURL);
        addBSFaculty=findViewById(addFaculty);
        btnAddBS=findViewById(R.id.btnAddBS);
        btnBACK=findViewById(R.id.btnBACK);

        btnAddBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
                ClearALL();

            }
        });

        btnBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private  void InsertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",addBSName.getText().toString());
        map.put("email",addBSEmail.getText().toString());
        map.put("Faculty",addBSFaculty.getText().toString());
        map.put("turl",addURLimg.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Doctor").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddBacSi.this, "Đã thêm thành công ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddBacSi.this, "Thêm bác sĩ thất bại", Toast.LENGTH_SHORT).show();
                    }
                });



    }
    private  void ClearALL(){
        addBSName.setText("");
        addURLimg.setText("");
        addBSEmail.setText("");
        addBSFaculty.setText("");

    }





}