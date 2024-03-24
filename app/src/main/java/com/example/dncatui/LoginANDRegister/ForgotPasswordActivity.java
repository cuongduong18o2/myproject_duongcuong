package com.example.dncatui.LoginANDRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dncatui.Layout_NoMain.UserProfileActivity;
import com.example.dncatui.MainActivity;
import com.example.dncatui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

   private Button btnReset_password;
    private EditText edtPasswordResetEmail;
    private  ProgressBar progressBar;
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtPasswordResetEmail=findViewById(R.id.edtPassword_resetEmail);
        btnReset_password=findViewById(R.id.btnPassword_reset);
        progressBar=findViewById(R.id.progressBar);
         btnReset_password.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String email = edtPasswordResetEmail.getText().toString();
                 if (TextUtils.isEmpty(email)) {
                     Toast.makeText(ForgotPasswordActivity.this,"vui lòng nhập email !!",Toast.LENGTH_LONG).show();
                     edtPasswordResetEmail.setError("yêu cầu nhập email");
                     edtPasswordResetEmail.requestFocus();
                 }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                     Toast.makeText(ForgotPasswordActivity.this,"vui lòng nhập email !!",Toast.LENGTH_LONG).show();
                     edtPasswordResetEmail.setError("vui lòng nhập đúng email");
                     edtPasswordResetEmail.requestFocus();
                 }
                 else {
                     progressBar.setVisibility(View.VISIBLE);
                     resetPassword(email);

                 }
             }
         });
    }

    private void resetPassword(String email) {
        authProfile=FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"vui lòng kiểm tra hộp thư của bạn",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(ForgotPasswordActivity.this,"Đã xảy ra lỗi ",Toast.LENGTH_LONG).show();

                }
                progressBar.setVisibility(View.VISIBLE);

            }
        });

    }
}