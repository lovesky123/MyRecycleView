package com.example.myrecycleview.snaphelper;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecycleview.R;


/**
 * 上下滑动的视频播放切换，类似抖音（只是最简单的演示）
 * Created by 28973 on 2018/12/25.
 */

public class PagerSnapVideoDemoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private int[] mVideoResources = {R.raw.douyin1, R.raw.douyin2, R.raw.douyin3, R.raw.douyin4, R.raw.douyin5, R.raw.douyin6};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_demo);
        recyclerView = findViewById(R.id.recyclerView);
        @SuppressLint("WrongConstant") LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new PagerSnapAdapter());
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                PagerSnapAdapter.SnapHolder snapHolder = (PagerSnapAdapter.SnapHolder)recyclerView.getChildViewHolder(view);
                snapHolder.video_view.setVideoURI(Uri.parse("android.resource://" + getPackageName()+ "/" + mVideoResources[position]));
                snapHolder.video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                PagerSnapAdapter.SnapHolder snapHolder = (PagerSnapAdapter.SnapHolder)recyclerView.getChildViewHolder(view);
                if(snapHolder.video_view != null && snapHolder.video_view.isPlaying()){
                    snapHolder.video_view.stopPlayback();
                }
            }
        });
    }

    class PagerSnapAdapter extends RecyclerView.Adapter{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(PagerSnapVideoDemoActivity.this).inflate(R.layout.item_pager_snap_layout, parent, false);
            SnapHolder decorationHolder = new SnapHolder(rootView);
            return decorationHolder;
        }

        @Override
        public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return mVideoResources.length;
        }
        class SnapHolder extends RecyclerView.ViewHolder{
            VideoView video_view;
            public SnapHolder(View itemView) {
                super(itemView);
                video_view = itemView.findViewById(R.id.video_view);
            }
        }
    }
}
