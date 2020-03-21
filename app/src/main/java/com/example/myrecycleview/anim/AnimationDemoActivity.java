package com.example.myrecycleview.anim;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.myrecycleview.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class AnimationDemoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab_add;
    private PhotoDisplayAdapter mAdapter;
    private ItemTouchHelper mTouchHelper;
    private LayoutInflater mLayoutInflater;
    private int mWindowWidth;
    private boolean mRecyclerScrollDown;//是否下滑
    private List<ItemEntity> mItemEntities = new ArrayList();
    private int[] mPhotoResources = {R.drawable.photo1, R.drawable.photo2, R.drawable.photo3,
            R.drawable.photo4, R.drawable.photo5, R.drawable.photo6, R.drawable.photo7, R.drawable.photo8,
            R.drawable.photo9, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12, R.drawable.photo13,
            R.drawable.photo14, R.drawable.photo15, R.drawable.photo16, R.drawable.photo17, R.drawable.photo18,
            R.drawable.photo19, R.drawable.photo20};

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mWindowWidth = metrics.widthPixels;
        mLayoutInflater = LayoutInflater.from(this);
        recyclerView = findViewById(R.id.recyclerView);
        fab_add = findViewById(R.id.fab_add);

        for (int i = 0; i < 88; i++) {
            mItemEntities.add(new ItemEntity("这么可爱肯定是男孩子" + i, mPhotoResources[i % mPhotoResources.length]));
        }
        /**设置默认实现的动画器，默认为DefaultItemAnimation，这里我们通过改动DefaultItemAnimation自定义实现（仅仅为add动画添加缩放效果）*/
        recyclerView.setItemAnimator(new SimpleDemoAnimator());
        //设置瀑布流布局，两列
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new PhotoDisplayAdapter();
        //设置手势操作
        mTouchHelper = new ItemTouchHelper(new OnItemTouchEvent(mAdapter));
//        recyclerView.addItemDecoration(new DecorationImage(this));
        recyclerView.setAdapter(mAdapter);
        //将recyclerView与手势绑定
        mTouchHelper.attachToRecyclerView(recyclerView);
        //添加滚动监听，根据滚动方向添加不同动画
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mRecyclerScrollDown = dy > 0;
            }
        });
        //增加各item的进入动画（首屏进入），LayoutAnimation可实现每个item的动画延迟
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(AnimationDemoActivity.this, R.anim.layout_anim_bottom_in));
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] positions = null;
                //将新卡片添加至当前显示的页面第一个完全显示的item位置
                positions = ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPositions(positions);
                int addPosition = 0;
                if(positions != null && positions.length > 0){
                    addPosition = positions[0];
                }
                if(addPosition < 0 || addPosition > mItemEntities.size() - 1){
                    addPosition = 0;
                }
                mItemEntities.add(addPosition, new ItemEntity("新增的卡片" + addPosition, mPhotoResources[(int)(Math.random() * mPhotoResources.length)]));
                mAdapter.notifyItemInserted(addPosition);
                mAdapter.notifyItemRangeChanged(addPosition, mItemEntities.size() - 1);
                if(addPosition == 0){
                    recyclerView.scrollToPosition(0);
                }
            }
        });
    }

    class PhotoDisplayAdapter extends RecyclerView.Adapter implements OnAdapterChangeEvent{

        
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            View rootView = mLayoutInflater.inflate(R.layout.item_card_layout, parent, false);
            CardViewHolder cardViewHolder = new CardViewHolder(rootView);
            return cardViewHolder;
        }

        @Override
        public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
            //运行item的滑入动画
            runItemEnterAnimation(holder);
            CardViewHolder cardViewHolder = (CardViewHolder)holder;
            ViewGroup.LayoutParams layoutParams = cardViewHolder.itemView.getLayoutParams();
            int height = 0;
            //随机分配item高度
            if((height = mItemEntities.get(position).height) == 0){
                height = (int)(mWindowWidth /2 * 16 /9 - Math.random() * 400);
                mItemEntities.get(position).height = height;
            }
            layoutParams.height = height;
            cardViewHolder.itemView.setLayoutParams(layoutParams);
            Glide.with(AnimationDemoActivity.this).load(mItemEntities.get(position).resource).into(cardViewHolder.img_photo);
            cardViewHolder.tx_content.setText(mItemEntities.get(position).content);
            cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemEntity itemEntity = new ItemEntity("通过点击改变的卡片", mPhotoResources[0]);
                    mItemEntities.set(position, itemEntity);
                    notifyItemChanged(position, itemEntity);
                    Toast.makeText(AnimationDemoActivity.this,"改变了" + position + "卡片", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItemEntities.size();
        }

        @Override
        public void onItemMove(int source, int target) {
            Collections.swap(mItemEntities, source, target);
            mAdapter.notifyItemMoved(source,target);
        }

        @Override
        public void onItemDelete(int position) {
            mItemEntities.remove(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, mItemEntities.size() - 1);
        }

        @Override
        public void onViewDetachedFromWindow( RecyclerView.ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.itemView.clearAnimation();
        }
    }

    class CardViewHolder extends RecyclerView.ViewHolder{
        ImageView img_photo;
        TextView tx_content;
        public CardViewHolder(View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.img_photo);
            tx_content = itemView.findViewById(R.id.tx_content);
        }
    }
    /**滚动时的动画*/
    private void runItemEnterAnimation(RecyclerView.ViewHolder viewHolder){
        AnimationSet animationSet = new AnimationSet(true);
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,mRecyclerScrollDown ? 0.5f:-0.5f, Animation.RELATIVE_TO_SELF, 0);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(500);
        viewHolder.itemView.startAnimation(animationSet);
    }
}
