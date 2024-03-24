package com.example.dncatui.Fragment_Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dncatui.HDDLActivity;
import com.example.dncatui.Layout_NoMain.AddressActivity;
import com.example.dncatui.Layout_NoMain.Recycle_view_DSBS;
import com.example.dncatui.Layout_NoMain.UserProfileActivity;
import com.example.dncatui.Layout_NoMain.Web_activity;
import com.example.dncatui.R;
import com.example.dncatui.TTBVActivity;


public class HomeFragment extends Fragment {
    View root;
    ImageButton imgbtnthongbao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);

        //click vào image

        imgbtnthongbao=root.findViewById(R.id.imgbtnthongbao);
        imgbtnthongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIMGBTN();
            }
        });



        ImageView imgdoctor = root.findViewById(R.id.imgdoctor);
        imgdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick();
            }
        });


        ImageView imgbtnINFO = root.findViewById(R.id.imgbtnINFO);
        imgbtnINFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageBTNINFO();
            }
        });

        ImageView imgphone=root.findViewById(R.id.imgphone);
        imgphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageTTBV();

            }
        });

        ImageView imgbook= root.findViewById(R.id.imgbook);
        imgbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageHDDL();
            }
        });

        ImageView imgaddress = root.findViewById(R.id.imgaddress);
        imgaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageAddress();

            }
        });

        TextView txtSearch = root.findViewById(R.id.txtSearch);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch();
            }
        });



        return root;
    }

    private void onClickIMGBTN() {
        Intent intent = new Intent(getActivity(), Web_activity.class);
        startActivity(intent);
    }


    //thì khi nhấp vào thì nó sẽ hiện activity khác
        public  void onImageAddress(){
            Intent intent = new Intent(getActivity(), AddressActivity.class);
            startActivity(intent);
        }
    public void onImageClick() {
        Intent intent = new Intent(getActivity(), Recycle_view_DSBS.class);
        startActivity(intent);
    }
    public  void onImageBTNINFO(){
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        startActivity(intent);
    }
    public  void onImageTTBV(){
        Intent intent = new Intent(getActivity(), TTBVActivity.class);
        startActivity(intent);
    }
    public  void onImageHDDL(){
        Intent intent = new Intent(getActivity(), HDDLActivity.class);
        startActivity(intent);
    }
    public  void txtSearch(){
        Intent intent = new Intent(getActivity(), Recycle_view_DSBS.class);
        startActivity(intent);
    }



}