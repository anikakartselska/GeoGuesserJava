package com.example.geoguesserjava.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoguesserjava.ui.utils.MapManagementService;
import com.example.geoguesserjava.R;
import com.example.geoguesserjava.ui.utils.Constants;
import com.example.geoguesserjava.ui.utils.UnknownCityToGuessCityLatLng;
import com.example.geoguesserjava.server.CityHttpClient;
import com.example.geoguesserjava.server.Services;
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

    private CityHttpClient cityHttpClient = Services.getCityHttpClient();
    private boolean twoPlayers;

    /**
     * Initializes the activity, sets its layout, and retrieves
     * a reference to a Street View panorama fragment in order to display Street View imagery in the app
     * Retrieving a boolean extra value named twoPlayers from the intent that started this activity.
     * The twoPlayers variable will be used for the MapsActivity to define if the game is for two players or not.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        twoPlayers = getIntent()
                .getBooleanExtra("twoPlayers", false);
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
        String cityName = twoPlayers ? cityHttpClient.getRandomCityFromAnyLevel().getName() :cityHttpClient.getRandomCity().getName();
        LatLng unknownCityLatLng = mapManagementService.findCityLatLang(cityName, this);
        this.unknownCityToGuessCityLatLng = new UnknownCityToGuessCityLatLng(cityName, unknownCityLatLng);
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

    /**
     * used to manage the countdown timer in an Android application.
     * It initializes a new CountDownTimer object with a duration of 2 minutes and an interval of 1 second
     */
    private void timerManagement() {
        TextView textView = findViewById(R.id.timer);

        long duration = TimeUnit.MINUTES.toMillis(2);
        this.countDownTimer = new CountDownTimer(duration, 2000) {
            int counter = 0;

            /**
             * When tick, convert millisecond to minute and second
             * This method updates the text of a TextView with the remaining time in minutes and seconds format.
             * If the remaining time is less than 10 seconds, it increases the size of the TextView and centers it on the screen.
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
             * It then calls the goToMapsActivity() method to navigate to the MapsActivity
             */
            @Override
            public void onFinish() {
                textView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), Constants.TIME_IS_UP, Toast.LENGTH_LONG).show();
                goToMapsActivity();
            }
        }.start();
    }

    /**
     * The method is used to cancel the countdown timer when the activity is being paused so the
     * timer won't last in the next activities.
     */
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    /**
     * Used to navigate to the MapsActivity class from the current StreetViewActivity class.
     * It creates an Intent object and sets the destination activity to MapsActivity.
     * It passes the unknownCityToGuessCityLatLng object and a boolean twoPlayers which
     * shows if the game is for two players or for one
     */

    private void goToMapsActivity() {
        Intent intent = new Intent(StreetViewActivity.this, MapsActivity.class);
        intent.putExtra("unknownCityToGuessCityLatLng", unknownCityToGuessCityLatLng);
        intent.putExtra("twoPlayers", twoPlayers);
        startActivity(intent);
    }
}