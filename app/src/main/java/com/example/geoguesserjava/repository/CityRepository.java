package com.example.geoguesserjava.repository;


import static com.example.geoguesserjava.utils.CityConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.geoguesserjava.entity.City;

import java.util.ArrayList;
import java.util.List;

public class CityRepository extends AbstractRepository<City> {
    private final String[] fromCity = {CITY_ID_COL, CITY_NAME, CITY_LEVEL};
    private final String whereId = "id = ?";

    private  String[] equalsToId(Integer id){
        return new String[]{id.toString()};
    }
    public CityRepository(Context context) {
        super(context);
    }

    @Override
    public void create(City entity) {
        ContentValues values = new ContentValues();

        values.put(CITY_NAME, entity.getName());
        values.put(CITY_LEVEL, entity.getLevel());

        writableDb.insert(CITIES_TABLE, null, values);
        System.out.println("Created city:" + entity.getName());
    }


    @Override
    public City find(Integer id) {
        Cursor cursor = readableDb.query(
                CITIES_TABLE,
                fromCity,
                whereId,
                equalsToId(id),
                null,
                null,
                null
        );
        City city;
        try {
           city = mapToCity(cursor).get(0);
        }catch (Exception e){
            String errorMessage = "City with id: " + id + "fetch failed..";
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        System.out.println("Found city:" + city);
        return city;
    }

    @Override
    public List<City> findAll() {
        List<City> cities;
        String sortOrder = CITY_LEVEL + " ASC";
        Cursor cursor = readableDb.query(
                CITIES_TABLE,
                fromCity,
                null,
                null,
                null,
                null,
                sortOrder
        );
        cities = mapToCity(cursor);
        System.out.println("Fetching all cities succeeded");
        return cities;
    }


    @Override
    public City update(City entity) {
        ContentValues updateCity = new ContentValues();
        updateCity.put(CITY_NAME, entity.getName());
        updateCity.put(CITY_LEVEL, entity.getLevel());
        int updatedRows = writableDb.update(CITIES_TABLE, updateCity, whereId, equalsToId(entity.getId()));
        if (updatedRows > 0) {
            System.out.println("City with id: " + entity.getId() + " was updated successfully..");
        } else {
            String errorMessage = "City with id: " + entity.getId() + "update failed..";
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        return entity;
    }

    @Override
    public void delete(Integer id) {
        String[] equalsToId = {String.valueOf(id)};

        int deletedRows = writableDb.delete(CITIES_TABLE, whereId, equalsToId);

        if (deletedRows > 0) {
            System.out.println("City with id: " + id + " was deleted successfully..");
        } else {
            String errorMessage = "City with id: " + id + "deletion failed..";
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    /** Recommended usage is fetching data
     *
     * @param cursor cursor from which reads and maps data to City object
     * @return List of cities, because it read the cursor while moveToNext() is valid,
     * if only one object is desired as return it will be stored as first list element
     */
    private List<City> mapToCity(Cursor cursor ) {
        List<City> cities = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cityId = cursor.getInt(cursor.getColumnIndexOrThrow(CITY_ID_COL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(CITY_NAME));
            int level = cursor.getInt(cursor.getColumnIndexOrThrow(CITY_LEVEL));
            cities.add( new City(cityId, name, level));
        }
        return cities;
    }
}
