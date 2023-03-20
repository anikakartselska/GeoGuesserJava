package com.example.geoguesserjava.entity;

import java.util.List;
import java.util.Objects;

public class User extends AbstractEntity{
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Integer level;
    private Double points;
    private List<String> alreadyPickedCityNames;

    public User(String username, String password, String email, String firstName, String lastName, Integer level, Double points, List<String> alreadyPickedCityNames) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.points = points;
        this.alreadyPickedCityNames = alreadyPickedCityNames;
    }

    public User(Integer id, String username, String password, String email, String firstName, String lastName, Integer level, Double points, List<String> alreadyPickedCityNames) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.points = points;
        this.alreadyPickedCityNames = alreadyPickedCityNames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getAlreadyPickedCityNames() {
        return alreadyPickedCityNames;
    }

    public void setAlreadyPickedCityNames(List<String> alreadyPickedCityNames) {
        this.alreadyPickedCityNames = alreadyPickedCityNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(level, user.level) && Objects.equals(points, user.points) && Objects.equals(alreadyPickedCityNames, user.alreadyPickedCityNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, email, firstName, lastName, level, points, alreadyPickedCityNames);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level=" + level +
                ", points=" + points +
                ", alreadyPickedCityNames=" + alreadyPickedCityNames +
                '}';
    }
}
