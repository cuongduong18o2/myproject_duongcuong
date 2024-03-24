package com.example.dncatui.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dncatui.Layout_NoMain.LichKham;
import com.example.dncatui.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class AdapterLichkhamAdmin extends FirebaseRecyclerAdapter<LichKham,AdapterLichkhamAdmin.ViewholderLickkhamAdmin> {


    String maLichkham;
    String key;
    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterLichkhamAdmin(@NonNull FirebaseRecyclerOptions<LichKham> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterLichkhamAdmin.ViewholderLickkhamAdmin holder, @SuppressLint("RecyclerView") int position, @NonNull LichKham model) {

        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        // lấy mã của lịch khám
        // Lấy tên nút con thứ 2
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lichkham");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    key = child.getKey();

                    // Sử dụng key để lấy dữ liệu từ node con
                     maLichkham = child.child("Lichkham").getValue(String.class); // thay "keyOfChildNode" bằng key của node con bạn muốn lấy
                }
                holder.txtTenBSLichKhamAdmin.setText(model.getTenBS());
                holder.txtGioKhamLichAdmin.setText(model.getNgay() + "-" + model.getGio());
                holder.txtTenBNLichkhamAdmin.setText(firebaseUser.getDisplayName());
                holder.txtMaBenhNhanAdmin.setText(firebaseUser.getUid());
               // holder.txtMaLichkhamAdmin.setText(maLichkham);
                holder.btnHuylich.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.txtTenBSLichKhamAdmin.getContext());
                        builder.setTitle("Bạn có chắc chắn hủy lịch ?");
                        builder.setMessage("Lịch sẽ bị xóa và không lưu lại");
                        builder.setPositiveButton("Hủy lịch ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                FirebaseDatabase.getInstance().getReference().child("Lichkham")
                                        .child(getRef(position).getKey()).removeValue();
                                Toast.makeText(holder.txtTenBSLichKhamAdmin.getContext(), "đã hủy lịch ", Toast.LENGTH_SHORT).show();


                            }
                        }) ;

                        builder.setNegativeButton("Quay lại ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(holder.txtTenBSLichKhamAdmin.getContext(), "không hủy ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    }

                    });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



            @NonNull
    @Override
    public AdapterLichkhamAdmin.ViewholderLickkhamAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichkham_admin,parent,false);


        return new ViewholderLickkhamAdmin(view);
    }

    public class ViewholderLickkhamAdmin extends RecyclerView.ViewHolder {

        TextView txtTenBSLichKhamAdmin,txtGioKhamLichAdmin,txtTenBNLichkhamAdmin,txtMaBenhNhanAdmin,txtMaLichkhamAdmin;
        Button btnHuylich;
        public ViewholderLickkhamAdmin(@NonNull View itemView) {
            super(itemView);
            txtGioKhamLichAdmin=itemView.findViewById(R.id.txtGioKhamLichAdmin);
            txtTenBSLichKhamAdmin=itemView.findViewById(R.id.txtTenBSLichKhamAdmin);
            txtTenBNLichkhamAdmin=itemView.findViewById(R.id.txtTenBNLichkhamAdmin);
            txtMaBenhNhanAdmin=itemView.findViewById(R.id.txtMaBenhNhanAdmin);
           // txtMaLichkhamAdmin=itemView.findViewById(R.id.txtMaLichkhamAdmin);
            btnHuylich=itemView.findViewById(R.id.btnHuylich);
        }
    }
}
