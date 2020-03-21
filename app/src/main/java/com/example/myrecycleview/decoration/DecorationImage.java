package com.example.myrecycleview.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecycleview.R;

public class DecorationImage extends RecyclerView.ItemDecoration{

    Bitmap bitmap = null;
    Context context = null;

    public DecorationImage(Context context) {
        this.context = context;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if(bitmap == null){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_livepreview_s);
        }
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int left = child.getLeft();
            int top = child.getTop();
            Paint paint = new Paint();
            if(bitmap != null && !bitmap.isRecycled())
            c.drawBitmap(bitmap, left + 200, top, paint);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
