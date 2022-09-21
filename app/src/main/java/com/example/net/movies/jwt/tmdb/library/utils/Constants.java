package com.example.net.movies.jwt.tmdb.library.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String API_KEY = "eea817b734ed288ab6730d4787451043";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780";

    private static final Map<Integer, String> genres_ids = new HashMap<Integer, String>() {
        {
            put(28, "Action");
            put(12, "Adventure");
            put(16, "Animation");
            put(35, "Comedy");
            put(80, "Crime");
            put(99, "Documentary");
            put(18, "Drama");
            put(10751, "Family");
            put(14, "Fantasy");
            put(36, "History");
            put(27, "Horror");
            put(10402, "Music");
            put(9648, "Mystery");
            put(10749, "Romance");
            put(878, "Science Fiction");
            put(10770, "TV Movie");
            put(53, "Thriller");
            put(10752, "War");
            put(37, "Western");
        }

        ;
    };

    public static final Map<Integer, String> categories = Collections.unmodifiableMap(genres_ids);

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static String formatTime(int s) {
        return String.valueOf(s / 60).concat("h ").concat(String.valueOf(s % 60));
    }
}
