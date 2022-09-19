package com.example.net.movies.jwt.tmdb.library.client;

import com.example.net.movies.jwt.tmdb.library.model.coming.ComingList;
import com.example.net.movies.jwt.tmdb.library.model.popular.PopularList;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Api {

    @GET("movie/popular")
    Observable<PopularList> getPopular(
            @Query("api_key") String api_key
    );

    @GET("movie/upcoming/")
    Observable<ComingList> getUpComing(
            @Query("api_key") String api_key
    );
}
