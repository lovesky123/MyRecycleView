package com.example.myrecycleview.decoration;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecycleview.R;

/**
 * 分割线分组Demo
 * Created by 28973 on 2018/12/23.
 */

public class DecorationDemoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String[] array = {"滨江", "萧山", "江干", "拱墅", "上城", "下城", "余杭", "宁波", "绍兴", "金华", "衢州", "嘉兴", "湖州", "丽水", "安徽", "福建", "上海", "江苏", "河南", "广东", "北京"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_demo);

        recyclerView = findViewById(R.id.recyclerView);
        @SuppressLint("WrongConstant") LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DecorationTitle());
        recyclerView.addItemDecoration(new DecorationImage(this));
        recyclerView.setAdapter(new DecorationAdapter());
    }

    class DecorationAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(DecorationDemoActivity.this).inflate(R.layout.item_decoration_layout, parent, false);
            DecorationHolder decorationHolder = new DecorationHolder(rootView);
            return decorationHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            DecorationHolder decorationHolder = (DecorationHolder)holder;
            decorationHolder.tx_title.setText(array[position]);
        }

        @Override
        public int getItemCount() {
            return array.length;
        }

        class DecorationHolder extends RecyclerView.ViewHolder{

            TextView tx_title;

            public DecorationHolder(View itemView) {
                super(itemView);
                tx_title = itemView.findViewById(R.id.tx_title);
            }
        }
    }
}
