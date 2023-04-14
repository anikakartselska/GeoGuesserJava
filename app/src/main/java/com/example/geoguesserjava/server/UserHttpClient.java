package com.example.geoguesserjava.server;

import com.example.geoguesserjava.entity.user.CreateUserDto;
import com.example.geoguesserjava.entity.user.LoggedInUser;
import com.example.geoguesserjava.entity.user.UpdateUserDto;
import com.example.geoguesserjava.entity.user.User;
import com.example.geoguesserjava.entity.user.UserDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserHttpClient {

    private static final LoggedInUser loggedInUser = new LoggedInUser();
    GsonBuilder gsonBuilder = new GsonBuilder();

    private static final Gson GSON = new Gson();
    private static final String CREATE_USER = "user/create";
    private static final String LOGIN_USER = "user/login";

    private static final String LOGOUT_USER = "user/logout";

    private static final String USERS_GET_ALL = "user/all";

    private static final String USER_UPDATE = "user/update";

    private static final String EMAIL_EXISTS = "user/email-exists";

    private static final String USERNAME_EXISTS = "user/username-exists";

    private static final String POST = "POST";

    private static final String GET = "GET";

    public User createUser(CreateUserDto user) { //        CreateUserDto user  = new CreateUserDto("password", "john222sadadoe@example.com", "John", "Doe", "johndoe");
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(POST, CREATE_USER, GSON.toJson(user));
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        User createdUser = parseUser(result);
        loggedInUser.setCurrentUser(createdUser);
        return createdUser;
    }

    public User loginUser(String usernameAndPassSeparatedWith3Hashtags) {//usernameAndPassSeparatedWith3Hashtags  = "johndoe###password";
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(POST, LOGIN_USER, usernameAndPassSeparatedWith3Hashtags);
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        User user = parseUser(result);
        loggedInUser.setCurrentUser(user);
        return user;
    }


    public User updateUser(UpdateUserDto user) { // UpdateUserDto user  = new UpdateUserDto(1L, 1, 69.77, null);
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(POST, USER_UPDATE, GSON.toJson(user));
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        User updatedUser = parseUser(result);
        loggedInUser.setCurrentUser(updatedUser);
        return updatedUser;
    }
    public Boolean usernameExists(String username) { // UpdateUserDto user  = new UpdateUserDto(1L, 1, 69.77, null);
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(POST, USERNAME_EXISTS, username);
        String result = "";
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result.equals("true");
    }

    public Boolean emailExists(String email) { // UpdateUserDto user  = new UpdateUserDto(1L, 1, 69.77, null);
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(POST, EMAIL_EXISTS, email);
        String result = "";
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result.equals("true");
    }

    public List<User> getAllUsers() {
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(GET, USERS_GET_ALL);
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return parseUsers(result);
    }

    public void logoutUser() {
        SpringServerAsyncTask springServerAsyncTask = new SpringServerAsyncTask();
        springServerAsyncTask.execute(GET, LOGOUT_USER);
        String result = null;
        try {
            result = springServerAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if(result != null && result.equals("Logged out successfully!")) {
            loggedInUser.setCurrentUser(null);
            System.out.println(result);
        }else{
            System.out.println("Error logging out!");
        }
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * return null if server response does not contain user
     *
     * @param response
     * @return
     */
    private static User parseUser(String response) {
        User user = null;
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
            Gson gson = gsonBuilder.create();
            user = gson.fromJson(response, User.class);
        } catch (Exception e) {
            System.out.println(response);
        }
        return user;
    }


    /**
     * return null if server response does not contain user
     *
     * @param response
     * @return parsed response to list of users
     */
    private static List<User> parseUsers(String response) {
        List<User> users = null;
        try {
            Type userListType = new TypeToken<List<User>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
            Gson gson = gsonBuilder.create();
            users = gson.fromJson(response, userListType);
        } catch (Exception e) {
            System.out.println(response);
        }
        return users;
    }
}
