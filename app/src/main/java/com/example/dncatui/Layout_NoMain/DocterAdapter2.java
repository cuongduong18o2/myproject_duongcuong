package com.example.dncatui.Layout_NoMain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dncatui.Admin.UpdateBacsi;
import com.example.dncatui.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DocterAdapter2 extends FirebaseRecyclerAdapter<Model,DocterAdapter2.MyViewHolder2> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DocterAdapter2(@NonNull FirebaseRecyclerOptions<Model> options) {super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DocterAdapter2.MyViewHolder2 holder, int position, @NonNull Model model) {

        // ViewHolder sử dụng layout item
        holder.txtTenBSADMIN.setText(model.getName());
        holder.txtEmailBSADMIN.setText(model.getEmail());
        holder.txtFacultyBSADMIN.setText(model.getFaculty());
        Glide.with(holder.ivhinhBSADMIN.getContext())
                .load(model.getTurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark) // Đặt ảnh placeholder tùy ý
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal) // Đặt ảnh lỗi tùy ý
                .into(holder.ivhinhBSADMIN);

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.ivhinhBSADMIN.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup_bacsi))
                        .setExpanded(true ,1600)
                        .create();


                View view = dialogPlus.getHolderView();

                EditText edtNameedit = view.findViewById(R.id.edtNameEdit);
                EditText edtEmail = view.findViewById(R.id.edtEmailEdit);
                EditText edtFaculty = view.findViewById(R.id.edtFacultyEdit);
                EditText edtIMGurl = view.findViewById(R.id.edtImageEditURL);
                Button btnHoantat=view.findViewById(R.id.btnHoanTat);

                edtNameedit.setText(model.getName());
                edtEmail.setText(model.getEmail());
                edtFaculty.setText(model.getFaculty());
                edtIMGurl.setText(model.getTurl());
                dialogPlus.show();



                btnHoantat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map=    new HashMap<>();
                        map.put("name",edtNameedit.getText().toString());
                        map.put("email",edtEmail.getText().toString());
                        map.put("Faculty",edtFaculty.getText().toString());
                        map.put("turl",edtIMGurl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Doctor")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.txtTenBSADMIN.getContext(),"cập nhật thành công ",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.txtTenBSADMIN.getContext(),"cập nhật không thành công !! ",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });



            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txtTenBSADMIN.getContext());
                builder.setTitle("Bạn có chắc chắn xóa ?");
                builder.setMessage("Dữ liệu sẽ không thể được khôi phục");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseDatabase.getInstance().getReference().child("Doctor")
                                        .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.txtTenBSADMIN.getContext(), "đã xóa ", Toast.LENGTH_SHORT).show();


                    }
                }) ;

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.txtTenBSADMIN.getContext(), "đã hủy ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public DocterAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin,parent,false);

        return new MyViewHolder2(view);
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        ImageView ivhinhBSADMIN;
        Button btnSua,btnXoa;
        TextView txtTenBSADMIN,txtEmailBSADMIN,txtFacultyBSADMIN;
        public MyViewHolder2(@NonNull View itemView) {


            super(itemView);
            ivhinhBSADMIN=itemView.findViewById(R.id.ivhinhBSADMIN);
            txtTenBSADMIN=itemView.findViewById(R.id.txtTenBSADMIN);
            txtEmailBSADMIN=itemView.findViewById(R.id.txtEmailBSADMIN);
            txtFacultyBSADMIN=itemView.findViewById(R.id.txtFacultyBSADMIN);

            btnSua=itemView.findViewById(R.id.btnSUA);
            btnXoa=itemView.findViewById(R.id.btnXOA);
        }
    }
}
