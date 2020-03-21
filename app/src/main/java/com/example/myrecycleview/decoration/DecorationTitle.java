package com.example.myrecycleview.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 28973 on 2018/12/23.
 */

public class DecorationTitle extends RecyclerView.ItemDecoration{

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if(position % 7 == 0){
                int left = child.getLeft();
                int right = child.getRight();
                int top = child.getTop() - 80;
                int bottom = child.getTop() - 20;
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLUE);
                c.drawRect(left, top, right, bottom + 20, paint);
                paint.setColor(Color.GRAY);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(40);
                paint.setAntiAlias(true);
                c.drawText("分组Group", left, bottom, paint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if(position % 7 == 0){
            outRect.top = 80;
        }
    }
}
