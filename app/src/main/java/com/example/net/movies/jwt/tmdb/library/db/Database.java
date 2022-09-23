package com.example.net.movies.jwt.tmdb.library.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.net.movies.jwt.tmdb.library.model.details.GenresItem;
import com.example.net.movies.jwt.tmdb.library.utils.Converters;

@androidx.room.Database(entities = {FavMovie.class, GenresItem.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "fav_db";
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract dao favDao();
}
