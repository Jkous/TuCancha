package com.keymobile.tucancha;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keymobile.tucancha.entidades.Cancha;
import com.keymobile.tucancha.entidades.Reserva;

public class RutaPartidoActivity extends FragmentActivity implements OnMapReadyCallback {

    Reserva reserva;
    Cancha cancha;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_partido);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        InicializarFirebase();
        Intent data = getIntent();
        CargarDatos(data.getStringExtra("ID_RESERVA"));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


    }


    private void InicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public void CargarDatos(String id_reserva) {

        CargarDatosReserva(id_reserva);

    }

    public void CargarDatosReserva(String id_reserva) {

        databaseReference.child("reservas").child(id_reserva).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Reserva partido = snapshot.getValue(Reserva.class);
                reserva = partido;
                CargarDatosCancha(reserva.getIdCancha());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void CargarDatosCancha(String id_cancha) {

        databaseReference.child("canchas").child(id_cancha).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Cancha _cancha = snapshot.getValue(Cancha.class);
                cancha = _cancha;
                ProcesarCancha();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void ProcesarCancha() {

        mMap.clear();

        LatLng marker = new LatLng(cancha.getLatitude(), cancha.getLongitude());
        mMap.addMarker(
                new MarkerOptions().position(marker).title(cancha.getNombre())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_soccer))
        );
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 16));
    }

}