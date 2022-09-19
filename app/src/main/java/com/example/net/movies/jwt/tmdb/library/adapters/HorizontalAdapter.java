package com.example.net.movies.jwt.tmdb.library.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.databinding.MovieHorizontaleLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.model.coming.ComingMovie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    private List<ComingMovie> movies;

    public HorizontalAdapter() {
        movies = new ArrayList<>();
    }

    public void setMovies(List<ComingMovie> movies) {
        this.movies = movies;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(MovieHorizontaleLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalAdapter.ViewHolder holder, int position) {
        holder.fillUI(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MovieHorizontaleLayoutBinding binding;

        public ViewHolder(@NonNull MovieHorizontaleLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void fillUI(ComingMovie movie) {
            binding.title.setText(movie.getTitle());
            String url = Constants.IMAGE_BASE_URL + (movie.getPosterPath() == null ? movie.getPosterPath() : movie.getBackdropPath());
            Picasso.get()
                    .load(url)
                    .into(binding.poster);
            for (int i = 0; i < movie.getGenreIds().size(); i++) {
                if (i != movie.getGenreIds().size() - 1)
                    binding.categories.append(Constants.categories.get(movie.getGenreIds().get(i)) + ", ");
                else
                    binding.categories.append(Constants.categories.get(movie.getGenreIds().get(i)) + "");
            }
            binding.release.setText(movie.getReleaseDate());
            binding.movie.setOnClickListener(v -> Log.d("fillUI", "" + movie.getTitle() + " , " + url));
        }
    }
}
