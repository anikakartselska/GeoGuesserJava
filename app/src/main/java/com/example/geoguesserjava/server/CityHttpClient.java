package com.example.geoguesserjava.server;

import com.example.geoguesserjava.entity.City;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class CityHttpClient {

    private static final String GET_RANDOM_CITY = "city/random";

    public City getRandomCity(){
        City city = null;
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(GET_RANDOM_CITY);
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
        Gson gson = new Gson();
        return gson.fromJson(response, City.class);
    }
}