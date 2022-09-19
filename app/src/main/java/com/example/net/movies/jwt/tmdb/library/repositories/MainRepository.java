package com.example.net.movies.jwt.tmdb.library.repositories;

import com.example.net.movies.jwt.tmdb.library.client.Api;
import com.example.net.movies.jwt.tmdb.library.client.RetrofitClient;
import com.example.net.movies.jwt.tmdb.library.model.coming.ComingList;
import com.example.net.movies.jwt.tmdb.library.model.popular.PopularList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainRepository {

    private Api api;

    public MainRepository() {
        api = RetrofitClient.getInstance().create(Api.class);
    }

    public Observable<PopularList> getPopularMovies(String key) {
        return api.getPopular(key);
    }

    public Observable<ComingList> getComingMovies(String key) {
        return api.getUpComing(key);
    }
}
