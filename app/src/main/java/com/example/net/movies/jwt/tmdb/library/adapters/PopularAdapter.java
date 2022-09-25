package com.example.net.movies.jwt.tmdb.library.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.callbacks.HandleMovieClick;
import com.example.net.movies.jwt.tmdb.library.databinding.PopularMovieLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.db.Database;
import com.example.net.movies.jwt.tmdb.library.db.FavMovie;
import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private List<Movie> movies;
    private HandleMovieClick listener;

    public PopularAdapter(HandleMovieClick listener) {
        movies = new ArrayList<>();
        this.listener = listener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(PopularMovieLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fillUI(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private PopularMovieLayoutBinding binding;

        public ViewHolder(@NonNull PopularMovieLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void fillUI(Movie movie) {
            binding.title.setText(movie.getTitle());
            String url = Constants.IMAGE_BASE_URL + (movie.getPosterPath() == null ? movie.getPosterPath() : movie.getBackdropPath());
            float vote = (float) (movie.getVoteAverage() / 2);
            Picasso.get()
                    .load(url)
                    .into(binding.poster);
            binding.rating.setRating(vote);
            Database.getInstance(binding.getRoot().getContext())
                    .favDao()
                    .getFavMovies(movie.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<FavMovie>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull FavMovie favMovie) {
                            binding.bookmark.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            binding.bookmark.setVisibility(View.GONE);
                        }
                    });
            binding.poster.setOnClickListener(v -> {
                Log.d("fillUI", "" + movie.getTitle() + " , " + movie.getId() + " , " + movie.getVoteAverage() + " , " + vote);
                listener.onMovieClick(movie, binding.poster);
            });
        }
    }
}
