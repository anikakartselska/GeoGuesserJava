package com.example.geoguesserjava;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

/**
 * Contains helper methods for the map functionality
 */
public class MapManagementService {
    private final Geocoder geocoder;

    public final LatLng centerOfBulgaria = new LatLng(42.766279, 25.238569);

    public MapManagementService(Context context) {
        this.geocoder = new Geocoder(context);
    }

    /**
     * Attempts to find the latitude and longitude of a given city name using
     * the Android Geocoder class, and returns a LatLng object that represents the location of the city.
     * If an error occurs or the city name cannot be found, the method shows an error dialog and
     * returns a default location.
     */
    public LatLng findCityLatLang(String cityName, Context context) {
        double latitude;
        double longitude;
        try {
            List<Address> addresses = geocoder.getFromLocationName(cityName, 1); // get a list of addresses that match the city name
            if (addresses != null && !addresses.isEmpty()) { // check if any addresses were returned
                Address address = addresses.get(0); // get the first address in the list
                latitude = address.getLatitude(); // get the latitude of the address
                longitude = address.getLongitude(); // get the longitude of the address
                return new LatLng(latitude, longitude);
            }  // handle case where no addresses were returned
            else throw new IOException();
        } catch (IOException e) {
            DialogsService.errorDialog(StringConstants.CITY_NOT_FOUND_ERROR, context);
            return centerOfBulgaria;
        }

    }

    /**
     * Calculates the distance in kilometers between two locations represented by
     * their latitude and longitude, using the Location.distanceBetween() method
     * @param latLng1 the latitude and longitude of the first city
     * @param latLng2 the latitude and longitude of the second city
     * @return the distance in kilometers
     */
    public float findDistanceBetweenTwoCitiesInKilometers(LatLng latLng1, LatLng latLng2) {
        float[] results = new float[1];
        Location.distanceBetween(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results);
        return results[0] / 1000;
    }

    /**
     * draws a line between two locations on a Google Map, using a polyline with a red color,
     * by adding the polyline to the map using the GoogleMap.addPolyline() method
     * @param latLng1 the latitude and longitude of the first city
     * @param latLng2 the latitude and longitude of the second city
     * @param mMap the map to draw a line on
     */
    public void drawLineBetweenTwoLocationsOnTheMap(LatLng latLng1, LatLng latLng2, GoogleMap mMap) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(latLng1)
                .add(latLng2)
                .color(Color.RED);
        mMap.addPolyline(polylineOptions);
    }

}
