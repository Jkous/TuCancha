package com.keymobile.tucancha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.UUID;

public class MantCanchaActivity extends AppCompatActivity {

    ArrayList<Cancha> lista_canchas = new ArrayList<>();
    ArrayAdapter<Cancha> listaAdapter;

    ListView lvCanchas;
    FloatingActionButton fabAgregar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mant_cancha);

        AsignarReferencias();
        InicializarFirebase();
        ListarDatos();
    }

    private void AsignarReferencias() {

        lvCanchas = findViewById(R.id.lvCanchas);
        lvCanchas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cancha cancha = lista_canchas.get(position);

                Intent intent = new Intent(getApplicationContext(), UpsertCanchaActivity.class);
                intent.putExtra(UpsertCanchaActivity.KEY_ID_CANCHA, cancha.getId());
                startActivity(intent);

            }
        });

        fabAgregar = findViewById(R.id.fabAgregar);
        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpsertCanchaActivity.class);
                intent.putExtra(UpsertCanchaActivity.KEY_ID_CANCHA, UUID.randomUUID().toString());
                startActivity(intent);
            }
        });
    }

    private void InicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void ListarDatos() {
        databaseReference.child("canchas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista_canchas.clear();

                for (DataSnapshot snap: snapshot.getChildren()) {
                    Cancha cancha = snap.getValue(Cancha.class);
                    lista_canchas.add(cancha);
                }

                listaAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, lista_canchas);
                lvCanchas.setAdapter(listaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}