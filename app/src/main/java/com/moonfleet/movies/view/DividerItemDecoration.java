package com.moonfleet.movies.view;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int columns;
    private final int itemsGap;

    public DividerItemDecoration(int columns, int itemsGap) {
        this.columns = columns;
        this.itemsGap = itemsGap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int left = 0, top = 0, right = 0, bottom = 0;

        int headersCount = 0;

        int position = parent.getChildPosition(view);

        // For header views
        if (position < headersCount) {
            outRect.set(left, top, right, bottom);
            return;
        }

        if (headersCount > 1) {
            position++;
        }

        if (position % columns == 0) {
            left = itemsGap;
            right = itemsGap / 2;
        } else if (position % columns == columns - 1) {
            right = itemsGap;
            left = itemsGap / 2;
        } else {
            left = itemsGap / 2;
            right = itemsGap / 2;
        }

        top = itemsGap;

        outRect.set(left, top, right, bottom);
    }

}
