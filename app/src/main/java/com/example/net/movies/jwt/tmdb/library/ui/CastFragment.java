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

import com.example.net.movies.jwt.tmdb.library.adapters.MovieAdapter;
import com.example.net.movies.jwt.tmdb.library.databinding.FragmentCastBinding;
import com.example.net.movies.jwt.tmdb.library.model.actor.ActorResponse;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.example.net.movies.jwt.tmdb.library.utils.GridDecoration;
import com.example.net.movies.jwt.tmdb.library.viewmodel.HomeViewModel;
import com.squareup.picasso.Picasso;


public class CastFragment extends Fragment {

    private FragmentCastBinding binding;
    private HomeViewModel viewModel;
    private int actor_id;
    private MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCastBinding.inflate(inflater, container, false);
        actor_id = getArguments().getInt("actor_id");
        adapter = new MovieAdapter();
        binding.recyclerMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerMovies.addItemDecoration(new GridDecoration(2, false, 20));
        binding.recyclerMovies.setHasFixedSize(true);
        binding.recyclerMovies.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getMoviesByActorId(Constants.API_KEY, actor_id)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response != null) {
                        adapter.setList(response.getResults());
                        binding.totalFilms.setText(String.valueOf(response.getTotalResults()));
                    }
                });
        viewModel.getActorDetails(Constants.API_KEY, actor_id)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response != null)
                        populateActorDetails(response);
                });
    }

    private void populateActorDetails(ActorResponse response) {
        Log.d("CastFragment", response.getProfilePath());
        Picasso.get()
                .load(Constants.IMAGE_BASE_URL + response.getProfilePath())
                .into(binding.avatar);
        binding.name.setText(response.getName());
        binding.birthDay.setText(response.getBirthday());
        binding.birthPlace.setText(response.getPlaceOfBirth());
        binding.biography.setText(response.getBiography());
    }

}