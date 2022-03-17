package com.example.agenda.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.VerActivity;
import com.example.agenda.entidades.Contactos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;
    Clic clic;

    public interface Clic{
        void pressed(Contactos contactos);
    }

    public ListaContactosAdapter(ArrayList<Contactos> listaContactos, Clic clic){
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
        this.clic = clic;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.viewNombre.setText(listaContactos.get(position).getNombre());
        holder.viewTelefono.setText(listaContactos.get(position).getTelefono());
        holder.viewCorreo.setText(listaContactos.get(position).getCorreo_electronico());
        if (position == listaContactos.size()-1){
            holder.separator.setVisibility(View.GONE);
        }else{
            holder.separator.setVisibility(View.VISIBLE);
        }
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud == 0){
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                List<Contactos> collection = listaContactos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaContactos.clear();
                listaContactos.addAll(collection);
            }else{
                for (Contactos c: listaOriginal){
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaContactos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewTelefono, viewCorreo;
        View separator;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
            separator = itemView.findViewById(R.id.separator);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clic.pressed(listaContactos.get(getAdapterPosition()));
                }
            });
        }
    }
}
