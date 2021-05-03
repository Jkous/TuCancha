package com.keymobile.tucancha;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keymobile.tucancha.adapters.CanchaAdapter;
import com.keymobile.tucancha.adapters.PartidoAdapter;
import com.keymobile.tucancha.entidades.Cancha;
import com.keymobile.tucancha.entidades.Reserva;

import java.util.ArrayList;
import java.util.UUID;

public class PartidosFragment extends Fragment {

    FirebaseAuth auth;

    ArrayList<Reserva> lista_partidos = new ArrayList<>();
    RecyclerView rvPartidos;
    PartidoAdapter partidoAdapter;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public PartidosFragment() {
        // Required empty public constructor
    }


    public static PartidosFragment newInstance(String param1, String param2) {
        PartidosFragment fragment = new PartidosFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partidos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InicializarFirebase();

        rvPartidos = view.findViewById(R.id.rvPartidos);
        partidoAdapter = new PartidoAdapter(getActivity(), getContext(), lista_partidos);
        rvPartidos.setAdapter(partidoAdapter);
        rvPartidos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        ListarDatos();
    }

    private void InicializarFirebase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void ListarDatos() {
        databaseReference.child("reservas").orderByChild("uidUsuario").equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista_partidos.clear();

                for (DataSnapshot snap: snapshot.getChildren()) {
                    Reserva partido = snap.getValue(Reserva.class);
                    lista_partidos.add(partido);
                }

                ListarCanchas();
                //partidoAdapter.setLista(lista_partidos);
                //partidoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void ListarCanchas() {

        ArrayList<Cancha> canchas = new ArrayList<>();

        databaseReference.child("canchas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful() && task.getResult() != null) {

                    DataSnapshot snapshot = task.getResult();


                    for (DataSnapshot item:snapshot.getChildren()) {
                        Cancha c = item.getValue(Cancha.class);
                        canchas.add(c);
                    }

                    AgregarCanchas(canchas);
                }

            }
        });

        /*
        databaseReference.child("canchas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item:snapshot.getChildren()) {
                    Cancha c = item.getValue(Cancha.class);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


    }

    private void AgregarCanchas(ArrayList<Cancha> canchas) {


        for (Reserva r:lista_partidos) {
            for (Cancha c:canchas) {
                if(c.getId().equals(r.getIdCancha()))
                    r.setDataCancha(c);

            }
        }

        partidoAdapter.setLista(lista_partidos);
        partidoAdapter.notifyDataSetChanged();

    }



}