package com.appynitty.ghantagaditracker.customComponents;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ayan Dey on 29/6/19.
 */
public class RecyclerViewDecorator extends RecyclerView.ItemDecoration {

    private int itemOffset;

    public RecyclerViewDecorator(int itemOffset) {
        this.itemOffset = itemOffset;
    }

    public RecyclerViewDecorator(@NonNull Context context, @DimenRes int itemOffsetId) {

        this(context.getResources().getDimensionPixelSize(itemOffsetId));

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset);
    }
}
