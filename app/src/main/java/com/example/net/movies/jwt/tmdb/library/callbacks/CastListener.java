package com.example.net.movies.jwt.tmdb.library.callbacks;

import android.view.View;

import com.example.net.movies.jwt.tmdb.library.model.credits.CastItem;

public interface CastListener {
    void onCastClick(CastItem cast, View view);
}
