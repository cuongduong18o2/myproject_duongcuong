package com.example.dncatui.Layout_NoMain;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dncatui.Admin.AddBacSi;
import com.example.dncatui.Admin.BacSi;
import com.example.dncatui.DatLichKhamActivity;
import com.example.dncatui.LoginANDRegister.ReadWriteUserDetail;
import com.example.dncatui.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class DoctorAdapter  extends FirebaseRecyclerAdapter<Model,DoctorAdapter.myViewHolder> {
    private  EditText edtNgaykham;
    private DatePickerDialog picker;
    RadioGroup Rdgbtn;
    RadioButton RadioBtnSelected;

    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
    TextView txtBSDL,txtEmailDL,txtFacultyDL,txtTenBN,txtGenderBN,txtDoBBN,txtSDTBN;




    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DoctorAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Model model) {
         FirebaseAuth authProfile;
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

            // ViewHolder sử dụng layout item
            holder.txtTenBSAdmin.setText(model.getName());
            holder.txtEmailBS.setText(model.getEmail());
            holder.txtFaculty.setText(model.getFaculty());
            Glide.with(holder.ivhinhAdmin.getContext())
                    .load(model.getTurl())
                    .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark) // Đặt ảnh placeholder tùy ý
                    .circleCrop()
                    .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal) // Đặt ảnh lỗi tùy ý
                    .into(holder.ivhinhAdmin);








            holder.btnDLK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.ivhinhAdmin.getContext())
                            .setContentHolder(new ViewHolder(R.layout.activity_dat_lich_kham))
                            .setExpanded(true ,1400)
                            .create();


                    View view = dialogPlus.getHolderView();

                    ImageView imghinhBSDL;
                    imghinhBSDL=view.findViewById(R.id.ivhinhBSDL);
                    txtBSDL=view.findViewById(R.id.txtTenBSDL);
                    txtEmailDL=view.findViewById(R.id.txtEmailBSDL);
                    txtFacultyDL=view.findViewById(R.id.txtFacultyDL);
                    txtTenBN=view.findViewById(R.id.txtTenBN);
                    txtGenderBN=view.findViewById(R.id.txtGenderBN);
                    txtDoBBN=view.findViewById(R.id.txtDoBBN);
                    txtSDTBN=view.findViewById(R.id.txtSDTBN);
                    Rdgbtn=view.findViewById(R.id.Rdgbtn);
                    Rdgbtn.clearCheck();


                    edtNgaykham=view.findViewById(R.id.edtNgaykham);


                    // thêm dữ liệu lịch khám vào firebase
                    Button btnXacNhan = view.findViewById(R.id.btnXacNhan);
                    btnXacNhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int selectedHourID = Rdgbtn.getCheckedRadioButtonId();
                            RadioBtnSelected=view.findViewById(selectedHourID);
                            AppCompatEditText editText = view.findViewById(R.id.edtNgaykham);
                            CharSequence text = editText.getText();

                            if(TextUtils.isEmpty(text)){
                                Toast.makeText(view.getContext(), "Vui lòng chọn ngày khám!", Toast.LENGTH_SHORT).show();
                                edtNgaykham.setError("vui lòng chọn ngày khám !");
                                return;
                            } else  if(Rdgbtn.getCheckedRadioButtonId()==-1){
                                Toast.makeText(view.getContext(),"vui lòng chọn thời gian!!",Toast.LENGTH_LONG).show();
                                return;
                            }

                            InsertDataLich();
                            Clear();

                        }
                    });


                        // mở calendar lên để chọn ngày
                    edtNgaykham.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar= Calendar.getInstance();
                            int day= calendar.get(Calendar.DAY_OF_MONTH);
                            int month= calendar.get(Calendar.MONTH);
                            int year= calendar.get(Calendar.YEAR);

                            picker=new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    edtNgaykham.setText(dayOfMonth + "/" + (month + 1)+"/"+ year );
                                }
                            },year,month,day);
                            picker.show();
                        }
                    });





                    // lấy thông tin người dùng trong bảng Register Users hiện thị lên txt

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Register Users");
                    String UserID = firebaseUser.getUid();
                    reference.child(UserID).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    ReadWriteUserDetail readUserDetail = snapshot.getValue(ReadWriteUserDetail.class);

                                    txtTenBN.setText(firebaseUser.getDisplayName());
                                    txtGenderBN.setText(readUserDetail.gender);
                                    txtDoBBN.setText(readUserDetail.doB);
                                    txtSDTBN.setText(readUserDetail.phone);



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            }
                    );


                            txtBSDL.setText(model.getName().toString());
                    txtEmailDL.setText(model.getEmail().toString());
                    txtFacultyDL.setText(model.getFaculty().toString());
                    Glide.with(holder.ivhinhAdmin.getContext())
                            .load(model.getTurl())
                            .circleCrop()
                            .into(imghinhBSDL);
                    dialogPlus.show();





                }
            });




    }

    private void Clear() {
        edtNgaykham.setText("");
    }

    // hàm nạp data bằng Map
    private void InsertDataLich() {
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        Map<String,Object> map = new HashMap<>();
        map.put("Ngay",edtNgaykham.getText().toString());
        map.put("Gio",RadioBtnSelected.getText().toString());
        map.put("TenBS",txtBSDL.getText().toString());
        map.put("IdBN",firebaseUser.getUid());
        map.put("TenBN",firebaseUser.getDisplayName());
        FirebaseDatabase.getInstance().getReference().child("Lichkham").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(edtNgaykham.getContext(), "Đã đặt lịch thành công ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(edtNgaykham.getContext(), "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
                    }
                });




    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new myViewHolder(view);
    }


    class   myViewHolder extends RecyclerView.ViewHolder{

        ImageView ivhinhAdmin;
        TextView txtTenBSAdmin,txtEmailBS,txtFaculty;
        Button btnDLK;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            ivhinhAdmin=itemView.findViewById(R.id.ivhinhAdmin);
            btnDLK=itemView.findViewById(R.id.btnDLK);
            txtTenBSAdmin=itemView.findViewById(R.id.txtTenBSAdmin);
            txtEmailBS=itemView.findViewById(R.id.txtEmailBS);
            txtFaculty=itemView.findViewById(R.id.txtFaculty);







        }
    }



}
