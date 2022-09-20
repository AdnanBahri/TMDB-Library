package com.example.net.movies.jwt.tmdb.library.utils;

import android.view.View;

import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;

public interface Callbacks {

    interface HandleSharedElement {
        void onMovieClick(Movie movie, View view);
    }
}
