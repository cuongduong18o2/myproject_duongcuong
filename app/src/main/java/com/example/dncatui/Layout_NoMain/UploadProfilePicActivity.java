package com.example.dncatui.Layout_NoMain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dncatui.MainActivity;
import com.example.dncatui.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadProfilePicActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private ImageView imgUploadPic;
    private Button btnUploadPicChoose ,btnUploadPics;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private static final int PICK_IMG_REQUEST=1;
    private Uri uriImage;

private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_pic);
        btnUploadPicChoose= findViewById(R.id.upload_pic_choose_button);
        btnUploadPics=findViewById(R.id.upload_pic_button);
        progressBar=findViewById(R.id.progressBar);
        imgUploadPic=findViewById(R.id.imageView_profile_dp);


        auth = FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();


        storageReference= FirebaseStorage.getInstance().getReference("DisplayPics");


        Uri uri = firebaseUser.getPhotoUrl();
        Picasso.with(UploadProfilePicActivity.this).load(uri).into(imgUploadPic);


        btnUploadPicChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenfileChooser();

            }
        });
        btnUploadPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                UploadPics();
            }
        });





    }

    private void UploadPics() {
        if(uriImage!=null){
            //lưu ảnh với id user  hiện tại đang đăng nhập
            StorageReference fileReference= storageReference.child(auth.getCurrentUser().getUid()+"."+getFileExtention(uriImage));
            //upload img into storage
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri dowloadUri= uri;
                            firebaseUser=auth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(dowloadUri).build();
                            firebaseUser.updateProfile(profileUpdates);
                        }
                    });

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UploadProfilePicActivity.this,"tải ảnh thành công ",Toast.LENGTH_LONG).show();
                    Intent intent=  new Intent(UploadProfilePicActivity.this,UserProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadProfilePicActivity.this,"tải ảnh thất bại ",Toast.LENGTH_LONG).show();

                }
            });



        }else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(UploadProfilePicActivity.this,"Không có ảnh nào được chọn ",Toast.LENGTH_LONG).show();

        }



    }

    private String getFileExtention(Uri uriImage) {
        ContentResolver cR= getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uriImage));

    }

    private void OpenfileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK && data !=null && data.getData()!=null){
            uriImage=data.getData();
            imgUploadPic.setImageURI(uriImage);

        }

    }

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0 ,0 );
        }/*else if(id==R.id.menu_updateProfile){
           Intent intent = new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
           startActivity(intent);
       }else if(id==R.id.menu_updateEmail){
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
            auth.signOut();
            Toast.makeText(UploadProfilePicActivity.this,"LogOut",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UploadProfilePicActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }else {
            Toast.makeText(UploadProfilePicActivity.this,"Đã xảy ra lỗi !",Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}