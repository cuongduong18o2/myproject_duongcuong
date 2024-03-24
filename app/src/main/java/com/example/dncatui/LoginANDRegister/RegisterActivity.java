package com.example.dncatui.LoginANDRegister;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dncatui.Layout_NoMain.UserProfileActivity;
import com.example.dncatui.MainActivity;
import com.example.dncatui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtRegisterFullName,edtRegisterEmail,edtRegisterNgaySinh,edtRegisterSDT,edtRegisterPass,edtRegisterCofPass;
    private Button btnDK;
    private ProgressBar progressBar;
    private RadioGroup radioGroupReGender;
    private RadioButton radioBtnSelect;
    public static final String TAG="RegisterActivity";
    FirebaseAuth auth= FirebaseAuth.getInstance();

    private  DatePickerDialog picker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        auth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        edtRegisterEmail= findViewById(R.id.edtRegisterEmail);
        edtRegisterFullName= findViewById(R.id.edtRegisterFullName);
        edtRegisterNgaySinh= findViewById(R.id.edtRegisterNgaySinh);
        edtRegisterSDT= findViewById(R.id.edtRegisterSDT);
        edtRegisterPass= findViewById(R.id.edtRegisterPass);
        edtRegisterCofPass= findViewById(R.id.edtRegisterCofPass);

        radioGroupReGender=findViewById(R.id.radiogroup_RE_gender);
        radioGroupReGender.clearCheck();
        btnDK= findViewById(R.id.btnDK);
            //goi calender ra de chon ngay
        edtRegisterNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar= Calendar.getInstance();
                int day= calendar.get(Calendar.DAY_OF_MONTH);
                int month= calendar.get(Calendar.MONTH);
                int year= calendar.get(Calendar.YEAR);


                picker= new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtRegisterNgaySinh.setText(dayOfMonth + "/" + (month + 1)+"/"+ year );

                    }
                },year,month,day);
                    picker.show();
            }
        });


        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderID= radioGroupReGender.getCheckedRadioButtonId();
                radioBtnSelect=findViewById(selectedGenderID);
                Register();
            }
        });

    }

    public void Register() {
        String Email,Name,DoB,Phone,Password,CofPass,Gender;
        Email=edtRegisterEmail.getText().toString();
        Name=edtRegisterFullName.getText().toString();
        DoB=edtRegisterNgaySinh.getText().toString();
        Phone=edtRegisterSDT.getText().toString();
        Password=edtRegisterPass.getText().toString();
        CofPass=edtRegisterCofPass.getText().toString();
        //check sdt
        String mobileRegex = "[0][0-9]{9}";
        Matcher mobileMatcher;
        Pattern mobilePattern= Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(Phone);


            //kiem tra du lieu

        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this,"vui lòng nhập đầy đủ họ và tên !!",Toast.LENGTH_LONG).show();
            edtRegisterFullName.setError("không được bỏ trống ");
            return;



        } if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"vui lòng nhập đầy đủ họ và tên !!",Toast.LENGTH_LONG).show();
            edtRegisterEmail.setError("không được bỏ trống ");
            return;



        }


        else  if(TextUtils.isEmpty(Phone)){
            Toast.makeText(this,"vui lòng nhập số điện thoại  !!",Toast.LENGTH_LONG).show();
            edtRegisterSDT.setError("không được bỏ trống ");
            return;
        }else  if(Phone.length()!=10){
            Toast.makeText(this,"vui lòng nhập số điện thoại  !!",Toast.LENGTH_LONG).show();
            edtRegisterSDT.setError("số điện thoại phải đủ 10 số ");
            return;
        }
        else if(!mobileMatcher.find()){
            Toast.makeText(this,"vui lòng nhập số điện thoại !!",Toast.LENGTH_LONG).show();
            edtRegisterSDT.setError("vui lòng xem lại số điện thoại");
            return;
        }



        else  if(TextUtils.isEmpty(DoB)){
            Toast.makeText(this,"vui lòng nhập ngày sinh !!",Toast.LENGTH_LONG).show();
            edtRegisterNgaySinh.setError("không được bỏ trống ");
            return;
        }else  if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"vui lòng nhập mật khẩu !!",Toast.LENGTH_LONG).show();
            edtRegisterPass.setError("không được bỏ trống ");
            return;
        }else  if(Password.length()<6){
            Toast.makeText(this,"vui lòng nhập mật khẩu !!",Toast.LENGTH_LONG).show();
            edtRegisterPass.setError("nhập mật khẩu khác mạnh hơn ");
            return;

        }

        else  if(TextUtils.isEmpty(CofPass)){
            Toast.makeText(this,"vui lòng nhập lại mật khẩu !!",Toast.LENGTH_LONG).show();
            edtRegisterCofPass.setError("không được bỏ trống ");
            return;
        }
        else  if(!Password.equals(CofPass)){
            Toast.makeText(this,"vui lòng nhập giống nhập khẩu ở trên !!",Toast.LENGTH_LONG).show();
            edtRegisterCofPass.setError("mật khẩu không giống ở trên ");
            return;
        }

        else  if(radioGroupReGender.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,"vui lòng chọn giới tính của bạn !!",Toast.LENGTH_LONG).show();

            return;
        }else {
            Gender=radioBtnSelect.getText().toString();
            progressBar.setVisibility(View.VISIBLE);



        }
        //tao tai khoan
        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener( RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser= auth.getCurrentUser();

                    //cập nhật lại tên của user
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(Name).build();
                    firebaseUser.updateProfile(profileChangeRequest);


                  //Inter User data into firebaseRealTime Database;
                    ReadWriteUserDetail writeUserDetail =  new ReadWriteUserDetail(Email,DoB,Phone,Gender,0);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Register Users");

                        referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    firebaseUser.sendEmailVerification();

                                    Toast.makeText(getApplicationContext(),"đăng ký thành công . Vui lòng xác nhận email của bạn  ",Toast.LENGTH_LONG).show();

                                   Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);

                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),"đăng ký thất bại , Vui lòng thử lại ",Toast.LENGTH_LONG).show();



                                }
                                progressBar.setVisibility(View.GONE);



                            }
                        });

                  }
                else {
                    try{
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e ){
                        edtRegisterPass.setError("mật khẩu của bạn quá yếu , vui lòng nhập mật khẩu mạnh hơn");
                        edtRegisterPass.requestFocus();

                    }
                    catch (FirebaseAuthInvalidCredentialsException e ){
                        edtRegisterEmail.setError("Email của bạn đã được sử dụng");
                        edtRegisterEmail.requestFocus();

                    }
                    catch (FirebaseAuthUserCollisionException e ){
                        edtRegisterEmail.setError("Email của bạn đã được đăng ký , sử dụng Email khác");
                        edtRegisterEmail.requestFocus();

                    }
                    catch (Exception e ){
                    Log.e(TAG,e.getMessage());
                    Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                    //ẩn cái progressBar khi mà user đăng ký thành công hoặc thất bại ;
                    progressBar.setVisibility(View.GONE);
                }
            }
        });





    }



}
