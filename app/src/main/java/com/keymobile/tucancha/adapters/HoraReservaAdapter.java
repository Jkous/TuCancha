package com.keymobile.tucancha.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.keymobile.tucancha.NuevaReservaActivity;
import com.keymobile.tucancha.R;
import com.keymobile.tucancha.entidades.CostoHora;
import com.keymobile.tucancha.entidades.Reserva;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HoraReservaAdapter extends RecyclerView.Adapter<HoraReservaAdapter.HoraReservaView>  {


    private Activity activity;
    private Context context;
    private ArrayList<Reserva> lista_items = new ArrayList<>();

    public ArrayList<Reserva> getLista() {
        return lista_items;
    }

    public void setLista(ArrayList<Reserva> lista_items) {
        this.lista_items = lista_items;
    }

    public HoraReservaAdapter(Activity activity, Context context, ArrayList<Reserva> lista_items) {
        this.activity = activity;
        this.context = context;
        this.lista_items = lista_items;
    }

    @NonNull
    @Override
    public HoraReservaAdapter.HoraReservaView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_hora_reserva, parent, false);
        return new HoraReservaAdapter.HoraReservaView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoraReservaAdapter.HoraReservaView holder, int position) {

        Reserva hora = lista_items.get(position);

        holder.tvHora.setText(String.format("%02d:00 - %02d:00", hora.getHoraReserva(), hora.getHoraReserva()+1));
        holder.tvTarifa.setText(String.format("S/ %#.2f", hora.getPrecioReserva()) );


        holder.btnReservarHora.setText(hora.getEstado().toUpperCase());

        if(hora.getEstado().equals("disponible")) {
            holder.btnReservarHora.setBackgroundColor(activity.getResources().getColor(R.color.estado_disponible));
            holder.btnReservarHora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NuevaReservaActivity acti = (NuevaReservaActivity) activity;
                    acti.RegistrarReserva(hora);
                }
            });

        } else {
            holder.btnReservarHora.setBackgroundColor(activity.getResources().getColor(R.color.estado_reservado));
        }


    }

    @Override
    public int getItemCount() {
        return lista_items.size();
    }


    public class HoraReservaView extends RecyclerView.ViewHolder {

        TextView tvHora, tvTarifa;
        Button btnReservarHora;

        public HoraReservaView(@NonNull View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvHoraReserva);
            tvTarifa = itemView.findViewById(R.id.tvPrecioReserva);
            btnReservarHora = itemView.findViewById(R.id.btnReservarHora);

        }
    }

}
