package com.keymobile.tucancha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keymobile.tucancha.adapters.CostoHoraAdapter;
import com.keymobile.tucancha.adapters.PolideportivoAdapter;
import com.keymobile.tucancha.entidades.Cancha;
import com.keymobile.tucancha.entidades.CostoHora;
import com.keymobile.tucancha.entidades.Dia;
import com.keymobile.tucancha.entidades.Polideportivo;

import java.util.ArrayList;
import java.util.UUID;

public class UpsertCanchaActivity extends AppCompatActivity {

    public static final String KEY_ID_CANCHA = "KEY_ID_CANCHA";

    String IdCancha;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button btnAddHora, btnGuardarCancha;
    EditText etNombre, etDireccion;

    ToggleButton dia1, dia2, dia3, dia4, dia5, dia6, dia7;

    RecyclerView rvCostoHoras;
    ArrayList<CostoHora> horas = new ArrayList<>();
    CostoHoraAdapter horasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upsert_cancha);
        AsignarReferencias();

        Intent intent = getIntent();
        IdCancha = intent.getStringExtra(KEY_ID_CANCHA);

        InicializarFirebase();
        ConsultarDatosCancha(IdCancha);
    }

    private void AsignarReferencias() {

        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);

        dia1 = findViewById(R.id.dia_l);
        dia2 = findViewById(R.id.dia_ma);
        dia3 = findViewById(R.id.dia_mi);
        dia4 = findViewById(R.id.dia_j);
        dia5 = findViewById(R.id.dia_v);
        dia6 = findViewById(R.id.dia_s);
        dia7 = findViewById(R.id.dia_d);

        btnAddHora = findViewById(R.id.btnAddHora);
        btnAddHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarDialogo(null, true);
            }
        });

        btnGuardarCancha = findViewById(R.id.btnGuardarCancha);
        btnGuardarCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarDatosCancha();
            }
        });


        rvCostoHoras = findViewById(R.id.rvCostoHora);
        horasAdapter = new CostoHoraAdapter(UpsertCanchaActivity.this, horas);
        rvCostoHoras.setAdapter(horasAdapter);
        rvCostoHoras.setLayoutManager(new LinearLayoutManager(UpsertCanchaActivity.this, LinearLayoutManager.VERTICAL, false));

    }


    private void InicializarFirebase() {

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void ConsultarDatosCancha(String idCancha) {

        databaseReference.child("canchas").child(IdCancha)//.orderByChild("id").equalTo(idCancha)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists() && snapshot.getValue() != null) {


                            Cancha cancha = snapshot.getValue(Cancha.class);

                            etNombre.setText(cancha.getNombre());
                            etDireccion.setText(cancha.getDireccion());

                            dia1.setChecked(true);

                            for (Dia dia:cancha.getDias()) {
                                if(dia.getAbreviatura().equals("L")) dia1.setChecked(true);
                                else if(dia.getAbreviatura().equals("MA")) dia2.setChecked(true);
                                else if(dia.getAbreviatura().equals("MI")) dia3.setChecked(true);
                                else if(dia.getAbreviatura().equals("J")) dia4.setChecked(true);
                                else if(dia.getAbreviatura().equals("V")) dia5.setChecked(true);
                                else if(dia.getAbreviatura().equals("S")) dia6.setChecked(true);
                                else if(dia.getAbreviatura().equals("D")) dia7.setChecked(true);

                            }

                            horas.clear();
                            horas.addAll(cancha.getHoras());
                            horasAdapter.setLista(horas);
                            horasAdapter.notifyDataSetChanged();

                            Log.d("HOJO", cancha.toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void GuardarDatosCancha() {

        Cancha cancha = new Cancha();
        cancha.setId(IdCancha);
        cancha.setNombre(String.valueOf(etNombre.getText()));
        cancha.setDireccion(String.valueOf(etDireccion.getText()));
        cancha.setHoras(horas);

        ArrayList<Dia> dias = cancha.getDias();

        if(dia1.isChecked()) dias.add(new Dia("L", true));
        if(dia2.isChecked()) dias.add(new Dia("MA", true));
        if(dia3.isChecked()) dias.add(new Dia("MI", true));
        if(dia4.isChecked()) dias.add(new Dia("J", true));
        if(dia5.isChecked()) dias.add(new Dia("V", true));
        if(dia6.isChecked()) dias.add(new Dia("S", true));
        if(dia7.isChecked()) dias.add(new Dia("D", true));

        cancha.setDias(dias);

        databaseReference.child("canchas").child(cancha.getId()).setValue(cancha)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        finish();
                    }
                });

    }


    private void MostrarDialogo(CostoHora costoHora, boolean is_new) {

        if(costoHora == null) {
            costoHora = new CostoHora();
            costoHora.setHora(20);
            costoHora.setId(UUID.randomUUID().toString());
            costoHora.setPrecio(0f);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(UpsertCanchaActivity.this);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE); //getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_costo_hora, null);


        // Asignar valores
        EditText etIdCostoHora = view.findViewById(R.id.etIdCostoHora);
        etIdCostoHora.setText(costoHora.getId());
        EditText etTarifa = view.findViewById(R.id.etTarifa);
        etTarifa.setText(String.valueOf(costoHora.getPrecio()));
        TimePicker tp = view.findViewById(R.id.tpHora);
        tp.setIs24HourView(true);
        //tp.findViewById(Resources.getSystem().getIdentifier("minutes", "id", "android")).setVisibility(View.GONE);
        tp.setCurrentHour(costoHora.getHora());
        tp.setCurrentMinute(0);



        //tp.findViewById(Resources.getSystem().getIdentifier("separator", "id", "android")).setVisibility(View.INVISIBLE);

        builder.setView(view)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CostoHora costo = new CostoHora();
                        costo.setId(String.valueOf(etIdCostoHora.getText()));
                        costo.setPrecio(Double.parseDouble(etTarifa.getText().toString()));
                        costo.setHora(tp.getCurrentHour());

                        if(is_new) {

                            horas.add(costo);
                            horasAdapter.setLista(horas);
                            horasAdapter.notifyDataSetChanged();
                        } else {
                            //int index = horas.indexOf(costo);
                            horas.remove(costo);
                            horas.add(costo);
                        }

                        Toast.makeText(UpsertCanchaActivity.this, etIdCostoHora.getText(), Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

    }



}