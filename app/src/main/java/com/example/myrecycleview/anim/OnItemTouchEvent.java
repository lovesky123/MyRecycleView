package com.example.myrecycleview.anim;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class OnItemTouchEvent extends ItemTouchHelper.Callback{

    private OnAdapterChangeEvent adapterChangeEvent;

    public OnItemTouchEvent(OnAdapterChangeEvent adapterChangeEvent) {
        this.adapterChangeEvent = adapterChangeEvent;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //四种手势可以长按拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT;
        //左滑右滑可以单操作（与布局方向有关，自行设置）
        return makeMovementFlags(dragFlags, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapterChangeEvent.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapterChangeEvent.onItemDelete(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

}
