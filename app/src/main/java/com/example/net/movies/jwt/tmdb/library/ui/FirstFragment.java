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
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.net.movies.jwt.tmdb.library.adapters.HorizontalAdapter;
import com.example.net.movies.jwt.tmdb.library.adapters.PopularAdapter;
import com.example.net.movies.jwt.tmdb.library.callbacks.HandleComingMovieClick;
import com.example.net.movies.jwt.tmdb.library.callbacks.HandleMovieClick;
import com.example.net.movies.jwt.tmdb.library.databinding.FragmentFirstBinding;
import com.example.net.movies.jwt.tmdb.library.model.coming.ComingMovie;
import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.example.net.movies.jwt.tmdb.library.utils.ItemDecorator;
import com.example.net.movies.jwt.tmdb.library.viewmodel.HomeViewModel;

public class FirstFragment extends Fragment implements HandleMovieClick, HandleComingMovieClick {

    private FragmentFirstBinding binding;
    private HomeViewModel viewModel;
    private PopularAdapter popularAdapter;
    private HorizontalAdapter comingAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        popularAdapter = new PopularAdapter(this);
        comingAdapter = new HorizontalAdapter(this::onComingMovieClick);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerPopular.setAdapter(popularAdapter);
        binding.recyclerUpComing.setHasFixedSize(true);
        binding.recyclerPopular.addItemDecoration(new ItemDecorator(20));
        binding.recyclerPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.recyclerUpComing.setAdapter(comingAdapter);
        binding.recyclerUpComing.setHasFixedSize(true);
        binding.recyclerUpComing.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getPopularMovies(Constants.API_KEY)
                .observe(getViewLifecycleOwner(), popularList -> {
//                        Log.d("First Fragment",""+popularList.getResults().size());
                    if (popularList != null && popularList.getResults() != null) {
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e("First Fragment", popularList.getResults().size() + "");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        popularAdapter.setMovies(popularList.getResults());
                        popularAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e("First Fragment", "Popular List is Null");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                    }
                });
        viewModel.getComingMovies(Constants.API_KEY)
                .observe(getViewLifecycleOwner(), comingList -> {
//                        Log.d("First Fragment",""+popularList.getResults().size());
                    if (comingList != null && comingList.getResults() != null) {
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e("First Fragment", comingList.getResults().size() + "");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        comingAdapter.setMovies(comingList.getResults());
                        comingAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e("First Fragment", "Popular List is Null");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                    }
                });

        viewModel.isPopularLoading()
                .observe(getViewLifecycleOwner(), bool -> {
                    if (bool)
                        Toast.makeText(getContext(), "Loading Movies in Progress", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Loading Movies is Done", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMovieClick(Movie movie, View view) {
        NavDirections directions = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
        directions.getArguments().putInt("movie_id", movie.getId());
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(directions);
    }

    @Override
    public void onComingMovieClick(ComingMovie movie, View view) {
        NavDirections directions = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
        directions.getArguments().putInt("movie_id", movie.getId());
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(directions);

    }
}