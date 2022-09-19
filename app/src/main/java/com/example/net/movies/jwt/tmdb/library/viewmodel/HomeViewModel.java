package com.example.net.movies.jwt.tmdb.library.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.net.movies.jwt.tmdb.library.model.coming.ComingList;
import com.example.net.movies.jwt.tmdb.library.model.popular.PopularList;
import com.example.net.movies.jwt.tmdb.library.repositories.MainRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private MainRepository repo;
    private MutableLiveData<PopularList> popularLiveData;
    private MutableLiveData<ComingList> comingLiveData;
    private MutableLiveData<Boolean> isPopularLoading;
    private MutableLiveData<Boolean> isComingLoading;

    public HomeViewModel() {
        repo = new MainRepository();
        popularLiveData = new MutableLiveData<>();
        comingLiveData = new MutableLiveData<>();
        isPopularLoading = new MutableLiveData<>();
        isComingLoading = new MutableLiveData<>();
    }

    public LiveData<PopularList> getPopularMovies(String key) {
        isPopularLoading.setValue(true);
        repo.getPopularMovies(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PopularList>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "Subscribe On Popular Movies");
                    }

                    @Override
                    public void onNext(PopularList popularList) {
                        Log.d(TAG, "On Next Popular Movies");
                        popularLiveData.setValue(popularList);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "On Error On Popular Movies");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e(TAG, t.getMessage());
                        t.printStackTrace();
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        popularLiveData.setValue(null);
                        isPopularLoading.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "On Complete Popular Movies");
                        isPopularLoading.setValue(false);
                    }
                });
        return popularLiveData;
    }

    public LiveData<Boolean> isPopularLoading() {
        return isPopularLoading;
    }

    public LiveData<ComingList> getComingMovies(String key) {
        repo.getComingMovies(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ComingList>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "Subscribe On Coming Movies");
                        isComingLoading.setValue(true);
                    }

                    @Override
                    public void onNext(ComingList comingList) {
                        Log.d(TAG, "On Next Coming Movies");
                        comingLiveData.setValue(comingList);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "On Error On UpComing Movies");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e(TAG, t.getMessage());
                        t.printStackTrace();
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        comingLiveData.setValue(null);
                        isComingLoading.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "On Complete Coming Movies");
                        isComingLoading.setValue(false);
                    }
                });
        return comingLiveData;
    }

    public LiveData<Boolean> isComingLoading() {
        return isComingLoading;
    }
}
