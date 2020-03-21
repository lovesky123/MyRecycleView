package com.example.myrecycleview.diff;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecycleview.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
/**
 * 使用AsyncListDiffer来局部刷新（只刷新当前页变更的数据）RecyclerView（不使用notifyDataSetChanged来进行当前页的全部刷新）
 * 子线程计算差异，主线程刷新数据，是对DiffUtil的异步封装用法
 * 适用于数据频繁刷新且数据重合度较高的场景
 * Created by 28973 on 2019/1/2.
 */

public class DiffDemoActivity extends AppCompatActivity {

    private int[] mPhotoResources = {R.drawable.photo1, R.drawable.photo2, R.drawable.photo3,
            R.drawable.photo4, R.drawable.photo5, R.drawable.photo6, R.drawable.photo7, R.drawable.photo8,
            R.drawable.photo9, R.drawable.photo10, R.drawable.photo11, R.drawable.photo12, R.drawable.photo13,
            R.drawable.photo14, R.drawable.photo15, R.drawable.photo16, R.drawable.photo17, R.drawable.photo18,
            R.drawable.photo19, R.drawable.photo20};

    private RecyclerView recyclerView;
    private FloatingActionButton fab_add;
    private DiffPersonAdapter mAdapter;
    private List<Person> mPersons = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);
        recyclerView = findViewById(R.id.recyclerView);
        fab_add = findViewById(R.id.fab_add);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_anim_bottom_in));

        for (int i = 0; i < 80; i++) {
            Person person = new Person();
            person.setNumber(10000 + i);
            person.setName("大华优秀员工" + i);
            person.setPhoto(mPhotoResources[i % mPhotoResources.length]);
            mPersons.add(person);
        }
        mAdapter = new DiffPersonAdapter(this, mPersons);
        recyclerView.setAdapter(mAdapter);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Person> personList = new ArrayList<>();
                for (int i = 0; i < 80; i++) {
                    Person person = new Person();
                    person.setNumber(i % 2 == 0 ?10000 + i : (int)(Math.random() * 28973));
                    person.setName("大华优秀员工" + i);
                    person.setPhoto(mPhotoResources[i % mPhotoResources.length]);
                    personList.add(person);
                }
                mAdapter.refreshData(personList);
            }
        });
    }
}
