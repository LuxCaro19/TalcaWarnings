package com.example.talcawarningsapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talcawarningsapp.R;
import com.example.talcawarningsapp.model.Denuncia;

import java.util.List;

public class AdapterDenuncia extends RecyclerView.Adapter<AdapterDenuncia.DenunciHolder> {
    private Activity activity;
    private int layout;
    private List<Denuncia> list;

    public AdapterDenuncia(Activity activity, int layout, List<Denuncia> denuncia) {
        this.activity = activity;
        this.layout = layout;
        this.list = denuncia;
    }

    @NonNull
    @Override
    public DenunciHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new DenunciHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DenunciHolder holder, int position) {
        Denuncia denuncia = list.get(position);
        holder.titulo.setText(denuncia.getTitulo());
        holder.direccion.setText(denuncia.getDireccion());
        int e = Integer.parseInt(denuncia.getEstado());
        if (e == 1){
            holder.estado.setImageResource(R.drawable.estadoa);
        }
        if (e == 0){
            holder.estado.setImageResource(R.drawable.estadob);
        }
        holder.id = denuncia.getId();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DenunciHolder extends RecyclerView.ViewHolder{
        TextView titulo, direccion;
        ImageView estado;
        String id;

        public DenunciHolder(@NonNull View itemView) {
            super(itemView);
            titulo =  itemView.findViewById(R.id.item_denuncia_title);
            direccion = itemView.findViewById(R.id.item_denuncia_direccion);
            estado = itemView.findViewById(R.id.estado);
        }
    }
}
