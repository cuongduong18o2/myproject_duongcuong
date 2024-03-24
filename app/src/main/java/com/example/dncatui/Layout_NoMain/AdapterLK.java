package com.example.dncatui.Layout_NoMain;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.lang.ref.Reference;

public class AdapterLK extends FirebaseRecyclerAdapter<LichKham,AdapterLK.ViewHolderLKham> {
            String key;
    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterLK(@NonNull FirebaseRecyclerOptions<LichKham> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderLKham holder, int position, @NonNull LichKham model) {
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        // lấy mã của lịch khám
        // Lấy tên nút con thứ 2
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lichkham");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                         key = child.getKey();

                        break;


                }
                String IDpatien=model.IdBN;
                String IDuser=firebaseUser.getUid();

                if(IDpatien.equals(IDuser)) {

                    holder.txtTenBSLichKham.setText(model.getTenBS());
                    holder.txtGioKhamLich.setText(model.getNgay() + "-" + model.getGio());
                    holder.txtTenBNLichkham.setText(firebaseUser.getDisplayName());
                    holder.txtMaBenhNhan.setText(firebaseUser.getUid());
                  //  holder.txtIDlichkham.setText(key);
                    //xử lý nút btn gọi điện
                    holder.btnGoiDien.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //cố định sdt và sử dụng intel Action_dial để mở ứng dụng gọi điện
                            String sdt = "0389601124";
                            Intent intent_call = new Intent(Intent.ACTION_DIAL);
                            intent_call.setData(Uri.parse("tel:"+sdt));
                            v.getContext().startActivity(intent_call);
                            //gọi activity gọi điện

                        }
                    });



                }
                else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                    Toast.makeText(holder.txtGioKhamLich.getContext(), "Bạn chưa đặt lịch khám!", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });










    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

    @NonNull
    @Override
    public ViewHolderLKham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_kham,parent,false);

        return new ViewHolderLKham(view);
    }

    class  ViewHolderLKham extends RecyclerView.ViewHolder{

        TextView txtTenBSLichKham,txtGioKhamLich,txtTenBNLichkham,txtMaBenhNhan,txtIDlichkham;
        Button btnGoiDien;


        public ViewHolderLKham(@NonNull View itemView) {


            super(itemView);

            txtTenBSLichKham=itemView.findViewById(R.id.txtTenBSLichKham);
            txtGioKhamLich=itemView.findViewById(R.id.txtGioKhamLich);
            txtTenBNLichkham=itemView.findViewById(R.id.txtTenBNLichkham);
            txtMaBenhNhan=itemView.findViewById(R.id.txtMaBenhNhan);
           // txtIDlichkham = itemView.findViewById(R.id.txtMaLichkham);
            btnGoiDien=itemView.findViewById(R.id.btnGoiDien);


        }
    }


}