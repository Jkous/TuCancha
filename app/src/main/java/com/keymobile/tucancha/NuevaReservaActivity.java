package com.keymobile.tucancha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keymobile.tucancha.adapters.HoraReservaAdapter;
import com.keymobile.tucancha.adapters.PolideportivoAdapter;
import com.keymobile.tucancha.entidades.Cancha;
import com.keymobile.tucancha.entidades.CostoHora;
import com.keymobile.tucancha.entidades.Dia;
import com.keymobile.tucancha.entidades.Polideportivo;
import com.keymobile.tucancha.entidades.Reserva;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class NuevaReservaActivity extends AppCompatActivity {

    public static final String KEY_ID_CANCHA = "KEY_ID_CANCHA";

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView tvNombreCancha;
    TextView btnEscogerFecha;

    RecyclerView rvReservas;
    ArrayList<Reserva> lista_reservas = new ArrayList<>();
    HoraReservaAdapter reservaAdapter;

    private String IdCancha;
    private Date fecha_consulta;

    ArrayList<CostoHora> lista_horas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_reserva);

        IdCancha = getIntent().getStringExtra(KEY_ID_CANCHA);
        AsignarReferencias();
        InicializarFirebase();
        ConsultarInformacionCancha();
    }

    private void InicializarFirebase() {
        auth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(NuevaReservaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void AsignarReferencias() {

        tvNombreCancha = findViewById(R.id.tvNombreCancha);

        rvReservas = findViewById(R.id.rvHorasReserva);
        reservaAdapter = new HoraReservaAdapter(NuevaReservaActivity.this, getApplicationContext(), lista_reservas);
        rvReservas.setAdapter(reservaAdapter);
        rvReservas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btnEscogerFecha = findViewById(R.id.btnEscogerFecha);

        btnEscogerFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerFragment();

            }
        });

    }

    private void showDatePickerFragment() {
        DialogFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("AÑO", String.valueOf(year));
                fecha_consulta = new Date(year, month, dayOfMonth);
                btnEscogerFecha.setText(dayOfMonth + "/" + (month+1) + "/" + year );

                ConsultarHorasReserva();
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void ConsultarInformacionCancha() {

        databaseReference.child("canchas").child(IdCancha).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists() && snapshot.getValue() != null) {

                    Cancha cancha = snapshot.getValue(Cancha.class);

                    lista_horas = cancha.getHoras();
                    ListarReservas();
                    //Toast.makeText(NuevaReservaActivity.this, cancha.getNombre(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ListarReservas() {
        lista_reservas.clear();

        Date fecha = Calendar.getInstance().getTime();

        for (CostoHora costo :lista_horas) {

            fecha.setHours(costo.getHora());

            Reserva r = new Reserva();
            r.setId(UUID.randomUUID().toString());
            r.setHoraReserva(costo.getHora());
            r.setPrecioReserva(costo.getPrecio());
            r.setEstado("disponible");
            r.setFechaReserva(fecha);

            lista_reservas.add(r);
        }

        reservaAdapter.setLista(lista_reservas);
        reservaAdapter.notifyDataSetChanged();

    }

    private void ConsultarHorasReserva() {


        databaseReference.child("reservas").child(IdCancha).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists() && snapshot.getValue() != null) {

                    //Reserva reserva = snapshot.getValue(Reserva.class);

                    //Toast.makeText(NuevaReservaActivity.this, reserva.getFechaReserva().toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//
//        databaseReference.child("reservas").child(IdCancha).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }


    public void RegistrarReserva(Reserva reserva) {
        //Toast.makeText(NuevaReservaActivity.this, String.valueOf(reserva.getHoraReserva()), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(NuevaReservaActivity.this)
                .setMessage("Está seguro de reservar la cancha a las " + reserva.getHoraReserva() + " horas ?")
                .setTitle("Reserva de cancha")
                .setPositiveButton("Reservar", (dialog, which) -> {

                    Date now = Calendar.getInstance().getTime();

                    reserva.setUidUsuario(auth.getCurrentUser().getUid());
                    reserva.setFechaReserva(fecha_consulta);
                    reserva.setFechaCreacion(now);
                    reserva.setIdCancha(IdCancha);
                    reserva.setEstado("Reservado");

                    databaseReference.child("reservas").child(reserva.getId()).setValue(reserva)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    finish();
                                }
                            });
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });
        builder.create().show();

    }


}