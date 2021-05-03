package com.keymobile.tucancha.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.keymobile.tucancha.NuevaReservaActivity;
import com.keymobile.tucancha.R;
import com.keymobile.tucancha.RutaPartidoActivity;
import com.keymobile.tucancha.entidades.Reserva;

import java.util.ArrayList;

public class PartidoAdapter extends RecyclerView.Adapter<PartidoAdapter.PartidoView>  {

    private Activity activity;
    private Context context;
    private ArrayList<Reserva> lista_items = new ArrayList<>();

    public ArrayList<Reserva> getLista() {
        return lista_items;
    }

    public void setLista(ArrayList<Reserva> lista_items) {
        this.lista_items = lista_items;
    }

    public PartidoAdapter(Activity activity, Context context, ArrayList<Reserva> lista_items) {
        this.activity = activity;
        this.context = context;
        this.lista_items = lista_items;
    }

    @NonNull
    @Override
    public PartidoAdapter.PartidoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_partido, parent, false);
        return new PartidoAdapter.PartidoView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartidoAdapter.PartidoView holder, int position) {

        Reserva partido = lista_items.get(position);

        //holder.tvDescripcion.setText(String.format("%02d:00 - %02d:00", hora.getHoraReserva(), hora.getHoraReserva()+1));
        holder.tvCancha.setText(partido.getDataCancha().getNombre() );
        holder.tvDireccion.setText(partido.getDataCancha().getDireccion() );
        holder.tvHora.setText(String.format("%02d:00 - %02d:00", partido.getHoraReserva(), partido.getHoraReserva()+1) );

        holder.ibLocalizarCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, RutaPartidoActivity.class);
                intent.putExtra("ID_RESERVA", partido.getId());
                activity.startActivity(intent);

            }
        });


//        holder.btnReservarHora.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NuevaReservaActivity acti = (NuevaReservaActivity) activity;
//                acti.RegistrarReserva(hora);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return lista_items.size();
    }


    public class PartidoView extends RecyclerView.ViewHolder {

        TextView tvCancha, tvDireccion, tvHora;
        ImageButton ibLocalizarCancha;

        public PartidoView(@NonNull View itemView) {
            super(itemView);
            tvCancha = itemView.findViewById(R.id.tvCancha);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvHora = itemView.findViewById(R.id.tvHora);
            ibLocalizarCancha = itemView.findViewById(R.id.ibLocalizarCancha);

        }
    }
}
