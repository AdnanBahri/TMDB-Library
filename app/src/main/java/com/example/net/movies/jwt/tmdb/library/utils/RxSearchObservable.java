package com.example.net.movies.jwt.tmdb.library.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.SearchView;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class RxSearchObservable {

    public static Observable<String> fromView(SearchView searchView) {

        final PublishSubject<String> subject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onNext(s);
//                subject.onNext(s);
//                InputMethodManager imm = (InputMethodManager) searchView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                subject.onNext(text);
                return true;
            }
        });

        return subject;
    }
}
