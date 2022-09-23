package com.example.net.movies.jwt.tmdb.library.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.net.movies.jwt.tmdb.library.model.details.GenresItem;

import java.util.List;

@Entity(tableName = "movie")
public class FavMovie {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "genres")
    private List<GenresItem> genres;


    public FavMovie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<GenresItem> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresItem> genres) {
        this.genres = genres;
    }
}
