package com.example.project1301.spacing;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing;

    public SpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Đặt margin cho item dựa trên spacing được chỉ định
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;

        // Đặt margin top chỉ cho item đầu tiên để tránh khoảng trống kép giữa các item
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.top = spacing;
//        } else {
//            outRect.top = 0;
//        }
    }
}