package com.example.geoguesserjava.ui.activities;

import static com.example.geoguesserjava.ui.utils.Constants.LEVEL_FOR_TWO_PLAYERS_GAME;
import static com.example.geoguesserjava.ui.utils.Constants.getPersonIcon;
import static com.example.geoguesserjava.ui.utils.Constants.RESULTS_FROM_GAME_TITLE;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoguesserjava.ui.utils.DialogsService;
import com.example.geoguesserjava.ui.utils.MapManagementService;
import com.example.geoguesserjava.R;
import com.example.geoguesserjava.ui.utils.Constants;
import com.example.geoguesserjava.ui.utils.UnknownCityToGuessCityLatLng;
import com.example.geoguesserjava.databinding.ActivityMapsBinding;
import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.entity.user.UpdateUserDto;
import com.example.geoguesserjava.entity.user.User;
import com.example.geoguesserjava.server.Services;
import com.example.geoguesserjava.server.UserHttpClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private MapManagementService mapManagementService;
    private Button showIfGuessed;
    private UnknownCityToGuessCityLatLng unknownCityToGuessCityLatLng;
    private boolean twoPlayers;
    private int playerNumber = 1;

    private TextView usernameText;

    private LatLng guessCityOfTheFirstPlayerInTwoPlayerGame = null;

    private UserHttpClient userHttpClient = Services.getUserHttpClient();

    public final LatLngBounds bulgariaBounds = new LatLngBounds(
            new LatLng(41.2352, 22.3571), // southwest corner of Bulgaria
            new LatLng(44.2176, 28.6098)  // northeast corner of Bulgaria
    );

    /**
     * It initializes the activity and sets up the map view to display a Google Map
     * Gets the twoPlayer parameter from the @StreetViewActivity so it can handle the
     * case of two players or only one player
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

        usernameText = findViewById(R.id.username_text);
        usernameText.setText(Constants.USERNAME);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);//Initialize the Google Map
    }

    /**
     * initializes a GoogleMap object and sets some of its properties in the onMapReady() method.
     * Specifically, it enables all gestures on the map, sets the camera target bounds to Bulgaria,
     * sets the initial camera position to the center of Bulgaria with a zoom level of 6,
     * and sets the map click listener to the current class instance
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);
        // Set the bounds of the camera target to Bulgaria
        mMap.setLatLngBoundsForCameraTarget(bulgariaBounds);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapManagementService.centerOfBulgaria, 6));
        mMap.setOnMapClickListener(this);
    }

    /**
     * This method is called when the user clicks on the map. It checks if the clicked location is within the bounds of Bulgaria,
     * clears the map from previous selections, adds a new marker to the clicked location, sets the guess city coordinates,
     * and makes the button for guessing visible.
     *
     * @param latLng
     */
    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (bulgariaBounds.contains(latLng)) {//checking if the user clicked on the bounds of bulgaria
            mMap.clear();//clearing the map from previous selection
            addMarkerWithIcon(latLng, null);
            unknownCityToGuessCityLatLng.setGuessCityLatLng(latLng);
            showIfGuessed = findViewById(R.id.show_if_guessed);
            showIfGuessed.setVisibility(View.VISIBLE); //set the button for guessing visible so the user can click on it
        }
    }

    /**
     * This method handles three cases.
     * If the game is for two and the first player is playing it gives him the chance to guess the
     * city by clicking the button.
     * If the fave is for two and the second player is playing it gives him the chance to guess
     * and after that shows the result from the game and draws the distance between the two guess cities
     * and the unknown city.
     * The same goes for the case where the player is one -> after his guess try the results from
     * the game are shown.
     * In all of the cases:
     * Positions the camera so the user can see the whole map and the line between
     * the two cities and hides the button so the user is no longer able to guess.
     *
     * @param view represents the Button view that was clicked to trigger the method.
     */
    public void OnShowIfGuessedClick(View view) {
        mMap.clear();
        if (playerNumber == 1 && twoPlayers) {
            playerNumber++;
            guessCityOfTheFirstPlayerInTwoPlayerGame = unknownCityToGuessCityLatLng.getGuessCityLatLng();
            usernameText.setText(Constants.FRIEND);

        } else if (playerNumber == 2 && twoPlayers) {
            Double firstPlayerKilometers = mapManagementService.findDistanceBetweenTwoCitiesInKilometers(unknownCityToGuessCityLatLng.getGuessCityLatLng(), unknownCityToGuessCityLatLng.getUnknownCityLatLng());
            Double secondPlayerKilometers = mapManagementService.findDistanceBetweenTwoCitiesInKilometers(guessCityOfTheFirstPlayerInTwoPlayerGame, unknownCityToGuessCityLatLng.getUnknownCityLatLng());
            showDialogAfterGuess(firstPlayerKilometers, secondPlayerKilometers, Constants.DOUBLE_ZERO);
            mapManagementService.drawLineBetweenTwoLocationsOnTheMap(guessCityOfTheFirstPlayerInTwoPlayerGame, unknownCityToGuessCityLatLng.getUnknownCityLatLng(), mMap);
            mapManagementService.drawLineBetweenTwoLocationsOnTheMap(unknownCityToGuessCityLatLng.getGuessCityLatLng(), unknownCityToGuessCityLatLng.getUnknownCityLatLng(), mMap);
            addMarkerWithIcon(guessCityOfTheFirstPlayerInTwoPlayerGame, getPersonIcon(this));
            addMarkerWithIcon(unknownCityToGuessCityLatLng.getUnknownCityLatLng(), null);
            addMarkerWithIcon(unknownCityToGuessCityLatLng.getGuessCityLatLng(), getPersonIcon(this));
        } else {
            Double firstPlayerKilometers = mapManagementService.findDistanceBetweenTwoCitiesInKilometers(unknownCityToGuessCityLatLng.getGuessCityLatLng(), unknownCityToGuessCityLatLng.getUnknownCityLatLng());
            Double wonPoints = calculatePoints(LoggedInUser.getCurrentUser().getLevel(), firstPlayerKilometers);
            showDialogAfterGuess(firstPlayerKilometers, -1.0, wonPoints);
            updateUserAfterGameByTheWonPoints(wonPoints);
            mapManagementService.drawLineBetweenTwoLocationsOnTheMap(unknownCityToGuessCityLatLng.getGuessCityLatLng(), unknownCityToGuessCityLatLng.getUnknownCityLatLng(), mMap);
            addMarkerWithIcon(unknownCityToGuessCityLatLng.getUnknownCityLatLng(), null);
            addMarkerWithIcon(unknownCityToGuessCityLatLng.getGuessCityLatLng(), getPersonIcon(this));
        }

        showIfGuessed = findViewById(R.id.show_if_guessed);
        showIfGuessed.setVisibility(View.GONE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapManagementService.centerOfBulgaria, 6));
    }

    /**
     * The showDialogAfterGuess() method is a private method that is called within the OnShowIfGuessedClick() method. It is responsible for creating a dialog box that displays the results of the player's guess. The dialog box contains information about the distance between the guessed city and the actual city, as well as the number of points earned by the player based on their guess.
     * If the game is being played between two players, the method calculates the distance between the first player's guess and the actual city, and then calculates the distance between the second player's guess and the actual city. It then determines which player was closer to the actual city and displays a message indicating the winner.
     * The method also provides a button for the user to go to the user screen activity.
     *
     * @param firstPlayerKilometers
     * @param secondPlayerKilometers
     */

    private void showDialogAfterGuess(Double firstPlayerKilometers, Double secondPlayerKilometers, Double wonPoints) {
        String htmlMessage = Constants.constructMessageForTheUnknownCityName(unknownCityToGuessCityLatLng.getUnknownCityName()).concat(
                Constants.constructMessageForThePlayerResults(Constants.USERNAME,
                        firstPlayerKilometers,
                        wonPoints == Constants.DOUBLE_ZERO ? calculatePoints(LEVEL_FOR_TWO_PLAYERS_GAME, firstPlayerKilometers) : wonPoints
                ));
        if (secondPlayerKilometers > Constants.DOUBLE_ZERO) {
            htmlMessage = htmlMessage.concat(Constants.constructMessageForThePlayerResults(Constants.FRIEND,
                    secondPlayerKilometers,
                    calculatePoints(LEVEL_FOR_TWO_PLAYERS_GAME, secondPlayerKilometers))
            ).concat(
                    Constants.messageForTheWinnerInTwoPlayersGame(
                            firstPlayerKilometers < secondPlayerKilometers ? Constants.USERNAME : Constants.FRIEND
                    )
            )
            ;
        }
        DialogsService.dialogAtTheBottomOfTheWindow(RESULTS_FROM_GAME_TITLE,
                htmlMessage,
                Constants.GO_TO_USER_PAGE,
                this,
                (dialog, which) -> goToUserScreenActivity());
    }

    /**
     * Creates a new MarkerOptions object and sets the position of the marker to the specified latLng location.
     * If a custom markerIcon is provided, it sets the icon of the marker to the specified markerIcon,
     * else it gets the default icon for the Google maps.
     * Finally, it adds the marker to the map using the addMarker() method of the GoogleMap object
     *
     * @param latLng     the location to set the marker
     * @param markerIcon icon of the marker
     */
    private void addMarkerWithIcon(LatLng latLng, BitmapDescriptor markerIcon) {
        // update the position of the marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng); //putting marker to the clicked location
        if (markerIcon != null) {
            markerOptions.icon(markerIcon);
        }
        mMap.addMarker(markerOptions);//adding the new selection
    }

    /**
     * This method is used to navigate the user to the UserScreenActivity when the game ends
     */
    private void goToUserScreenActivity() {
        Intent intent = new Intent(MapsActivity.this, UserScreenActivity.class);
        startActivity(intent);
    }

    /**
     * Updates the current logged-in user's information after a game by adding the won points to their total points and
     * recalculating their level and points according to the game rules.
     *
     * @param wonPoints the number of points the user won in the game
     */
    private void updateUserAfterGameByTheWonPoints(Double wonPoints) {
        User loggedInUser = LoggedInUser.getCurrentUser();
        double newPoints = LoggedInUser.getCurrentUser().getPoints() + wonPoints;
        newPoints = BigDecimal.valueOf(newPoints).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Integer level = calculateUserLevel(loggedInUser.getLevel(), newPoints);
        newPoints = calculateUserPoints(newPoints);
        userHttpClient.updateUser(new UpdateUserDto(loggedInUser.getId(), level, newPoints, loggedInUser.getImage()));
    }

    /**
     * Calculates the points earned by a user for a given number of kilometers and user level.
     *
     * @param userLevel  the current user level
     * @param kilometers the number of kilometers traveled
     * @return the points earned by the user
     */
    private Double calculatePoints(int userLevel, Double kilometers) {
        return (500 - kilometers) / userLevel;
    }

    /**
     * Calculates the level of a user based on their current level and points earned.
     *
     * @param userLevel the current user level
     * @param points    the total points earned by the user
     * @return the new level of the user
     */
    private int calculateUserLevel(int userLevel, Double points) {
        return points >= 1000.0 ? ++userLevel : userLevel;
    }

    /**
     * Calculates the points earned by a user after deducting 1000 points (if the user has more than 1000 points).
     *
     * @param points the total points earned by the user
     * @return the adjusted points earned by the user
     */
    private Double calculateUserPoints(Double points) {
        return points >= 1000.0 ? (Double) (points - 1000.0) : points;
    }
}

