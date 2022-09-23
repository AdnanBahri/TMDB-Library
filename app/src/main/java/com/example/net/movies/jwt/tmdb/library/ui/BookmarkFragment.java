package com.example.net.movies.jwt.tmdb.library.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.net.movies.jwt.tmdb.library.adapters.FavAdapter;
import com.example.net.movies.jwt.tmdb.library.databinding.FragmentBookmarkBinding;
import com.example.net.movies.jwt.tmdb.library.db.Database;
import com.example.net.movies.jwt.tmdb.library.db.FavMovie;
import com.example.net.movies.jwt.tmdb.library.utils.GridDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class BookmarkFragment extends Fragment {

    private FragmentBookmarkBinding binding;
    private FavAdapter adapter;
    private List<FavMovie> movies;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(getLayoutInflater(), container, false);
        adapter = new FavAdapter();
        movies = new ArrayList<>();
        binding.recyclerBookmark.setHasFixedSize(true);
        binding.recyclerBookmark.setAdapter(adapter);
        binding.recyclerBookmark.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerBookmark.addItemDecoration(new GridDecoration(2, false, 10));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Database.getInstance(getContext())
                .favDao()
                .getAllFavMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favMovies -> {
                    if (favMovies != null) {
                        Log.d("Room", "----------------------On Result---------------------");
                        adapter.setList(favMovies);
                        Log.d("Room", "----------------------On Result---------------------");
                    } else {
                        Log.d("Room", "----------------------On Result---------------------");
                        Log.d("Room", "Database is Empty");
                        Log.d("Room", "----------------------On Result---------------------");
                    }
                });
    }
}