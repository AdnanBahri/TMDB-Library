package com.example.net.movies.jwt.tmdb.library.repositories;

import com.example.net.movies.jwt.tmdb.library.client.Api;
import com.example.net.movies.jwt.tmdb.library.client.RetrofitClient;
import com.example.net.movies.jwt.tmdb.library.model.coming.ComingList;
import com.example.net.movies.jwt.tmdb.library.model.credits.CreditsList;
import com.example.net.movies.jwt.tmdb.library.model.details.MovieDetails;
import com.example.net.movies.jwt.tmdb.library.model.movie.MoviesResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MainRepository {

    // Todo: Need To store all data in the local database for caching purposes

    private Api api;

    public MainRepository() {
        api = RetrofitClient.getInstance().create(Api.class);
    }

    public Observable<MoviesResponse> getPopularMovies(String key) {
        return api.getPopular(key);
    }

    public Observable<ComingList> getComingMovies(String key) {
        return api.getUpComing(key);
    }

    public Single<MovieDetails> getMovieDetails(int movie_id, String key) {
        return api.getMovieDetails(movie_id, key);
    }

    public Observable<CreditsList> getMovieCredits(int movie_id, String key) {
        return api.getMovieCredits(movie_id, key);
    }

    public Observable<MoviesResponse> getMovieSimilar(int movie_id, String key) {
        return api.getMovieSimilar(movie_id, key);
    }
}
