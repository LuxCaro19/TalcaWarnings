package com.example.talcawarningsapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


public class DenunciasFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    RecyclerView recyclerView;
    String uid;
    List<Denuncia> list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_denuncias, container, false);

        recyclerView= view.findViewById(R.id.recycler_denuncias);
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        database= FirebaseDatabase.getInstance();
        uid=auth.getUid();
        myRef= database.getReference("denuncias");
        cargarTodasLasDenuncias();



        return view;
    }


    public void cargarTodasLasDenuncias(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    list.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){

                        for (DataSnapshot ds_denuncias: ds.getChildren()){

                            Denuncia denuncia = ds_denuncias.getValue(Denuncia.class);
                            denuncia.setId(ds_denuncias.getKey());
                            list.add(denuncia);

                        }


                    }





                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());

                    lm.setOrientation(RecyclerView.VERTICAL);


                    AdapterDenuncia adapterDenuncia = new AdapterDenuncia(getActivity(),R.layout.item_misdenuncias,list);

                    recyclerView.setLayoutManager(lm);

                    recyclerView.setAdapter(adapterDenuncia);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}