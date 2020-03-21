package com.example.myrecycleview.diff;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myrecycleview.R;

import java.util.List;

public class DiffPersonAdapter extends RecyclerView.Adapter{
    private AsyncListDiffer<Person> mListDiffer;
    private Context mContext;
    private LayoutInflater mInflater;
    private DisplayMetrics mDisplayMetrics;

    public DiffPersonAdapter(Context context, List<Person> persons) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        mDisplayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        mListDiffer = new AsyncListDiffer<Person>(this, itemCallback);
        mListDiffer.submitList(persons);
    }

    /**通过该接口刷新数据*/
    public void refreshData(List<Person> persons){
        if(mListDiffer != null){
            mListDiffer.submitList(persons);
        }
    }

    private DiffUtil.ItemCallback<Person> itemCallback = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(Person oldItem, Person newItem) {
            return oldItem.getNumber() == newItem.getNumber();//比较标示如id
        }

        @Override
        public boolean areContentsTheSame(Person oldItem, Person newItem) {
            return TextUtils.equals(oldItem.getName(), newItem.getName()) && oldItem.getPhoto() == newItem.getPhoto();//比较具体内容
        }

        @Override
        public Object getChangePayload(Person oldItem, Person newItem) {
            return super.getChangePayload(oldItem, newItem);//当id一样，内容不一样时，用于返回内容的差异，根据这个差异做你想要的处理
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_grid_person_layout, parent, false);
        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
        layoutParams.width = mDisplayMetrics.widthPixels / 4;
        layoutParams.height = mDisplayMetrics.widthPixels / 4 * 16 /9;
        rootView.setLayoutParams(layoutParams);
        PersonHolder holder = new PersonHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        Log.i("onBindViewHolder","看看onBindViewHolder的调用次数---position" + position);
        PersonHolder personHolder = (PersonHolder)holder;
        Glide.with(mContext).load(mListDiffer.getCurrentList().get(position).getPhoto()).into(personHolder.img_photo);
        personHolder.tx_number.setText(String.valueOf(mListDiffer.getCurrentList().get(position).getNumber()));
        personHolder.tx_name.setText(mListDiffer.getCurrentList().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mListDiffer.getCurrentList().size();
    }

    class PersonHolder extends RecyclerView.ViewHolder{

        ImageView img_photo;
        TextView tx_number;
        TextView tx_name;

        public PersonHolder(View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.img_photo);
            tx_number = itemView.findViewById(R.id.tx_number);
            tx_name = itemView.findViewById(R.id.tx_name);
        }
    }
}
