package com.example.spotyfier.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import com.bumptech.glide.Glide;
import com.example.spotyfier.R;
import com.example.spotyfier.model.Personaje;
import com.example.spotyfier.view.ItemActivity;
import com.example.spotyfier.view.MainActivity;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    List<Personaje> listPersonajes;
    Context contexto;
    private static int modoBorrar = 0;
    public RecyclerAdapter(List<Personaje> listPersonajes, Context context) {
        this.listPersonajes = listPersonajes;
        this.contexto = context;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view, contexto);

        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) { //Cada vez que se carga un item
        Personaje personaje = listPersonajes.get(position);

        holder.txtViewTitulo.setText(personaje.getTitle());
        holder.txtViewNombre.setText(personaje.getFullname());
        holder.txtViewFamilia.setText(personaje.getFamily()); //Ponemos en cada campo del item los datos del personaje
        Glide.with(holder.itemView) //Glide nos ayuda a cargar la imagen del item
                .load(personaje.getImageurl())
                .error(R.mipmap.ic_launcher)
                .into(holder.imgPersonaje);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modoBorrar==0){
                    Intent intentItem = new Intent(contexto, ItemActivity.class);

                    Bundle parametros = new Bundle();

                    parametros.putString("fullName", personaje.getFullname());
                    parametros.putString("title", personaje.getTitle());
                    parametros.putString("family", personaje.getFamily());
                    parametros.putString("url", personaje.getImageurl());

                    intentItem.putExtras(parametros);

                    contexto.startActivity(intentItem);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setMessage("¿Borrar contenido?") .setTitle("Borrando informacion delicada.");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            removeItem(holder);
                            modoBorrar=0;
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }
    private void removeItem(RecyclerHolder holder) { //Borra el item de la lista e informa al adaptador para que realice el cambio
        listPersonajes.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
        notifyItemRangeChanged(holder.getAdapterPosition(), listPersonajes.size());
    }
    @Override
    public int getItemCount() {
        return listPersonajes.size(); //Devuelve la longitud de la lista
    }

    public class RecyclerHolder extends ViewHolder{
        ImageView imgPersonaje;
        TextView txtViewNombre;
        TextView txtViewFamilia;
        TextView txtViewTitulo;

        public RecyclerHolder(@NonNull View itemView, Context context) {
            super(itemView);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context); //Cargamos las preferencias para saber de que color será el texto
            String colorTexto = sharedPreferences.getString("list_preference_1","1");

            imgPersonaje  = (ImageView) itemView.findViewById(R.id.img_item);
            txtViewNombre = (TextView)  itemView.findViewById(R.id.txt_item_nombre);
            txtViewFamilia  = (TextView)  itemView.findViewById(R.id.txt_item_familia_txt);
            txtViewTitulo = (TextView) itemView.findViewById(R.id.txt_item_titulo); //Recogemos los datos del item
            if(colorTexto.equals("2")){ //Aplicamos el color segun la preferencia
                txtViewNombre.setTextColor(Color.RED);
                txtViewFamilia.setTextColor(Color.RED);
                txtViewTitulo.setTextColor(Color.RED);
            }
            else{
                txtViewNombre.setTextColor(Color.BLACK);
                txtViewFamilia.setTextColor(Color.BLACK);
                txtViewTitulo.setTextColor(Color.BLACK);
            }
        }
    }

    //Metodos para saber si esta activo el modo borrar.

    public static void setModoBorrar(int modo){
        modoBorrar=modo;
    }

    public static int getModoBorrar(){
        return modoBorrar;
    }
}