package com.example.net.movies.jwt.tmdb.library.utils;

import com.example.net.movies.jwt.tmdb.library.model.popular.PopularMovie;

public interface Callbacks {

    interface HandleSharedElement {
        void onMovieClick(PopularMovie movie);
    }
}
