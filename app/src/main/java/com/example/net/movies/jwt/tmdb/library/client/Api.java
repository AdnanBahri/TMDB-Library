package com.example.net.movies.jwt.tmdb.library.client;

import com.example.net.movies.jwt.tmdb.library.model.coming.ComingList;
import com.example.net.movies.jwt.tmdb.library.model.credits.CreditsList;
import com.example.net.movies.jwt.tmdb.library.model.details.MovieDetails;
import com.example.net.movies.jwt.tmdb.library.model.movie.MoviesResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("movie/popular")
    Observable<MoviesResponse> getPopular(
            @Query("api_key") String api_key
    );

    @GET("movie/upcoming/")
    Observable<ComingList> getUpComing(
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}")
    Single<MovieDetails> getMovieDetails(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}/credits")
    Observable<CreditsList> getMovieCredits(
            @Path("movie_id") int id,
            @Query("api_key") String api_key
    );

//    @GET("movie/{movie_id}/images")
//    Observable<MovieDetails> getMovieImages(
//            @Path("movie_id") int id,
//            @Query("api_key") String api_key
//    );

    @GET("movie/{movie_id}/videos")
    Observable<MovieDetails> getMovieVideos(
            @Path("movie_id") int id,
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}/similar")
    Observable<MoviesResponse> getMovieSimilar(
            @Path("movie_id") int id,
            @Query("api_key") String api_key
    );

    @GET("search/movie")
    Observable<MoviesResponse> search(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("include_adult") boolean include_adult,
            @Query("page") int page
    );
}
