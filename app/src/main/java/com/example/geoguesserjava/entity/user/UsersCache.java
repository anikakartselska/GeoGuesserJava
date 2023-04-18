package com.example.geoguesserjava.entity.user;

import android.util.LruCache;

import com.example.geoguesserjava.server.UserHttpClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsersCache {
    public static LruCache<String, List<User>> usersCache = new LruCache<>(100);

    public static void fillCache() {
        UserHttpClient userHttpClient = new UserHttpClient();
        List<User> users = userHttpClient.getAllUsers();
        if (usersCache.maxSize() < users.size()) {
            usersCache.resize(users.size());
        }
        usersCache.put("users", users);
    }

    public static void updateStudentInCache(User updatedUser) {
        List<User> users = usersCache.get("users");
        List<User> updatedUsers = users.stream().map(user ->
                {
                    if (Objects.equals(user.getId(), updatedUser.getId())) {
                        return updatedUser;
                    } else {
                        return user;
                    }
                }
        ).collect(Collectors.toList());
        usersCache.put("users", updatedUsers);
    }

}
