package com.example.net.movies.jwt.tmdb.library.callbacks;

import android.view.View;

import com.example.net.movies.jwt.tmdb.library.model.coming.ComingMovie;

public interface HandleComingMovieClick {
    void onComingMovieClick(ComingMovie movie, View view);
}
