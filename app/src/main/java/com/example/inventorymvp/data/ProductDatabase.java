package com.example.inventorymvp.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Product.class}, version = 2, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase database;
    private static final String DATABASE_NAME = "inventory_db";

    public abstract ProductDao productDao();

    public static synchronized ProductDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext()
                    , ProductDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
