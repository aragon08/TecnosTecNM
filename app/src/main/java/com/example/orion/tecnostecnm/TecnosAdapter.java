package com.example.orion.tecnostecnm;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Orion on 31/03/2017.
 */

public class TecnosAdapter extends RecyclerView.Adapter<TecnosAdapter.TecnoViewHolder> {

    Context contexto;
    private List<Tecnos> listaTecnos;

    public static class TecnoViewHolder extends RecyclerView.ViewHolder{

        private ImageView imv_logo;
        private TextView txt_long_name;
        private TextView txt_short_name;

        public TecnoViewHolder(View itemView) {
            super(itemView);

            imv_logo=(ImageView) itemView.findViewById(R.id.imv_logo);
            txt_long_name=(TextView) itemView.findViewById(R.id.txt_long_name);
            txt_short_name=(TextView) itemView.findViewById(R.id.txt_short_name);
        }
    }

    public TecnosAdapter(List<Tecnos> lista, Context con){
        contexto=con;
        listaTecnos=lista;
    }

    @Override
    public TecnoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_tecno,parent,false);
        return new TecnoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TecnosAdapter.TecnoViewHolder holder, int position) {

        try {
            Bitmap bitmap= Ion.with(contexto).load(listaTecnos.get(position).getLogoTecno()).withBitmap().asBitmap().get();
            holder.imv_logo.setImageBitmap(bitmap);
            holder.txt_long_name.setText(listaTecnos.get(position).getNomTecno());
            holder.txt_short_name.setText(listaTecnos.get(position).getNomCortoTecno());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaTecnos.size();
    }
}
