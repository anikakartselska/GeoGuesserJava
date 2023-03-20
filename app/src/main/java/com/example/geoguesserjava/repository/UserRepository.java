package com.example.geoguesserjava.repository;

import static com.example.geoguesserjava.utils.UserConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.geoguesserjava.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository extends AbstractRepository<User> {
    private final String[] fromUser = {USER_ID_COL, USERNAME, PASSWORD, EMAIL, FIRST_NAME, LAST_NAME, LEVEL, POINTS, ALREADY_PICKED_CITIES};
    private final String whereId = "id = ?";

    private String[] equalsToId(Integer id) {
        return new String[]{id.toString()};
    }

    public UserRepository(Context context) {
        super(context);
    }

    @Override
    public void create(User entity) {
        ContentValues values = new ContentValues();

        values.put(USERNAME, entity.getUsername());
        values.put(PASSWORD, entity.getPassword());
        values.put(EMAIL, entity.getEmail());
        values.put(FIRST_NAME, entity.getFirstName());
        values.put(LAST_NAME, entity.getLastName());
        values.put(LEVEL, entity.getLevel());
        values.put(POINTS, entity.getPoints());
        values.put(ALREADY_PICKED_CITIES, entity.getAlreadyPickedCityNames().toString());

        writableDb.insert(USER_TABLE, null, values);
        System.out.println("Created user:" + entity.getUsername());
    }


    @Override
    public User find(Integer id) {
        Cursor cursor = readableDb.query(
                USER_TABLE,
                fromUser,
                whereId,
                equalsToId(id),
                null,
                null,
                null
        );
        User user;
        try {
            user = mapToUser(cursor).get(0);
        } catch (Exception e) {
            String errorMessage = "user with id: " + id + "fetch failed..";
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        System.out.println("Found user:" + user);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        String sortOrder = USERNAME + " ASC";
        Cursor cursor = readableDb.query(
                USER_TABLE,
                fromUser,
                null,
                null,
                null,
                null,
                sortOrder
        );
        users = mapToUser(cursor);
        System.out.println("Fetching all users succeeded");
        return users;
    }


    @Override
    public User update(User entity) {
        ContentValues updateUser = new ContentValues();
        updateUser.put(USERNAME, entity.getUsername());
        updateUser.put(PASSWORD, entity.getPassword());
        updateUser.put(EMAIL, entity.getEmail());
        updateUser.put(FIRST_NAME, entity.getFirstName());
        updateUser.put(LAST_NAME, entity.getLastName());
        updateUser.put(LEVEL, entity.getLevel());
        updateUser.put(POINTS, entity.getPoints());
        updateUser.put(ALREADY_PICKED_CITIES, entity.getAlreadyPickedCityNames().toString());
        int updatedRows = writableDb.update(USER_TABLE, updateUser, whereId, equalsToId(entity.getId()));
        if (updatedRows > 0) {
            System.out.println("user with id: " + entity.getId() + " was updated successfully..");
        } else {
            String errorMessage = "user with id: " + entity.getId() + "update failed..";
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        return entity;
    }

    @Override
    public void delete(Integer id) {
        String[] equalsToId = {String.valueOf(id)};

        int deletedRows = writableDb.delete(USER_TABLE, whereId, equalsToId);

        if (deletedRows > 0) {
            System.out.println("user with id: " + id + " was deleted successfully..");
        } else {
            String errorMessage = "user with id: " + id + "deletion failed..";
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * Recommended usage is fetching data
     *
     * @param cursor cursor from which reads and maps data to City object
     * @return List of cities, because it read the cursor while moveToNext() is valid,
     * if only one object is desired as return it will be stored as first list element
     */
    private List<User> mapToUser(Cursor cursor) {
        List<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID_COL));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL));
            int level = cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL));
            double points = cursor.getDouble(cursor.getColumnIndexOrThrow(POINTS));
            List<String> pickedCities = Arrays.asList(cursor.getString(cursor.getColumnIndexOrThrow(POINTS)).split(","));
            users.add(
                    new User(
                            userId, username,
                            password, email,
                            firstName, lastName,
                            level, points, pickedCities
                    )
            );
        }
        return users;
    }
}
