package com.example.dncatui.Fragment_Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dncatui.ActivityinFragmenttaikhoan.DKDV_Activity;
import com.example.dncatui.ActivityinFragmenttaikhoan.QDSD_Activity;
import com.example.dncatui.ActivityinFragmenttaikhoan.chinhsachbaomat;
import com.example.dncatui.Layout_NoMain.UserProfileActivity;
import com.example.dncatui.MainActivity;
import com.example.dncatui.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class TaiKhoanFragment extends Fragment {
View root;
FirebaseAuth authProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        authProfile = FirebaseAuth.getInstance();
        root = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
       // Button btnDX=root.findViewById(R.id.btnDX);
        TextView txtDX = root.findViewById(R.id.txtDX);
        txtDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authProfile.signOut();
                Toast.makeText(getActivity(),"LogOut",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
       /* btnDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authProfile.signOut();
                Toast.makeText(getActivity(),"LogOut",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });*/
        TextView txtCSBM,txtQDSD,txtDKDV;
        txtCSBM=root.findViewById(R.id.txtCSBM);
        txtCSBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), chinhsachbaomat.class);
                startActivity(intent);
            }
        });
        txtQDSD=root.findViewById(R.id.txtQDSD);

        txtQDSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QDSD_Activity.class);
                startActivity(intent);

            }
        });

        txtDKDV=root.findViewById(R.id.txtDKDV);
        txtDKDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DKDV_Activity.class);
                startActivity(intent);

            }
        });

        return  root;


    }
}