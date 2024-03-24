package com.example.dncatui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.dncatui.Layout_NoMain.DoctorAdapter;
import com.example.dncatui.Layout_NoMain.LichKham;
import com.example.dncatui.Layout_NoMain.Model;
import com.example.dncatui.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class QLPK_Recyclerview_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterLichkhamAdmin adapterLichkhamAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlpk_recyclerview);


        recyclerView=findViewById(R.id.recycleviewLichkham);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<LichKham> options =
                new FirebaseRecyclerOptions.Builder<LichKham>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lichkham"), LichKham.class)
                        .build();


        adapterLichkhamAdmin = new AdapterLichkhamAdmin(options);
        recyclerView.setAdapter(adapterLichkhamAdmin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterLichkhamAdmin.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterLichkhamAdmin.stopListening();

    }

    //search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    private void txtSearch(String str){
        FirebaseRecyclerOptions<LichKham> options =
                new FirebaseRecyclerOptions.Builder<LichKham>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lichkham").orderByChild("TenBS").startAt(str).endAt(str+"~"), LichKham.class)
                        .build();


        adapterLichkhamAdmin = new AdapterLichkhamAdmin(options);
        adapterLichkhamAdmin.startListening();
        recyclerView.setAdapter(adapterLichkhamAdmin);

    }

}