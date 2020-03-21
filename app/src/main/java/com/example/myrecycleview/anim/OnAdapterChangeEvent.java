package com.example.myrecycleview.anim;


public interface OnAdapterChangeEvent {

    void onItemMove(int source, int target);

    void onItemDelete(int position);
}
