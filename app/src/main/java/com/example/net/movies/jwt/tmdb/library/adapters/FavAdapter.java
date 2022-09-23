package com.example.net.movies.jwt.tmdb.library.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.databinding.MovieLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.db.FavMovie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private List<FavMovie> list;

    public FavAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<FavMovie> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MovieLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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

        private MovieLayoutBinding binding;

        public ViewHolder(@NonNull MovieLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void fillUI(FavMovie movie) {
            Picasso.get()
                    .load(Constants.IMAGE_BASE_URL + movie.getPosterPath())
                    .into(binding.poster);
            binding.rating.setRating((int) movie.getVoteAverage() / 2);
        }
    }
}