package com.example.net.movies.jwt.tmdb.library.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridDecoration extends RecyclerView.ItemDecoration {

    private boolean includeEdges = false;
    private int space = 0;
    private int columns;

    // Todo: Adapt The code to Work with Random column Number

    public GridDecoration(int columns, boolean includeEdges, int space) {
        this.includeEdges = includeEdges;
        this.space = space;
        this.columns = columns;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position % 2 == 0) {
            outRect.right = space / 2;
        } else {
            outRect.left = space / 2;
        }
        if (position != 0 && position != 1)
            outRect.top = space;
    }
}
