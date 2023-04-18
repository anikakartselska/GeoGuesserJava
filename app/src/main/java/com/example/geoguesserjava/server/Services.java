package com.example.geoguesserjava.server;

public class Services {

    private static final UserHttpClient userHttpClient = new UserHttpClient();
    private static final CityHttpClient cityHttpClient = new CityHttpClient();

    //make the constructor private so that this class cannot be
    //instantiated
    private Services(){}

    //Get the only object available
    public static UserHttpClient getUserHttpClient(){
        return userHttpClient;
    }

    public static CityHttpClient getCityHttpClient(){
        return cityHttpClient;
    }
}
