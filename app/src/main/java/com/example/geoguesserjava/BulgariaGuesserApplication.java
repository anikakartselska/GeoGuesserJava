package com.example.geoguesserjava;

import android.app.Application;
import android.util.LruCache;

import com.example.geoguesserjava.entity.user.User;
import com.example.geoguesserjava.server.UserHttpClient;

import java.util.List;

public class BulgariaGuesserApplication extends Application {

    public static LruCache<String, List<User>> usersCache = new LruCache<>(100);

    @Override
    public void onCreate() {
        super.onCreate();
        UserHttpClient userHttpClient = new UserHttpClient();
        List<User> users = userHttpClient.getAllUsers();
        if(usersCache.maxSize()<users.size()){
            usersCache.resize(users.size());
        }
        usersCache.put("users",users);
    }
}
