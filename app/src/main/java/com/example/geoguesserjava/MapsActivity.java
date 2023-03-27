package com.example.geoguesserjava;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.geoguesserjava.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private MapManagementService mapManagementService;
    private Button showIfGuessed;
    private UnknownCityToGuessCityLatLng unknownCityToGuessCityLatLng;

    private boolean twoPlayers;

    private int playerNumber = 1;

    public final LatLngBounds bulgariaBounds = new LatLngBounds(
            new LatLng(41.2352, 22.3571), // southwest corner of Bulgaria
            new LatLng(44.2176, 28.6098)  // northeast corner of Bulgaria
    );

    /**
     * It initializes the activity and sets up the map view to display a Google Map
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        unknownCityToGuessCityLatLng = getIntent()
                .getParcelableExtra("unknownCityToGuessCityLatLng");
        twoPlayers = getIntent()
                .getBooleanExtra("twoPlayers", false);
        //getting the UnknownCityToGuessCityLatLng from the StreetViewActivity where unknownCityLatLang is set
        mapManagementService = new MapManagementService(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);//Initialize the Google Map
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);
        // Set the bounds of the camera target to Bulgaria
        mMap.setLatLngBoundsForCameraTarget(bulgariaBounds);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapManagementService.centerOfBulgaria, 6));
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // update the position of the marker
        MarkerOptions markerOptions = new MarkerOptions();
        if (bulgariaBounds.contains(latLng)) {//checking if the user clicked on the bounds of bulgaria
            markerOptions.position(latLng); //putting marker to the clicked location
            unknownCityToGuessCityLatLng.setGuessCityLatLng(latLng);
            mMap.clear();//clearing the map from previous selection
            mMap.addMarker(markerOptions);//adding the new selection
            showIfGuessed = findViewById(R.id.show_if_guessed);
            showIfGuessed.setVisibility(View.VISIBLE); //set the button for guessing visible so the user can click on it
        }
    }

    /**
     * Shows the distance between the guess city and the unknown city.
     * Positions the camera so the user can see the whole map and the line between
     * the two cities and hides the button so the user is no longer able to guess.
     *
     * @param view represents the Button view that was clicked to trigger the method.
     */
    public void OnShowIfGuessedClick(View view) {
        if (playerNumber == 1 && twoPlayers) {
            playerNumber++;
            mMap.clear();

        } else {
            mapManagementService.drawLineBetweenTwoLocationsOnTheMap(unknownCityToGuessCityLatLng.getGuessCityLatLng(), unknownCityToGuessCityLatLng.getUnknownCityLatLng(), mMap);
            DialogsService.showResultsAfterGuessingTheCityDialog(mapManagementService.findDistanceBetweenTwoCitiesInKilometers(unknownCityToGuessCityLatLng.getGuessCityLatLng(),
                            unknownCityToGuessCityLatLng.getUnknownCityLatLng()),
                    this,
                    (dialog, which) -> {
                        goToUserScreenActivity();
                    });
            showIfGuessed = findViewById(R.id.show_if_guessed);
            showIfGuessed.setVisibility(View.GONE);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapManagementService.centerOfBulgaria, 6));
        }
    }

    private void goToUserScreenActivity() {
        Intent intent = new Intent(MapsActivity.this, UserScreenActivity.class);
        startActivity(intent);
    }

}

