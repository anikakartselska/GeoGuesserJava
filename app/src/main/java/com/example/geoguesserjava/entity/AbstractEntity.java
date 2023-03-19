package com.example.geoguesserjava.entity;

import java.util.Objects;

public abstract class AbstractEntity {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AbstractEntity() {
    }

    public AbstractEntity(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        AbstractEntity abstractEntity = (AbstractEntity) o;
        return Objects.equals(id, abstractEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
