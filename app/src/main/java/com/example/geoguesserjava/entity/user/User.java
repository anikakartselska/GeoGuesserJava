package com.example.geoguesserjava.entity.user;

public class User {
    private Long id;

    private String email;
    private Integer level;
    private Double points;
    private String firstName;

    private String lastName;

    private String username;

    private String registrationDate;

    private byte[] image;


    public User(Long id, String email, Integer level, Double points, String firstName, String lastName, String username, String registrationDate, byte[] image) {
        this.id = id;
        this.email = email;
        this.level = level;
        this.points = points;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.registrationDate = registrationDate;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public User setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
