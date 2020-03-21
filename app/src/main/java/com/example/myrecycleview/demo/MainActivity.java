package com.example.myrecycleview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myrecycleview.R;
import com.example.myrecycleview.anim.AnimationDemoActivity;
import com.example.myrecycleview.decoration.DecorationDemoActivity;
import com.example.myrecycleview.diff.DiffDemoActivity;
import com.example.myrecycleview.snaphelper.LinearSnapPhotoDemoActivity;
import com.example.myrecycleview.snaphelper.PagerSnapVideoDemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tx_animation;
    private TextView tx_decoration;
    private TextView tx_linear_snap;
    private TextView tx_pager_snap;
    private TextView tx_diff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_animation = findViewById(R.id.tx_animation);
        tx_decoration = findViewById(R.id.tx_decoration);
        tx_linear_snap = findViewById(R.id.tx_linear_snap);
        tx_pager_snap = findViewById(R.id.tx_pager_snap);
        tx_diff = findViewById(R.id.tx_diff);
        setViewOnClicks(tx_animation, tx_decoration, tx_linear_snap, tx_pager_snap, tx_diff);
    }

    private void setViewOnClicks(View... views){
        for (View view: views) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_animation:
                startActivity(new Intent(this, AnimationDemoActivity.class));
                break;
            case R.id.tx_decoration:
                startActivity(new Intent(this, DecorationDemoActivity.class));
                break;
            case R.id.tx_linear_snap:
                startActivity(new Intent(this, LinearSnapPhotoDemoActivity.class));
                break;
            case R.id.tx_pager_snap:
                startActivity(new Intent(this, PagerSnapVideoDemoActivity.class));
                break;
            case R.id.tx_diff:
                startActivity(new Intent(this, DiffDemoActivity.class));
                break;
        }
    }
}

