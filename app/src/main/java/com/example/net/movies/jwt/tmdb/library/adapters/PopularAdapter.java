package com.example.net.movies.jwt.tmdb.library.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.databinding.PopularMovieLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.model.popular.PopularMovie;
import com.example.net.movies.jwt.tmdb.library.utils.Callbacks;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private List<PopularMovie> movies;
    private Callbacks.HandleSharedElement listener;

    public PopularAdapter(Callbacks.HandleSharedElement listener) {
        movies = new ArrayList<>();
        this.listener = listener;
    }

    public void setMovies(List<PopularMovie> movies) {
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

        public void fillUI(PopularMovie movie) {
            binding.title.setText(movie.getTitle());
            String url = Constants.IMAGE_BASE_URL + (movie.getPosterPath() == null ? movie.getPosterPath() : movie.getBackdropPath());
            float vote = (float) (movie.getVoteAverage() / 2);
            Picasso.get()
                    .load(url)
                    .into(binding.poster);
            binding.rating.setRating(vote);
            binding.poster.setOnClickListener(v -> {
                Log.d("fillUI", "" + movie.getTitle() + " , " + movie.getVoteAverage() + " , " + vote);
                listener.onMovieClick(movie);
            });
        }
    }
}
