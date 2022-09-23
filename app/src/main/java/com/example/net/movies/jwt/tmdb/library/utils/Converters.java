package com.example.net.movies.jwt.tmdb.library.utils;

import androidx.room.TypeConverter;

import com.example.net.movies.jwt.tmdb.library.model.details.GenresItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<GenresItem> fromString(String value) {
        if (value == null)
            return null;
        Type type = new TypeToken<List<GenresItem>>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String toString(List<GenresItem> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
