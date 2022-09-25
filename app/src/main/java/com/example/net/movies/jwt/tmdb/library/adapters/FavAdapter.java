package com.example.net.movies.jwt.tmdb.library.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.databinding.FavMovieLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.db.Database;
import com.example.net.movies.jwt.tmdb.library.db.FavMovie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

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
        return new ViewHolder(FavMovieLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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

        private FavMovieLayoutBinding binding;

        public ViewHolder(@NonNull FavMovieLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void fillUI(FavMovie movie) {
            Picasso.get()
                    .load(Constants.IMAGE_BASE_URL + movie.getPosterPath())
                    .into(binding.poster);
            binding.movie.setOnClickListener(v -> Toast.makeText(binding.getRoot().getContext(), "" + movie.getId(), Toast.LENGTH_SHORT).show());
            binding.bookmark.setOnClickListener(v -> {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(binding.getRoot().getContext());
//                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                builder.setCancelable(true);
                builder.setMessage("The Movie will be deleted? Do you Want to Continue ??").setTitle("Confirmation Dialog");
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.setPositiveButton("Ok", (dialog, which) -> {
                    Database.getInstance(binding.getRoot().getContext())
                            .favDao()
                            .delete(movie)
                            .subscribeWith(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Toast.makeText(binding.getRoot().getContext(), "The Movie Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                    Toast.makeText(binding.getRoot().getContext(), "Something Went Wrong, Please Try Later.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                });
                builder.show();

            });
//            binding.rating.setRating((int) movie.getVoteAverage() / 2);
        }
    }
}