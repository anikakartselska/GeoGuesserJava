package com.example.geoguesserjava.entity;

import java.util.Objects;

public class City extends AbstractEntity{
    private String name;
    private Integer level;

    public City(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    public City(Integer id, String name, Integer level) {
        super(id);
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return name.equals(city.name) && level.equals(city.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level);
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
