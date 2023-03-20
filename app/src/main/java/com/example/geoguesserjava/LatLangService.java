package com.example.geoguesserjava;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.example.geoguesserjava.repository.CityRepository;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class LatLangService {

    public LatLng unknownCityLocation;
    private final Geocoder geocoder;

    public LatLangService(Context context) {
        this.geocoder = new Geocoder(context);
        CityRepository cityRepository = new CityRepository(context);
        this.unknownCityLocation = findCityLatLang(cityRepository.find(1).getName());
    }

    public LatLng findCityLatLang(String cityName) {
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

    public float findDistanceBetweenTwoCitiesInKilometers(LatLng latLng1, LatLng latLng2) {
        float[] results = new float[1];
        Location.distanceBetween(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results);
        return results[0] / 1000;
    }

}
