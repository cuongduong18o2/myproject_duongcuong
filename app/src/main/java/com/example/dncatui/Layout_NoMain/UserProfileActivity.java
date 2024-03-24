package com.example.dncatui.Layout_NoMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dncatui.LoginANDRegister.ReadWriteUserDetail;
import com.example.dncatui.MainActivity;
import com.example.dncatui.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private TextView txtWelcome,txtName,txtEmail,txtDob,txtGender,txtPhone;
    private String name,email,DoB,Gender,Phone;
    private ImageView imgProfile;
    private FirebaseAuth authProfile;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        txtWelcome=findViewById(R.id.txtShow_welcome);
        txtName=findViewById(R.id.showfullname);
        txtEmail=findViewById(R.id.showEmail);
        txtDob=findViewById(R.id.showDoB);
        txtGender=findViewById(R.id.showGender);
        txtPhone=findViewById(R.id.showPhone);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        imgProfile=findViewById(R.id.img_profile);



        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,UploadProfilePicActivity.class);
                startActivity(intent);

            }
        });





        if(firebaseUser==null){
            Toast.makeText(UserProfileActivity.this,"Đã xảy ra lỗi ! ",Toast.LENGTH_LONG).show();

        } else {
            showUserProfile(firebaseUser);
        }


    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String UserID = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Register Users");
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetail readUserDetail = snapshot.getValue(ReadWriteUserDetail.class);
                if(readUserDetail!=null){
                    name = firebaseUser.getDisplayName();
                    email = readUserDetail.email;
                    DoB = readUserDetail.doB;
                    Gender = readUserDetail.gender;
                    Phone = readUserDetail.phone;


                    txtWelcome.setText("Xin chào " + name);
                    txtName.setText(name);
                    txtDob.setText(DoB);
                    txtEmail.setText(email);
                    txtGender.setText(Gender);
                    txtPhone.setText(Phone);

                    //tải ảnh lên khi mà user đã upload
                    Uri uri =firebaseUser.getPhotoUrl();
                    Picasso.with(UserProfileActivity.this).load(uri).into(imgProfile );


                }else {
                    Toast.makeText(UserProfileActivity.this,"đã xảy ra lỗi !",Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this,"đã xảy ra lỗi !",Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if(id==R.id.menu_refresh) {
           startActivity(getIntent());
           finish();
           overridePendingTransition(0, 0);
       }else if(id==R.id.menu_updateProfile){
           Intent intent = new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
           startActivity(intent);
           finish();
       }/*else if(id==R.id.menu_updateEmail){
           Intent intent = new Intent(UserProfileActivity.this,UpdateEmailActivity.class);
           startActivity(intent);
       }else if(id==R.id.menu_setting){
          Toast.makeText(UserProfileActivity.this,"trang cài đặt",Toast.LENGTH_LONG).show();
       }else if(id==R.id.menu_changePwd){
           Intent intent = new Intent(UserProfileActivity.this,ChangePasswordActivity.class);
           startActivity(intent);
       }else if(id==R.id.menu_delete_profile){
           Intent intent = new Intent(UserProfileActivity.this,DeleteProfileActivity.class);
           startActivity(intent);
       }*/else if(id==R.id.menu_logout){
           authProfile.signOut();
           Toast.makeText(UserProfileActivity.this,"LogOut",Toast.LENGTH_LONG).show();
           Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
           finish();

       }else {
           Toast.makeText(UserProfileActivity.this,"Đã xảy ra lỗi !",Toast.LENGTH_LONG).show();

       }

        return super.onOptionsItemSelected(item);
    }
}

