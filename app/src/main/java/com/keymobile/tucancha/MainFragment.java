package com.keymobile.tucancha;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keymobile.tucancha.adapters.CanchaAdapter;
import com.keymobile.tucancha.adapters.PolideportivoAdapter;
import com.keymobile.tucancha.entidades.Cancha;
import com.keymobile.tucancha.entidades.Polideportivo;
import com.keymobile.tucancha.utils.GsonArrayRequest;
import com.keymobile.tucancha.utils.GsonRequest;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView rvPolideportivos;
    ArrayList<Polideportivo> polideportivos = new ArrayList<>();
    PolideportivoAdapter poliadapter;


    RecyclerView rvCanchas;
    ArrayList<Cancha> lista_canchas = new ArrayList<>();
    CanchaAdapter canchaAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvPolideportivos = view.findViewById(R.id.rvPolideportivos);
        poliadapter = new PolideportivoAdapter(getContext(), polideportivos);
        rvPolideportivos.setAdapter(poliadapter);
        rvPolideportivos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvCanchas = view.findViewById(R.id.rvCanchas);
        canchaAdapter = new CanchaAdapter(getContext(), lista_canchas);
        rvCanchas.setAdapter(canchaAdapter);
        rvCanchas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        CargarPolideportivos();
        CargarCanchas();
        //ListarPolideportivos();
    }

    private void ListarPolideportivos() {


        String url = getString(R.string.URL_API_REST) + "polideportivos";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        GsonArrayRequest<Polideportivo> myReq = new GsonArrayRequest<>(url, Polideportivo.class, null,
            response -> {
                polideportivos = response;
                Log.d("polis", String.valueOf(polideportivos.size()));
                poliadapter.setListapolideportivos(polideportivos);
                poliadapter.notifyDataSetChanged();
            },
            error -> {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        );

        queue.add(myReq);

    }


    private void CargarPolideportivos() {

        databaseReference.child("polideportivos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                polideportivos.clear();

                for (DataSnapshot item:snapshot.getChildren()) {
                    Polideportivo p = item.getValue(Polideportivo.class);
                    polideportivos.add(p);
                }

                poliadapter.setListapolideportivos(polideportivos);
                poliadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void CargarCanchas() {

        databaseReference.child("canchas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista_canchas.clear();

                for (DataSnapshot item:snapshot.getChildren()) {
                    Cancha c = item.getValue(Cancha.class);
                    lista_canchas.add(c);
                }

                canchaAdapter.setLista(lista_canchas);
                canchaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}