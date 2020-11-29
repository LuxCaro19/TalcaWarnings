package com.example.talcawarningsapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.talcawarningsapp.R;
import com.example.talcawarningsapp.adapter.AdapterDenuncia;
import com.example.talcawarningsapp.model.Denuncia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MisDenunciasFragment extends Fragment {
    FirebaseAuth auth;
    List<Denuncia> list;
    RecyclerView recyclerMisDenuncias;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_denuncias, container, false);
        recyclerMisDenuncias = view.findViewById(R.id.recycler_misdenuncias);
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        String uid = auth.getCurrentUser().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("denuncias").child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    list.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Denuncia denuncia = ds.getValue(Denuncia.class);
                        denuncia.setId(ds.getKey());
                        list.add(denuncia);
                    }
                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());
                    lm.setOrientation(RecyclerView.VERTICAL);

                    AdapterDenuncia adapterDenuncia = new AdapterDenuncia(getActivity(),R.layout.item_misdenuncias,list);
                    recyclerMisDenuncias.setLayoutManager(lm);
                    recyclerMisDenuncias.setAdapter(adapterDenuncia);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return view;
    }
}