package com.example.dncatui.LoginANDRegister;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dncatui.Admin.MainAdmin;
import com.example.dncatui.Fragment_Main.ActivitytoFM;
import com.example.dncatui.Layout_NoMain.UserProfileActivity;
import com.example.dncatui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail,edtPass;
    private Button btnLogin,btnSignup,btnQMK;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    public static final String TAG="LoginActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth=FirebaseAuth.getInstance();

        btnQMK=findViewById(R.id.btnforgotPwd);

        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=findViewById(R.id.btnDangky);
        progressBar=findViewById(R.id.progressBar);


        btnQMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Bạn có thể cập nhật lại mật khẩu",Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));


            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    private void Register() {
        Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void Login() {
        String Email,Pass;
        Email=edtEmail.getText().toString();
        Pass=edtPass.getText().toString();
        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"vui lòng nhập Email !!",Toast.LENGTH_LONG).show();
            edtEmail.setError("vui lòng nhập Email");
            return;
        }else  if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            Toast.makeText(this,"vui lòng nhập Email !!",Toast.LENGTH_LONG).show();
            edtEmail.setError("Email không đúng");
            return;
        }
        else if (TextUtils.isEmpty(Pass)){
            Toast.makeText(this,"vui lòng nhập mật khẩu !!",Toast.LENGTH_LONG).show();
            edtPass.setError("vui lòng nhập mật khẩu ");
            return;
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){


                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        firebaseDatabase.getReference().child("Register Users").child(mAuth.getUid()).child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int userType=snapshot.getValue(Integer.class);
                                if(userType==0){
                                    Intent intent= new Intent(LoginActivity.this, ActivitytoFM.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);
                                }
                                if(userType==1){
                                    Intent intent= new Intent(LoginActivity.this, MainAdmin.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }

                    else {

                            try{
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidUserException e ){
                                edtEmail.setError("User này không tồn tại hoặc là đã hết hiệu lực , vui lòng đăng ký lại ");
                                edtEmail.requestFocus();

                            }
                            catch (FirebaseAuthInvalidCredentialsException e ){
                                edtPass.setError("Không hợp lệ, vui lòng kiểm tra mật khẩu hoặc email và nhập lại");
                                edtPass.requestFocus();

                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(LoginActivity.this,"đã xảy ra lỗi",Toast.LENGTH_LONG).show();
                            }



                        Toast.makeText(getApplicationContext(),"Đăng nhập không thành công",Toast.LENGTH_SHORT).show();;

                    }
                }
            });

        }


    }




}
