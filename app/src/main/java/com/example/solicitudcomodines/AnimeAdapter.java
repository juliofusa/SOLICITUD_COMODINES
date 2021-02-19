package com.example.solicitudcomodines;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by JULIO on 14/11/2017.
 */

public class AnimeAdapter extends RecyclerView.Adapter<com.example.solicitudcomodines.AnimeAdapter.ViewHolder> {

    private List<SOLICITUD> userModelList;

    public AnimeAdapter(List<SOLICITUD> userModelList) {
        this.userModelList = userModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjetas_solicitudes, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s_comodin = userModelList.get(position).getCOMODIN();
        String s_cliente = userModelList.get(position).getCLIENTE();
        String s_observacion = userModelList.get(position).getOBSERVACION();
        String s_gestor = userModelList.get(position).getGESTOR();

        String s_fecha = userModelList.get(position).getFECHA();
        String s_hora_entrada = userModelList.get(position).getHORA_ENTRADA();
        String s_hora_salida = userModelList.get(position).getHORA_SALIDA();

        holder.comodin.setText(s_comodin);
        holder.gestor.setText(s_gestor);
        holder.cliente.setText(s_cliente);
        holder.observacion.setText(s_observacion);
        holder.fecha.setText(s_fecha);
        holder.hora_entrada.setText(s_hora_entrada);
        holder.hora_salida.setText(s_hora_salida);
        if (s_gestor.equals("-")){
            holder.SITUACION.setText("LIBRE");
            holder.SITUACION.setTextColor(Color.GREEN);
            holder.ESTADO.setImageResource(R.drawable.libre);
        }else{
            holder.SITUACION.setText("SOLICITADO");
            holder.SITUACION.setTextColor(Color.RED);
            holder.ESTADO.setImageResource(R.drawable.nodisponible);
        }



    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView comodin,gestor,fecha,hora_entrada,hora_salida,cliente,observacion,SITUACION;
        private ImageView ESTADO;

        ImageView personPhoto,personPhoto2personPhoto3,personPhoto4;
        public ViewHolder(View v) {
            super(v);
            comodin =  v.findViewById(R.id.CARD_COMODIN);
            gestor =  v.findViewById(R.id.CARD_GESTOR);
            fecha =  v.findViewById(R.id.CARD_FECHA);
            cliente =  v.findViewById(R.id.CARD_CLIENTE);

            hora_entrada =  v.findViewById(R.id.CARD_HE);
            hora_salida=  v.findViewById(R.id.CARD_HS);
            observacion =  v.findViewById(R.id.CARD_OBSERVACIONES);
            SITUACION=  v.findViewById(R.id.CARD_SITUACION);
            ESTADO=  v.findViewById(R.id.CARD_ESTADO);

        }
    }

}
