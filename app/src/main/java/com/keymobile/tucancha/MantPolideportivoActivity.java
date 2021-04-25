package com.keymobile.tucancha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keymobile.tucancha.entidades.Cancha;
import com.keymobile.tucancha.entidades.Polideportivo;

import java.util.ArrayList;

public class MantPolideportivoActivity extends AppCompatActivity {

    ArrayList<Polideportivo> lista_polideportivos = new ArrayList<>();
    ArrayAdapter<Polideportivo> listaAdapter;

    ListView lvPolideportivos;
    FloatingActionButton fabAgregar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mant_polideportivo);

        AsignarReferencias();
        InicializarFirebase();
        ListarDatos();
    }


    private void AsignarReferencias() {

        lvPolideportivos = findViewById(R.id.lvPolideportivos);

        lvPolideportivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Polideportivo poli = lista_polideportivos.get(position);

                Toast.makeText(getApplicationContext(), poli.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        fabAgregar = findViewById(R.id.fabAgregar);
        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void InicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void ListarDatos() {
        databaseReference.child("polideportivos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista_polideportivos.clear();

                for (DataSnapshot snap: snapshot.getChildren()) {
                    Polideportivo poli = snap.getValue(Polideportivo.class);
                    lista_polideportivos.add(poli);
                }

                listaAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, lista_polideportivos);
                lvPolideportivos.setAdapter(listaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}