package com.example.talcawarningsapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.talcawarningsapp.R;
import com.example.talcawarningsapp.model.Denuncia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NuevoFragment extends Fragment {

    EditText txt_titulo, txt_direccion;
    Button button_crear;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_nuevo, container, false);

         txt_titulo=view.findViewById(R.id.nuevo_input_titulo);
         txt_direccion=view.findViewById(R.id.nuevo_input_direccion);
         button_crear = view.findViewById(R.id.nuevo_bt_crearDenuncia);
         auth = FirebaseAuth.getInstance();
         button_crear.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String mensaje = crearDenuncia();

                 Toast.makeText(getActivity(),mensaje, Toast.LENGTH_LONG).show();

             }
         });

        return view;
    }



    public String crearDenuncia(){

        String titulo, direccion, uid, msg;
        uid = auth.getCurrentUser().getUid();
        titulo = txt_titulo.getText().toString();
        direccion = txt_direccion.getText().toString();


        if (titulo.isEmpty()||direccion.isEmpty()){

            msg="llene los campos";

        }else{

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("denuncias").child(uid);

            Denuncia denuncia = new Denuncia();

            denuncia.setTitulo(titulo);
            denuncia.setDireccion(direccion);
            myRef.push().setValue(denuncia);

            msg= "Denuncia registrada correctamente";


        }


        return msg;


    }




}