package com.example.geoguesserjava;

import android.app.Application;
import android.util.LruCache;

import com.example.geoguesserjava.entity.user.User;
import com.example.geoguesserjava.entity.user.UsersCache;
import com.example.geoguesserjava.server.UserHttpClient;

import java.util.List;

public class BulgariaGuesserApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UsersCache.fillCache();
    }
}
