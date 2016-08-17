package com.codepath.traintogether.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:03 PM.
 */
public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ItemSpaceDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space + 5;

        outRect.top = parent.getChildLayoutPosition(view) == 0 ? space : 0;
    }
}