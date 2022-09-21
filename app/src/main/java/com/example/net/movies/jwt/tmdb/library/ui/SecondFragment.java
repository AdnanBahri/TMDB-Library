package com.example.net.movies.jwt.tmdb.library.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.net.movies.jwt.tmdb.library.model.credits.CreditsList;
import com.example.net.movies.jwt.tmdb.library.model.details.MovieDetails;
import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;
import com.example.net.movies.jwt.tmdb.library.model.movie.MoviesResponse;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.example.net.movies.jwt.tmdb.library.utils.GridDecoration;
import com.example.net.movies.jwt.tmdb.library.utils.ItemDecorator;
import com.example.net.movies.jwt.tmdb.library.viewmodel.HomeViewModel;
import com.squareup.picasso.Picasso;

public class SecondFragment extends Fragment implements HandleMovieClick {

    // Todo: Add Room Database To Store Favorites Movies
    // Todo: Create Watch_Now and Watch_list functionalities

    private FragmentSecondBinding binding;
    private HomeViewModel viewModel;
    private CastAdapter castAdapter;
    private MovieAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Second Fragment", getArguments().getInt("movie_id") + "");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        int movie_id = getArguments().getInt("movie_id");
        viewModel.getMovieDetails(movie_id, Constants.API_KEY)
                .observe(getViewLifecycleOwner(), this::populateUI);
        viewModel.getMovieCredits(movie_id, Constants.API_KEY)
                .observe(getViewLifecycleOwner(), this::castRecyclerUI);
        viewModel.getMovieSimilar(movie_id, Constants.API_KEY)
                .observe(getViewLifecycleOwner(), this::similarRecyclerUI);
    }

    private void similarRecyclerUI(MoviesResponse response) {
        adapter = new MovieAdapter();
        adapter.setList(response.getResults());
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