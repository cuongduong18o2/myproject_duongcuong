package com.example.dncatui.Fragment_Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dncatui.Layout_NoMain.Recycle_view_DSBS;
import com.example.dncatui.R;
import com.example.dncatui.databinding.ActivityActivitytoFmBinding;

public class ActivitytoFM extends AppCompatActivity {

    ActivityActivitytoFmBinding binding;
        ImageView imgdoctor;
        HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActivitytoFmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        imgdoctor=findViewById(R.id.imgdoctor);






        binding.bottomNavigation.setOnItemSelectedListener(item ->{

            if(item.getItemId()==R.id.bottomHome)
            {replaceFragment(new HomeFragment());


            }
            if(item.getItemId()==R.id.bottomPhieukham)
            {replaceFragment(new DanhSachPKFragment());


            }
            if(item.getItemId()==R.id.bottomTaikhoan)
            {replaceFragment(new TaiKhoanFragment());


            }
            return true;

        } );
    }
    public   void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}