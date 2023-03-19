package com.example.geoguesserjava.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.geoguesserjava.database.DbMigrations;
import com.example.geoguesserjava.entity.AbstractEntity;

import java.util.List;

public abstract class AbstractRepository<T extends AbstractEntity> {

    protected final SQLiteDatabase writableDb;
    protected final SQLiteDatabase readableDb;
    public AbstractRepository(Context context) {
        DbMigrations db= new DbMigrations(context);
        this.writableDb = db.getWritableDatabase();
        this.readableDb = db.getReadableDatabase();
    }

    private void clearDbConnections(){
        writableDb.close();
        readableDb.close();
    }

    /**
     * automatically called when garbage collector is about to destruct this object,
     * the connections are going to be closed
     */
    @Override
    protected void finalize() {
        clearDbConnections();
    }
    public abstract void create(T entity);
    public abstract T find(Integer id);
    public abstract List<T> findAll();
    public abstract T update(T entity);
    public abstract void delete(Integer id);
}
