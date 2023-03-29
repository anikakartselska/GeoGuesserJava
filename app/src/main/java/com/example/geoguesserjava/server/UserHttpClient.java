package com.example.geoguesserjava.server;

import com.example.geoguesserjava.entity.City;
import com.example.geoguesserjava.entity.CreateUserDto;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class UserHttpClient {

    private static final String CREATE_USER = "user/create";
    private static final Gson GSON = new Gson();

    private static final String POST = "POST";
    public void createUser(){
        CreateUserDto user  = new CreateUserDto("p@ssw0rd", "john222sadadoe@example.com", "John", "Doe", "johndoe123");
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(POST,CREATE_USER, GSON.toJson(user));
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

}
