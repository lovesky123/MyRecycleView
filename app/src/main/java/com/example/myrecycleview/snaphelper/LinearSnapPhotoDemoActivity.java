package com.example.myrecycleview.snaphelper;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myrecycleview.R;

public class LinearSnapPhotoDemoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DisplayMetrics mDisplayMetrics;
    private int[] mPhotoResources = {R.drawable.photo1, R.drawable.photo2, R.drawable.photo3,
            R.drawable.photo4, R.drawable.photo5, R.drawable.photo6, R.drawable.photo7, R.drawable.photo8,
            R.drawable.photo9, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12, R.drawable.photo13,
            R.drawable.photo14, R.drawable.photo15, R.drawable.photo16, R.drawable.photo17, R.drawable.photo18,
            R.drawable.photo19, R.drawable.photo20};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_demo);
        mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new SnapAdapter());

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int[] location = new int[2];
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View v = recyclerView.getChildAt(i);
                    v.getLocationOnScreen(location);
                    //当前RecyclerView的中心X坐标
                    int recyclerViewCenterX = recyclerView.getLeft() + recyclerView.getWidth() / 2;
                    //当前item的中心X坐标
                    int itemCenterX = location[0] + v.getWidth() / 2;
                    //缩放比例
                    float scale = 0.8f;
                    //RecyclerView的中心X坐标和item的中心X坐标偏差（最大为RecyclerView的getWidth一半大小）
                    int offX = Math.abs(itemCenterX - recyclerViewCenterX);
                    //偏差越大，意味着需要更多的缩放
                    float percent = 1 - offX * (1 - scale) / v.getWidth();

                    if (percent < scale) {
                        percent = scale;
                    }
                    v.setScaleX(percent);
                    v.setScaleY(percent);
                }
            }
        });
    }

    class SnapAdapter extends RecyclerView.Adapter{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(LinearSnapPhotoDemoActivity.this).inflate(R.layout.item_snap_photo_layout, parent, false);
            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            layoutParams.width = mDisplayMetrics.widthPixels *2/3;
            rootView.setLayoutParams(layoutParams);
            SnapAdapter.SnapHolder snapHolder = new SnapAdapter.SnapHolder(rootView);
            return snapHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SnapHolder snapHolder = (SnapHolder)holder;
            final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            // 最后一个item居中问题：第一个条目leftMagrin、最后一个条目的rightMargin为(recyclerView.getWidth- item.getWidth)/2
            int margin = (mDisplayMetrics.widthPixels - layoutParams.width) / 2;
            layoutParams.leftMargin = position == 0 ? margin:0;
            layoutParams.rightMargin = position == mPhotoResources.length - 1 ? margin:0;

            holder.itemView.setLayoutParams(layoutParams);
            Glide.with(LinearSnapPhotoDemoActivity.this).load(mPhotoResources[position]).into(snapHolder.img_photo);
        }

        @Override
        public int getItemCount() {
            return mPhotoResources.length;
        }

        class SnapHolder extends RecyclerView.ViewHolder{

            ImageView img_photo;

            public SnapHolder(View itemView) {
                super(itemView);
                img_photo = itemView.findViewById(R.id.img_photo);
            }
        }
    }
}
