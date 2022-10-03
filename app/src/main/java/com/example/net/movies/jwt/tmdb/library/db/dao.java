package com.example.net.movies.jwt.tmdb.library.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface dao {

    @Query("SELECT * FROM movie")
    Observable<List<FavMovie>> getAllFavMovies();

    @Query("SELECT * FROM movie WHERE id=:movie_id")
    Single<FavMovie> getFavMovies(int movie_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable save(FavMovie movie);

    @Delete
    Completable delete(FavMovie movie);

    @Query("DELETE FROM movie WHERE id=:movie_id")
    Completable deleteMovieById(int movie_id);

    @Query("DELETE FROM movie")
    Completable deleteAll();
}
