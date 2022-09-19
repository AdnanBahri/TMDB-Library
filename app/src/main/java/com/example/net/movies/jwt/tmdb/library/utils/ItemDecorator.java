package com.example.net.movies.jwt.tmdb.library.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorator extends RecyclerView.ItemDecoration {

    private int dp;

    public ItemDecorator(int dp) {
        this.dp = dp;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int space = Constants.dpToPx(view.getContext(), dp) / 2;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.right = space;
        } else if (parent.getChildAdapterPosition(view) == parent.getChildCount() - 1) {
            outRect.left = space;
        } else {
            outRect.right = space;
            outRect.left = space;
        }
    }


}
