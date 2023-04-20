package com.example.geoguesserjava.server;
/**
 * Services is a utility class that provides static access to singleton instances of
 * UserHttpClient and CityHttpClient classes. These instances are initialized only once and
 * are shared throughout the application.
 *
 * To obtain an instance of UserHttpClient or CityHttpClient, call the corresponding
 * static method, getUserHttpClient() or getCityHttpClient().
 *
 * This class uses the Singleton design pattern to ensure that only one instance of
 * UserHttpClient and CityHttpClient exists in the application.
 */
public class Services {

    private static final UserHttpClient userHttpClient = new UserHttpClient();
    private static final CityHttpClient cityHttpClient = new CityHttpClient();

    //make the constructor private so that this class cannot be
    //instantiated
    private Services(){}

    public static UserHttpClient getUserHttpClient(){
        return userHttpClient;
    }

    public static CityHttpClient getCityHttpClient(){
        return cityHttpClient;
    }
}
