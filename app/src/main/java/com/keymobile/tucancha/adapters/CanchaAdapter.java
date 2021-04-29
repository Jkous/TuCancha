package com.keymobile.tucancha.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.keymobile.tucancha.NuevaReservaActivity;
import com.keymobile.tucancha.R;
import com.keymobile.tucancha.entidades.Cancha;
import com.keymobile.tucancha.entidades.CostoHora;
import com.keymobile.tucancha.entidades.Dia;

import java.util.ArrayList;

public class CanchaAdapter  extends RecyclerView.Adapter<CanchaAdapter.CanchaView>   {

    private Activity activity;
    private Context context;
    private ArrayList<Cancha> lista_items = new ArrayList<>();

    public ArrayList<Cancha> getLista() {
        return lista_items;
    }

    public void setLista(ArrayList<Cancha> lista_items) {
        this.lista_items = lista_items;
    }

    public CanchaAdapter(Activity activity, Context context, ArrayList<Cancha> polideportivos) {
        this.activity = activity;
        this.context = context;
        this.lista_items = polideportivos;
    }

    @NonNull
    @Override
    public CanchaAdapter.CanchaView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cancha, parent, false);
        return new CanchaAdapter.CanchaView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanchaAdapter.CanchaView holder, int position) {

        Cancha cancha = lista_items.get(position);

        holder.tvNombre.setText(String.valueOf(cancha.getNombre()));

        holder.tvDireccion.setText(String.valueOf(cancha.getDireccion()));
        holder.tvLikes.setText(String.valueOf(cancha.getLikes()));

        for (Dia dia:cancha.getDias()) {

            if(dia.getAbreviatura().equals("L")) holder.dia1.setChecked(true);
            else if(dia.getAbreviatura().equals("MA")) holder.dia2.setChecked(true);
            else if(dia.getAbreviatura().equals("MI")) holder.dia3.setChecked(true);
            else if(dia.getAbreviatura().equals("J")) holder.dia4.setChecked(true);
            else if(dia.getAbreviatura().equals("V")) holder.dia5.setChecked(true);
            else if(dia.getAbreviatura().equals("S")) holder.dia6.setChecked(true);
            else if(dia.getAbreviatura().equals("D")) holder.dia7.setChecked(true);

        }

        holder.tbLikes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    FirebaseDatabase.getInstance().getReference().child("canchas")
                            .child(cancha.getId()).child("likes").setValue(ServerValue.increment(1));

                } else {
                    FirebaseDatabase.getInstance().getReference().child("canchas")
                            .child(cancha.getId()).child("likes").setValue(ServerValue.increment(-1));
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NuevaReservaActivity.class);
                intent.putExtra(NuevaReservaActivity.KEY_ID_CANCHA, cancha.getId());
                activity.startActivity(intent);
                //Toast.makeText(context, "Clicked " + cancha.getNombre(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return lista_items.size();
    }


    public class CanchaView extends RecyclerView.ViewHolder {

        ImageView ivCancha;
        TextView tvNombre, tvLikes, tvDireccion;
        ToggleButton tbLikes;
        ToggleButton dia1, dia2, dia3, dia4, dia5, dia6, dia7;

        public CanchaView(@NonNull View itemView) {
            super(itemView);
            ivCancha = itemView.findViewById(R.id.ivCancha);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvLikes = itemView.findViewById(R.id.tvLikes);

            tbLikes = itemView.findViewById(R.id.likes);

            dia1 = itemView.findViewById(R.id.dia_l);
            dia2 = itemView.findViewById(R.id.dia_ma);
            dia3 = itemView.findViewById(R.id.dia_mi);
            dia4 = itemView.findViewById(R.id.dia_j);
            dia5 = itemView.findViewById(R.id.dia_v);
            dia6 = itemView.findViewById(R.id.dia_s);
            dia7 = itemView.findViewById(R.id.dia_d);

        }
    }

}
