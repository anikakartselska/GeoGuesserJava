package com.example.geoguesserjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;


public class StreetViewActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {
    private StreetViewPanorama streetViewPanorama;
    private  LatLangService  latLangService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        latLangService = new LatLangService(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googlemapstreetview);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
        LatLng latLng = latLangService.unknownCityLocation;
        streetViewPanorama.setPosition(latLng, StreetViewSource.OUTDOOR); //setting the panorama position to the unknown city

        streetViewPanorama.setStreetNamesEnabled(true);//showing the street name on the street view
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().orientation(new StreetViewPanoramaOrientation(20, 20))
                        .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                        .build(), 2000
        );
//        streetViewPanorama.setOnStreetViewPanoramaChangeListener(panoramachangelistner);
    }

//    private StreetViewPanorama.OnStreetViewPanoramaChangeListener panoramachangelistner = new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
//        @Override
//        public void onStreetViewPanoramaChange(@NonNull StreetViewPanoramaLocation streetViewPanoramaLocation) {
//            Toast.makeText(MainActivity.this, "Location update", Toast.LENGTH_SHORT).show();
//        }
//    };

    public void OnOpenMapsActivityClick(View view){
        Intent intent = new Intent(StreetViewActivity.this, MapsActivity.class);
        startActivity(intent);
    }

}