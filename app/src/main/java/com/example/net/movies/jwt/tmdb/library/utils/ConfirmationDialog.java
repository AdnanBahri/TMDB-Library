package com.example.net.movies.jwt.tmdb.library.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.net.movies.jwt.tmdb.library.db.Database;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class ConfirmationDialog extends MaterialAlertDialogBuilder {
    private Context context;

    public ConfirmationDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setup(int movie_id, String msg, String negativeMsg, String positiveMsg) {
        setCancelable(true);
        setMessage(msg);
        setTitle("Confirmation Dialog");
        setNegativeButton(negativeMsg, (dialog, which) -> dialog.dismiss());
    }
}
