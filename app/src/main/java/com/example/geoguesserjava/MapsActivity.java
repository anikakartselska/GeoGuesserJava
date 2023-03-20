package com.example.geoguesserjava;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.geoguesserjava.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLangService latLangService;
    private LatLng guessCity;

    private Button showIfGuessed;
    private final LatLng centerOfBulgaria = new LatLng(42.766279, 25.238569);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        latLangService = new LatLangService(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);


        MarkerOptions markerOptions = new MarkerOptions();

        LatLngBounds bulgariaBounds = new LatLngBounds(
                new LatLng(41.2352, 22.3571), // southwest corner of Bulgaria
                new LatLng(44.2176, 28.6098)  // northeast corner of Bulgaria
        );

// Set the bounds of the camera target to Bulgaria
        mMap.setLatLngBoundsForCameraTarget(bulgariaBounds);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerOfBulgaria, 6));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                // update the position of the marker
                if (bulgariaBounds.contains(latLng)) {
                    markerOptions.position(latLng);
                    guessCity = latLng;
                    mMap.clear();
                    mMap.addMarker(markerOptions);
                    showIfGuessed = findViewById(R.id.show_if_guessed);
                    showIfGuessed.setVisibility(View.VISIBLE);
                }
            }

        });

    }

    public void OnShowIfGuessedClick(View view) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(guessCity)
                .add(latLangService.unknownCityLocation)
                .color(Color.RED);
        mMap.addPolyline(polylineOptions);
        createAlertDialog();
        showIfGuessed = findViewById(R.id.show_if_guessed);
        showIfGuessed.setVisibility(View.GONE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerOfBulgaria, 6));
    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Разликата в разтоянията между двете локации е:");
        builder.setMessage(String.valueOf(latLangService.findDistanceBetweenTwoCitiesInKilometers(guessCity, latLangService.unknownCityLocation)));

        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(128, 255, 255, 255)));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }
}

