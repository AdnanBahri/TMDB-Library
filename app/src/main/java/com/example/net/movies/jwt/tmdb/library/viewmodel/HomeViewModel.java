package com.example.net.movies.jwt.tmdb.library.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.net.movies.jwt.tmdb.library.db.Database;
import com.example.net.movies.jwt.tmdb.library.db.FavMovie;
import com.example.net.movies.jwt.tmdb.library.model.coming.ComingList;
import com.example.net.movies.jwt.tmdb.library.model.credits.CreditsList;
import com.example.net.movies.jwt.tmdb.library.model.details.MovieDetails;
import com.example.net.movies.jwt.tmdb.library.model.movie.MoviesResponse;
import com.example.net.movies.jwt.tmdb.library.repositories.MainRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private MainRepository repo;
    private MutableLiveData<MoviesResponse> popularLiveData;
    private MutableLiveData<MovieDetails> detailsLiveData;
    private MutableLiveData<ComingList> comingLiveData;
    private MutableLiveData<CreditsList> creditsLiveData;
    private MutableLiveData<MoviesResponse> similarLiveData;
    private MutableLiveData<Boolean> isPopularLoading;
    private MutableLiveData<Boolean> isComingLoading;
    private MutableLiveData<Boolean> isDetailsLoading;
    private MutableLiveData<Boolean> isMovieCreditsLoading;
    private MutableLiveData<Boolean> isSimilarMoviesLoading;
    private MutableLiveData<Boolean> isMovieSaved;

    public HomeViewModel() {
        repo = new MainRepository();
        popularLiveData = new MutableLiveData<>();
        detailsLiveData = new MutableLiveData<>();
        comingLiveData = new MutableLiveData<>();
        creditsLiveData = new MutableLiveData<>();
        similarLiveData = new MutableLiveData<>();
        isPopularLoading = new MutableLiveData<>();
        isComingLoading = new MutableLiveData<>();
        isDetailsLoading = new MutableLiveData<>();
        isMovieCreditsLoading = new MutableLiveData<>();
        isSimilarMoviesLoading = new MutableLiveData<>();
        isMovieSaved = new MutableLiveData<>(false);
    }

    public LiveData<MoviesResponse> getPopularMovies(String key) {
        isPopularLoading.setValue(true);
        repo.getPopularMovies(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoviesResponse>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "Subscribe On Popular Movies");
                    }

                    @Override
                    public void onNext(MoviesResponse moviesResponse) {
                        Log.d(TAG, "On Next Popular Movies");
                        popularLiveData.setValue(moviesResponse);
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

    public LiveData<MovieDetails> getMovieDetails(int movie_id, String key) {
        repo.getMovieDetails(movie_id, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieDetails>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "Subscribe On Movie Details");
                        isDetailsLoading.setValue(true);
                    }

                    @Override
                    public void onSuccess(@NonNull MovieDetails details) {
                        Log.d(TAG, "On Success Movie Details");
                        if (details != null) {
                            detailsLiveData.setValue(details);
                        }
                        isDetailsLoading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "On Error On Movie Details");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        detailsLiveData.setValue(null);
                        isDetailsLoading.setValue(false);
                    }
                });
        return detailsLiveData;
    }

    public LiveData<CreditsList> getMovieCredits(int movie_id, String key) {
        repo.getMovieCredits(movie_id, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CreditsList>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "Subscribe On Movie Credits");
                        isMovieCreditsLoading.setValue(true);
                    }

                    @Override
                    public void onNext(CreditsList creditsList) {
                        Log.d(TAG, "On Next Movie Credits");
                        creditsLiveData.setValue(creditsList);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "On Error Movie Credits");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e(TAG, t.getMessage());
                        t.printStackTrace();
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        creditsLiveData.setValue(null);
                        isMovieCreditsLoading.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "On Complete Movie Credits");
                        isMovieCreditsLoading.setValue(false);
                    }
                });
        return creditsLiveData;
    }

    public LiveData<Boolean> isMovieCreditsLoading() {
        return isMovieCreditsLoading;
    }

    public LiveData<MoviesResponse> getMovieSimilar(int movie_id, String key) {
        repo.getMovieSimilar(movie_id, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoviesResponse>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "Subscribe On Similar Movies");
                        isSimilarMoviesLoading.setValue(true);
                    }

                    @Override
                    public void onNext(MoviesResponse response) {
                        Log.d(TAG, "On Next Similar Movies");
                        similarLiveData.setValue(response);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "On Error On Similar Movies");
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        Log.e(TAG, t.getMessage());
                        t.printStackTrace();
                        Log.d("----", "---------------------------------------------------------------------------------------------------");
                        similarLiveData.setValue(null);
                        isSimilarMoviesLoading.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "On Complete Similar Movies");
                        isSimilarMoviesLoading.setValue(false);
                    }
                });
        return similarLiveData;
    }

    public LiveData<Boolean> isSimilarMoviesLoading() {
        return isSimilarMoviesLoading;
    }

    public LiveData<Boolean> isMovieDetailsLoading() {
        return isDetailsLoading;
    }

    public LiveData<Boolean> isMovieSaved(Database instance, int movie_id) {
        instance.favDao()
                .getFavMovies(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FavMovie>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull FavMovie favMovie) {
                        isMovieSaved.setValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        isMovieSaved.setValue(false);
                    }
                });
        return isMovieSaved;
    }

    public void saveMovie(Database instance, FavMovie movie) {
        instance.favDao()
                .save(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d("Room", "----------------------On Subscribe---------------------");
                    }

                    @Override
                    public void onComplete() {
//                        Toast.makeText(getContext(), "The Movie Saved Successfully", Toast.LENGTH_SHORT).show();
//                        Log.d("Room", "----------------------On Complete---------------------");
//                        Log.d("Room", "Movie Saved Successfully to Database");
//                        Log.d("Room", "----------------------On Complete---------------------");
                        isMovieSaved.setValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Toast.makeText(getContext(), "The Movie Saved Successfully", Toast.LENGTH_SHORT).show();
                        Log.d("HomeViewModel", "----------------------On Error ---------------------");
                        Log.d("HomeViewModel", "The Movie has not been saved to Database");
                        Log.d("HomeViewModel", "" + e.getMessage());
                        Log.d("HomeViewModel", "----------------------On Error ---------------------");
//                        isMovieSaved.setValue(false);
                    }
                });
    }

    public void deleteMovie(Database instance, FavMovie movie) {
        instance.favDao()
                .delete(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        isMovieSaved.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("HomeViewModel", "----------------------On Error---------------------");
                        Log.d("HomeViewModel", "Movie Not Deleted");
                        Log.d("HomeViewModel", "----------------------On Error---------------------");
                    }
                });
    }
}
