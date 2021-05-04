package com.keymobile.tucancha;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.keymobile.tucancha.utils.PermissionUtils;
import com.keymobile.tucancha.utils.StringUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

public class MapaCanchasActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private PlacesClient placesClient;

    private GoogleMap mMap;
    TextView tvDireccion;

    private Button btnSeleccionarPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_canchas);
        setResult(Activity.RESULT_CANCELED);

        tvDireccion = findViewById(R.id.tvDireccion);
        btnSeleccionarPosition = findViewById(R.id.btnSeleccionarPosition);
        btnSeleccionarPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng centerMap = mMap.getCameraPosition().target;

                Intent intent = new Intent();
                intent.putExtra("direccion", String.valueOf(tvDireccion.getText()));
                intent.putExtra("latitude", centerMap.latitude);
                intent.putExtra("longitude", centerMap.longitude);

                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });

        Places.initialize(MapaCanchasActivity.this, getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng chorrillos = new LatLng(-12.1823365, -77.0004542);

        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position_marker, 16));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chorrillos));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        mMap.setOnCameraIdleListener(this);
        enableMyLocation();

        findCurrentPlace();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);

            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    ACCESS_FINE_LOCATION);
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public void onCameraIdle() {

        getPositionAddress();

    }


    private void getPositionAddress() {

        //LatLng centerMap = mMap.getCameraPosition().target;

        //findCurrentPlace();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        findCurrentPlaceWithPermissions();
    }

    private void findCurrentPlace() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    this,
                    "Both ACCESS_WIFI_STATE & ACCESS_FINE_LOCATION permissions are required",
                    Toast.LENGTH_SHORT)
                    .show();
        }

        // Note that it is not possible to request a normal (non-dangerous) permission from
        // ActivityCompat.requestPermissions(), which is why the checkPermission() only checks if
        // ACCESS_FINE_LOCATION is granted. It is still possible to check whether a normal permission
        // is granted or not using ContextCompat.checkSelfPermission().
        if (checkPermission(ACCESS_FINE_LOCATION)) {
            findCurrentPlaceWithPermissions();
        }
    }


    @RequiresPermission(allOf = {ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    private void findCurrentPlaceWithPermissions() {
        //setLoading(true);


        List<Place.Field> placeFields = Collections.singletonList(Place.Field.ADDRESS);
        FindCurrentPlaceRequest currentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields);


        Task<FindCurrentPlaceResponse> currentPlaceTask = placesClient.findCurrentPlace(currentPlaceRequest);



        currentPlaceTask.addOnSuccessListener(
                (response) -> {

                    PlaceLikelihood hood = response.getPlaceLikelihoods().get(0);
//                        for (PlaceLikelihood hood: response.getPlaceLikelihoods()) {
//                            //Log.d("LOCATION", hood.getPlace().getAddress());
//                        }

                    tvDireccion.setText(hood.getPlace().getAddress());
                });
        currentPlaceTask.addOnFailureListener(
                (exception) -> {
                    exception.printStackTrace();
                    //tvDireccion.setText(exception.getMessage());
                });

    }

    private boolean checkPermission(String permission) {
        boolean hasPermission =
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        }
        return hasPermission;
    }

}