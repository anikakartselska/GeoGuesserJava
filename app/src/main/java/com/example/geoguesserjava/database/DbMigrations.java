package com.example.geoguesserjava.database;

import static com.example.geoguesserjava.utils.CityConstants.*;
import static com.example.geoguesserjava.utils.UserConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DbMigrations extends SQLiteOpenHelper {

    private static final String DB_NAME = "geo_guesser";

    private static final int DB_VERSION = 1;


    public DbMigrations(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private void createUserTable(SQLiteDatabase db) {
        String userQuery = "CREATE TABLE " + USER_TABLE + " ("
                + USER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT,"
                + EMAIL + " TEXT,"
                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + LEVEL + " INTEGER,"
                + POINTS + " DOUBLE,"
                + ALREADY_PICKED_CITIES + " TEXT)";
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL(userQuery);
    }

    private void createCities(SQLiteDatabase db) {
        String cityQuery = "CREATE TABLE " + CITIES_TABLE + " ("
                + CITY_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CITY_NAME + " TEXT,"
                + CITY_LEVEL + " INTEGER)";
        db.execSQL("DROP TABLE IF EXISTS " + CITIES_TABLE);
        db.execSQL(cityQuery);
    }

    private void populateCities(SQLiteDatabase db){
        List<ContentValues> valuesToInsert = new ArrayList<>();
        for(String name: cityNames){
            ContentValues values = new ContentValues();
            values.put(CITY_NAME, name);
            values.put(CITY_LEVEL, 1);
            valuesToInsert.add(values);
        }
        valuesToInsert.forEach(it -> db.insert(CITIES_TABLE, null, it));
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createCities(db);
        populateCities(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // Perform database schema migration
            switch (oldVersion) {
                case 1:
                    //migrate
                    break;
                default:
                    break;
            }
        } else {
            onCreate(db);
        }

    }
}