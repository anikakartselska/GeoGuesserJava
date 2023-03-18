package com.example.geoguesserjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {
    private StreetViewPanorama streetViewPanorama;
    private boolean secondlocation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googlemapstreetview);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        Button mapsActivityButton = findViewById(R.id.open_maps_activity);
        mapsActivityButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
        Geocoder geocoder = new Geocoder(this); // create a Geocoder object
        String cityName = "Florence"; // specify the name of the city you want to get the location of
        LatLng latLng1 = findCityLatLang(cityName, geocoder);
        LatLng latLng2 = findCityLatLang("Istanbul", geocoder);
        float[] results = new float[1];
        Location.distanceBetween(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results);
        TextView distance = (TextView) findViewById(R.id.distance);
        distance.setText(String.valueOf(results[0] / 1000));
        streetViewPanorama.setPosition(latLng1, StreetViewSource.OUTDOOR);

        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().orientation(new StreetViewPanoramaOrientation(20, 20))
                        .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                        .build(), 2000
        );
        streetViewPanorama.setOnStreetViewPanoramaChangeListener(panoramachangelistner);
    }

    private StreetViewPanorama.OnStreetViewPanoramaChangeListener panoramachangelistner = new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
        @Override
        public void onStreetViewPanoramaChange(@NonNull StreetViewPanoramaLocation streetViewPanoramaLocation) {
            Toast.makeText(MainActivity.this, "Location update", Toast.LENGTH_SHORT).show();
        }
    };

    private LatLng findCityLatLang(String cityName, Geocoder geocoder) {
        double latitude = 0;
        double longitude = 0;
        try {
            List<Address> addresses = geocoder.getFromLocationName(cityName, 1); // get a list of addresses that match the city name
            if (addresses != null && !addresses.isEmpty()) { // check if any addresses were returned
                Address address = addresses.get(0); // get the first address in the list
                latitude = address.getLatitude(); // get the latitude of the address
                longitude = address.getLongitude(); // get the longitude of the address
                // do something with the latitude and longitude values, such as display them in a map
            } else {
                // handle case where no addresses were returned
            }
        } catch (IOException e) {
            // handle any errors that occur while trying to get the location
        }
        return new LatLng(latitude, longitude);
    }


}