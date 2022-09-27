package com.example.net.movies.jwt.tmdb.library.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.net.movies.jwt.tmdb.library.R;
import com.example.net.movies.jwt.tmdb.library.adapters.SearchAdapter;
import com.example.net.movies.jwt.tmdb.library.callbacks.HandleMovieClick;
import com.example.net.movies.jwt.tmdb.library.databinding.FragmentSearchBinding;
import com.example.net.movies.jwt.tmdb.library.model.movie.Movie;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.example.net.movies.jwt.tmdb.library.utils.RxSearchObservable;
import com.example.net.movies.jwt.tmdb.library.viewmodel.HomeViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements HandleMovieClick {

    /*
     Todo: Code The Search View Functionalities with regards to this remarks:
     Todo:      * Try to implement an autocomplete or save the 5 last queries
    */

    private FragmentSearchBinding binding;
    private HomeViewModel viewModel;
    private SearchAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater(), container, false);
        binding.search.setIconifiedByDefault(true);
        binding.search.setFocusable(true);
        binding.search.setIconified(false);
        binding.search.requestFocusFromTouch();
        binding.search.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                showInputMethod(v.findFocus());
        });
        binding.search.setOnClickListener(v -> binding.search.setIconified(false));
        ((ImageView) binding.search.findViewById(androidx.appcompat.R.id.search_close_btn)).setColorFilter(R.color.textColor);
        ((ImageView) binding.search.findViewById(androidx.appcompat.R.id.search_button)).setColorFilter(R.color.textColor);
        adapter = new SearchAdapter(this::onMovieClick);
        binding.recyclerSearch.setAdapter(adapter);
        binding.recyclerSearch.setHasFixedSize(true);
        binding.recyclerSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.isSearchingMovies().observe(getViewLifecycleOwner(), searching -> {
            if (searching) {
                showProgress();
            } else {
                hideProgress();
            }
        });
        RxSearchObservable.fromView(binding.search)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty() && text.length() >= 3 && text.trim().length() != 0)
                .map(String::toLowerCase)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> viewModel.search(
                        Constants.API_KEY,
                        query,
                        false,
                        1
                ));
        viewModel.getSearchedMovies()
                .observe(getViewLifecycleOwner(), movies -> {
                    if (movies != null) adapter.setList(movies);
                });
    }

    private void hideProgress() {
        binding.progress.setVisibility(View.GONE);
        binding.recyclerSearch.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        binding.recyclerSearch.setVisibility(View.GONE);
        binding.progress.setVisibility(View.VISIBLE);
//        adapter.setList(new ArrayList<>());
    }

    @Override
    public void onMovieClick(Movie movie, View view) {
    }
}