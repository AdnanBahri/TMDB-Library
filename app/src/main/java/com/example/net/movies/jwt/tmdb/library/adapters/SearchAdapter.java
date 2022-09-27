package com.example.net.movies.jwt.tmdb.library.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.callbacks.HandleMovieClick;
import com.example.net.movies.jwt.tmdb.library.databinding.MovieHorizontaleLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Movie> list;
    private HandleMovieClick listener;

    public SearchAdapter(HandleMovieClick listener) {
        list = new ArrayList<>();
        this.listener = listener;
    }

    public void setList(List<Movie> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MovieHorizontaleLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fillUI(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MovieHorizontaleLayoutBinding binding;

        public ViewHolder(@NonNull MovieHorizontaleLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void fillUI(Movie movie) {
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
            binding.movie.setOnClickListener(v -> {
                Log.d("fillUI", "" + movie.getTitle() + " , " + movie.getId() + " , " + url);
                listener.onMovieClick(movie, binding.getRoot());
            });
        }
    }
}