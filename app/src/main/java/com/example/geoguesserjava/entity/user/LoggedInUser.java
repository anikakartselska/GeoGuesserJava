package com.example.geoguesserjava.entity.user;

public class LoggedInUser {
    private static User currentUser = new User();

    public static User getCurrentUser() {
        return currentUser;
    }

    public LoggedInUser setCurrentUser(User currentUser) {
        LoggedInUser.currentUser = currentUser;
        return this;
    }
}