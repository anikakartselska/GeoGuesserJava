package com.example.geoguesserjava.entity;

public class City {
    private Long id;
    private String name;
    private Integer level;


    public City(Long id, String name, Integer level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public City() {
    }

    public Long getId() {
        return id;
    }

    public City setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public City setLevel(Integer level) {
        this.level = level;
        return this;
    }
}
