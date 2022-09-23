package com.example.net.movies.jwt.tmdb.library.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.net.movies.jwt.tmdb.library.adapters.CastAdapter;
import com.example.net.movies.jwt.tmdb.library.adapters.MovieAdapter;
import com.example.net.movies.jwt.tmdb.library.callbacks.HandleMovieClick;
import com.example.net.movies.jwt.tmdb.library.databinding.FragmentSecondBinding;
import com.example.net.movies.jwt.tmdb.library.db.Database;
import com.example.net.movies.jwt.tmdb.library.db.FavMovie;
import com.example.net.movies.jwt.tmdb.library.model.credits.CreditsList;
import com.example.net.movies.jwt.tmdb.library.model.details.MovieDetails;
import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.example.net.movies.jwt.tmdb.library.utils.GridDecoration;
import com.example.net.movies.jwt.tmdb.library.utils.ItemDecorator;
import com.example.net.movies.jwt.tmdb.library.viewmodel.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SecondFragment extends Fragment implements HandleMovieClick {

    // Todo: Add Room Database To Store Favorites Movies
    // Todo: Create Watch_Now and Watch_list functionalities

    private FragmentSecondBinding binding;
    private HomeViewModel viewModel;
    private CastAdapter castAdapter;
    private MovieAdapter adapter;
    private MovieDetails details;
    private CreditsList credits;
    private List<Movie> similar;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        details = new MovieDetails();
        credits = new CreditsList();
        similar = new ArrayList<>();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.d("Second Fragment", getArguments().getInt("movie_id") + "");
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        int movie_id = getArguments().getInt("movie_id");
        viewModel.getMovieDetails(movie_id, Constants.API_KEY)
                .observe(getViewLifecycleOwner(), details1 -> this.details = details1);
        viewModel.getMovieCredits(movie_id, Constants.API_KEY)
                .observe(getViewLifecycleOwner(), creditsList -> this.credits = creditsList);
        viewModel.getMovieSimilar(movie_id, Constants.API_KEY)
                .observe(getViewLifecycleOwner(), response -> similar = response.getResults());

        viewModel.isMovieDetailsLoading().observe(getViewLifecycleOwner(), bool -> {
            if (bool) Toast.makeText(getContext(), "Loading Details", Toast.LENGTH_SHORT).show();
            else populateUI(details);
        });
        viewModel.isMovieCreditsLoading().observe(getViewLifecycleOwner(), bool -> {
            if (bool) Toast.makeText(getContext(), "Loading Credits", Toast.LENGTH_SHORT).show();
            else castRecyclerUI(credits);
        });
        viewModel.isSimilarMoviesLoading().observe(getViewLifecycleOwner(), bool -> {
            if (bool) Toast.makeText(getContext(), "Loading Similar", Toast.LENGTH_SHORT).show();
            else similarRecyclerUI(similar);
        });

        binding.watchNow.setOnClickListener(v -> {
            Database.getInstance(getContext())
                    .favDao()
                    .getAllFavMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(favMovies -> {
                        if (favMovies != null) {
                            Log.d("Room", "----------------------On Result---------------------");
                            Log.d("Room", "Saved Movie List Size : " + favMovies.size());
                            Log.d("Room", "----------------------On Result---------------------");
                        } else {
                            Log.d("Room", "----------------------On Result---------------------");
                            Log.d("Room", "Database is Empty");
                            Log.d("Room", "----------------------On Result---------------------");
                        }
                    });
        });

        binding.watchLater.setOnClickListener(v -> {
            FavMovie movie = new FavMovie(movie_id);
            if (details != null) {
                movie.setPosterPath(details.getPosterPath());
                movie.setVoteAverage(details.getVoteAverage());
                movie.setGenres(details.getGenres());


                Database.getInstance(getContext())
                        .favDao()
                        .save(movie)
                        .subscribeWith(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                Log.d("Room", "----------------------On Subscribe---------------------");
                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(getContext(), "The Movie Saved Successfully", Toast.LENGTH_SHORT).show();
                                Log.d("Room", "----------------------On Complete---------------------");
                                Log.d("Room", "Movie Saved Successfully to Database");
                                Log.d("Room", "----------------------On Complete---------------------");
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                Toast.makeText(getContext(), "The Movie Saved Successfully", Toast.LENGTH_SHORT).show();
                                Log.d("Room", "----------------------On Error ---------------------");
                                Log.d("Room", "" + e.getMessage());
                                Log.d("Room", "----------------------On Error ---------------------");
                            }
                        });
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void similarRecyclerUI(List<Movie> movies) {
        adapter = new MovieAdapter();
        adapter.setList(movies);
        binding.recyclerSimilar.setHasFixedSize(true);
        binding.recyclerSimilar.setAdapter(adapter);
        binding.recyclerSimilar.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerSimilar.addItemDecoration(new GridDecoration(2, false, Constants.dpToPx(getContext(), 10)));
    }

    private void castRecyclerUI(CreditsList creditsList) {
        castAdapter = new CastAdapter();
        castAdapter.setCastItems(creditsList.getCast());
        binding.recyclerCast.setHasFixedSize(true);
        binding.recyclerCast.setAdapter(castAdapter);
        binding.recyclerCast.addItemDecoration(new ItemDecorator(10));
        binding.recyclerCast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void populateUI(MovieDetails details) {
        Picasso.get()
                .load(Constants.IMAGE_BASE_URL + (details.getPosterPath() != null ? details.getPosterPath() : details.getBackdropPath()))
                .into(binding.poster);
        Picasso.get()
                .load(Constants.IMAGE_BASE_URL + (details.getPosterPath() != null ? details.getPosterPath() : details.getBackdropPath()))
                .into(binding.backdrop);
        binding.title.setText(details.getTitle());
        binding.rating.setRating((float) details.getVoteAverage() / 2);
        for (int i = 0; i < details.getGenres().size(); i++) {
            if (i != details.getGenres().size() - 1)
                binding.categories.append(details.getGenres().get(i).getName() + ", ");
            else
                binding.categories.append(details.getGenres().get(i).getName());
        }
        binding.duration.setText(Constants.formatTime(details.getRuntime()));
        binding.overview.setText(details.getOverview());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMovieClick(Movie movie, View view) {

    }
}