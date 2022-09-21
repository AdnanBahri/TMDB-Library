package com.example.net.movies.jwt.tmdb.library.callbacks;

import android.view.View;

import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;

public interface HandleMovieClick {
    void onMovieClick(Movie movie, View view);
}
