package com.example.geoguesserjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

import java.util.Locale;
import java.util.concurrent.TimeUnit;



public class StreetViewActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {
    private MapManagementService mapManagementService;
    private CountDownTimer countDownTimer;
    private UnknownCityToGuessCityLatLng unknownCityToGuessCityLatLng;
    private boolean twoPlayers;

    /**
     * initializes the activity, sets its layout, and retrieves
     * a reference to a Street View panorama fragment in order to display Street View imagery in the app
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        twoPlayers = getIntent()
                .getBooleanExtra("twoPlayers",false);
        mapManagementService = new MapManagementService(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googlemapstreetview);
        assert streetViewPanoramaFragment != null;
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        timerManagement();
    }

    /**
     * Sets up the StreetViewPanorama object to display imagery for a particular city taken
     * randomly from the database and configures
     * user interactions for the Street View display
     */
    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        LatLng unknownCityLatLng = mapManagementService.findCityLatLang("Sofia", this);
        this.unknownCityToGuessCityLatLng = new UnknownCityToGuessCityLatLng(unknownCityLatLng);
        streetViewPanorama.setPosition(unknownCityToGuessCityLatLng.getUnknownCityLatLng(), StreetViewSource.OUTDOOR);
        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().orientation(new StreetViewPanoramaOrientation(20, 20))
                        .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                        .build(), 2000
        );
    }

    /**
     * Open the MapsActivity and pass object with the
     * key "unknownCityToGuessCityLatLng" which has two LatLng properties (unknownCityLatLng and guessCityLatLng)
     * to the destination activity
     */
    public void OnOpenMapsActivityClick(View view) {
        goToMapsActivity();
    }

    private void timerManagement() {
        TextView textView = findViewById(R.id.timer);

        long duration = TimeUnit.MINUTES.toMillis(1);
        this.countDownTimer = new CountDownTimer(duration, 1000) {
            int counter = 0;

            /**
             * When tick, convert millisecond to minute and second
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 10000) {
                    textView.setTextSize(75);
                    // Get the layout params of the view
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                    //Set the centerInParent property to true
                    params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    // Update the layout params of the view
                    textView.setLayoutParams(params);
                    // Remove drawable
                    textView.setCompoundDrawables(null, null, null, null);

                }
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String sDuration = String.format(Locale.getDefault(), "%02d : %02d", minutes, seconds);
                textView.setText(sDuration);
                counter++;
            }

            /**
             *When finish hide text view and display toast with message
             */
            @Override
            public void onFinish() {
                textView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Времето свърши!", Toast.LENGTH_LONG).show();
                goToMapsActivity();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    private void goToMapsActivity() {
        Intent intent = new Intent(StreetViewActivity.this, MapsActivity.class);
        intent.putExtra("unknownCityToGuessCityLatLng", unknownCityToGuessCityLatLng);
        intent.putExtra("twoPlayers",twoPlayers);
        startActivity(intent);
    }
}