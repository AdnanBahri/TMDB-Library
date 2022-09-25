package com.example.net.movies.jwt.tmdb.library.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.net.movies.jwt.tmdb.library.databinding.FragmentSearchBinding;
import com.example.net.movies.jwt.tmdb.library.db.Database;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;


public class SearchFragment extends Fragment {

    /*
     Todo: Code The Search View Functionalities with regards to this remarks:
     Todo:      * start fetching data after the user enter 3 characters at least && stop typing
     Todo:      * start when the user start typing again tops api call
     Todo:      * Try to implement an autocomplete or save the 5 last queries
    */

    private FragmentSearchBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater(), container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnDelete.setOnClickListener(v -> {
            Database.getInstance(getContext())
                    .favDao()
                    .deleteAll()
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            Log.d("Room", "----------------------On Subscribe---------------------");
                        }

                        @Override
                        public void onComplete() {
                            Log.d("Room", "----------------------On Complete---------------------");
                            Log.d("Room", "Database Deleted Successfully");
                            Log.d("Room", "----------------------On Complete---------------------");
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.d("Room", "----------------------On Error---------------------");
                            Log.d("Room", "" + e.getMessage());
                            Log.d("Room", "----------------------On Error---------------------");
                        }
                    });
        });
    }
}