package com.example.talcawarningsapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talcawarningsapp.R;
import com.example.talcawarningsapp.model.Denuncia;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class AdapterMisDenuncias extends RecyclerView.Adapter<AdapterMisDenuncias.MisDenunciaHolder>{
    private int layout;
    public List<Denuncia> list;

    public AdapterMisDenuncias(int layout, List<Denuncia> list) {
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public MisDenunciaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new MisDenunciaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MisDenunciaHolder holder, int position) {
        Denuncia denuncia = list.get(position);
        holder.id.setText(denuncia.getId());
        holder.titulo.setText(denuncia.getTitulo());
        holder.direccion.setText(denuncia.getDireccion());
        int e = Integer.parseInt(denuncia.getEstado());
        if (e == 1){
            holder.estado.setImageResource(R.drawable.estadoa);
        }
        if (e == 0){
            holder.estado.setImageResource(R.drawable.estadob);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MisDenunciaHolder extends RecyclerView.ViewHolder{
        public TextView titulo,direccion,id;
        DatabaseReference reference;
        ImageView estado,delete;

        public MisDenunciaHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_denuncia_id);
            titulo = itemView.findViewById(R.id.item_denuncia_title);
            direccion = itemView.findViewById(R.id.item_denuncia_direccion);
            estado = itemView.findViewById(R.id.estado);
            delete = itemView.findViewById(R.id.item_denuncia_delete);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = Objects.requireNonNull(Objects.requireNonNull(user).getUid());
            reference = database.getReference("denuncias").child(uid);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(id.getText().toString());
                }
            });
        }
        private void delete(String id) {
            reference.child(id).removeValue();
            Snackbar snackbar = Snackbar.make(itemView, "Denuncia Eliminada", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

}
