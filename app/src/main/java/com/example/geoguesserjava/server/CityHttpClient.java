package com.example.geoguesserjava.server;

import com.example.geoguesserjava.entity.City;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class CityHttpClient {

    private static final String GET_RANDOM_CITY = "city/random";

    private static final String GET_RANDOM_CITY_ALL_LEVEL = "city/random/all-levels";

    private static final String GET = "GET";
    private static final Gson GSON = new Gson();

    private static final SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();

    public City getRandomCity() {
        City city = null;
        springServerAsyncTask.execute(GET, GET_RANDOM_CITY);
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        city = parseCity(result);
        return city;
    }

    public City getRandomCityFromAnyLevel(){
        City city = null;
        springServerAsyncTask.execute(GET, GET_RANDOM_CITY_ALL_LEVEL);
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        city = parseCity(result);
        return city;
    }

    private static City parseCity(String response) {
        City city = null;
        try {
            city = GSON.fromJson(response, City.class);
        } catch (Exception e) {
            System.out.println(response);
        }
        return city;
    }
}