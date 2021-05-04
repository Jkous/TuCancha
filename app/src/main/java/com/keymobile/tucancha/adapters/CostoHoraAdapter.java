package com.keymobile.tucancha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.keymobile.tucancha.R;
import com.keymobile.tucancha.entidades.CostoHora;

import java.util.ArrayList;

public class CostoHoraAdapter extends RecyclerView.Adapter<CostoHoraAdapter.CostoHoraView>  {

    private Context context;
    private ArrayList<CostoHora> lista_items = new ArrayList<>();

    public ArrayList<CostoHora> getLista() {
        return lista_items;
    }

    public void setLista(ArrayList<CostoHora> lista_items) {
        this.lista_items = lista_items;
    }

    public CostoHoraAdapter(Context context, ArrayList<CostoHora> lista_items) {
        this.context = context;
        this.lista_items = lista_items;
    }

    @NonNull
    @Override
    public CostoHoraView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_costo_hora, parent, false);
        return new CostoHoraView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CostoHoraView holder, int position) {

        CostoHora hora = lista_items.get(position);
        holder.tvHora.setText(String.format("%02d:00 - %02d:00", hora.getHora(), hora.getHora()+1));

        holder.tvTarifa.setText(String.format("S/ %#.2f", hora.getPrecio()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.format("%02d:00 - %02d:00", hora.getHora(), hora.getHora()+1), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_items.size();
    }


    public class CostoHoraView extends RecyclerView.ViewHolder {

        TextView tvHora, tvTarifa;

        public CostoHoraView(@NonNull View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvTarifa = itemView.findViewById(R.id.tvTarifa);

        }
    }
}
