package com.example.geoguesserjava.entity;

public class CreateUserDto {
    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String username;

    public CreateUserDto(String password, String email, String firstName, String lastName, String username) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
}
