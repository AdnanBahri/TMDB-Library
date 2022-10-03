package com.example.net.movies.jwt.tmdb.library.adapters;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.callbacks.HandleComingMovieClick;
import com.example.net.movies.jwt.tmdb.library.databinding.MovieHorizontaleLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.db.Database;
import com.example.net.movies.jwt.tmdb.library.db.FavMovie;
import com.example.net.movies.jwt.tmdb.library.model.coming.ComingMovie;
import com.example.net.movies.jwt.tmdb.library.utils.ConfirmationDialog;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    private List<ComingMovie> movies;
    private HandleComingMovieClick listener;

    public HorizontalAdapter(HandleComingMovieClick listener) {
        movies = new ArrayList<>();
        this.listener = listener;
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
            String url = Constants.IMAGE_BASE_URL + (movie.getPosterPath() == null ? movie.getBackdropPath() : movie.getPosterPath());
            Picasso.get()
                    .load(url)
                    .into(binding.poster);
            for (int i = 0; i < movie.getGenreIds().size(); i++) {
                if (i != movie.getGenreIds().size() - 1)
                    binding.categories.append(Constants.categories.get(movie.getGenreIds().get(i)) + ", ");
                else
                    binding.categories.append(Constants.categories.get(movie.getGenreIds().get(i)) + "");
            }
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
            binding.release.setText(movie.getReleaseDate());
            binding.bookmark.setOnClickListener(v -> {
                ConfirmationDialog builder = new ConfirmationDialog(binding.getRoot().getContext());
                builder.setup(movie.getId(),"Are you sure you want to delete this movie?","Cancel","Continue");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Database.getInstance(binding.getRoot().getContext())
                                .favDao()
                                .deleteMovieById(movie.getId())
                                .subscribeWith(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(binding.getRoot().getContext(), "The Movie Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        Toast.makeText(binding.getRoot().getContext(), "Something Went Wrong, Please Try Later.", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
                builder.show();

            });
            binding.movie.setOnClickListener(v -> {
                Log.d("fillUI", "" + movie.getTitle() + " , " + movie.getId() + " , " + url);
                listener.onComingMovieClick(movie, binding.getRoot());
            });
        }
    }
}
