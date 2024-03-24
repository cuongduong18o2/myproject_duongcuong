package com.example.dncatui.Fragment_Main;

import static com.example.dncatui.databinding.ItemAdminBinding.inflate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dncatui.Layout_NoMain.AdapterLK;
import com.example.dncatui.Layout_NoMain.DoctorAdapter;
import com.example.dncatui.Layout_NoMain.LichKham;
import com.example.dncatui.Layout_NoMain.Model;
import com.example.dncatui.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class DanhSachPKFragment extends Fragment {
    View root;
    private RecyclerView recyclerView;
    private AdapterLK adapterLK;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_danh_sach_p_k, container, false);


        recyclerView=root.findViewById(R.id.recyclerViewLichkham);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        FirebaseRecyclerOptions<LichKham> options =
                new FirebaseRecyclerOptions.Builder<LichKham>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lichkham"), LichKham.class)
                        .build();


        adapterLK = new AdapterLK(options);
        recyclerView.setAdapter(adapterLK);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterLK.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterLK.stopListening();
    }

}