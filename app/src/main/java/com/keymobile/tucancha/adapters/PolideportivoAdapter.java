package com.keymobile.tucancha.adapters;


import android.app.Activity;
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
import com.keymobile.tucancha.entidades.Polideportivo;

import java.util.ArrayList;

public class PolideportivoAdapter extends RecyclerView.Adapter<PolideportivoAdapter.PolideportivoView> {

    private Context context;
    private ArrayList<Polideportivo> listapolideportivos = new ArrayList<>();

    public ArrayList<Polideportivo> getListapolideportivos() {
        return listapolideportivos;
    }

    public void setListapolideportivos(ArrayList<Polideportivo> listapolideportivos) {
        this.listapolideportivos = listapolideportivos;
    }

    public PolideportivoAdapter(Context context, ArrayList<Polideportivo> polideportivos) {
        this.context = context;
        this.listapolideportivos = polideportivos;
    }

    @NonNull
    @Override
    public PolideportivoAdapter.PolideportivoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_polideportivo, parent, false);
        return new PolideportivoView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolideportivoView holder, int position) {

        Polideportivo poli = listapolideportivos.get(position);
        holder.tvNombre.setText(String.valueOf(poli.getNombre()));

        holder.tvCanchas.setText(String.valueOf(poli.getCanchas()));
        holder.tvRating.setText(String.valueOf(poli.getRating()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked " + poli.getNombre(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listapolideportivos.size();
    }


    public class PolideportivoView extends RecyclerView.ViewHolder {

        TextView tvNombre, tvCanchas, tvRating;
        ImageView ivPolideportivo;

        public PolideportivoView(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCanchas = itemView.findViewById(R.id.tvCanchas);
            tvRating = itemView.findViewById(R.id.tvRating);
            ivPolideportivo = itemView.findViewById(R.id.ivPolideportivo);

        }
    }

}
