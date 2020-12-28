package com.rainy.rainymusic.activity;

import android.util.Log;
import android.view.View;

import com.rainy.rainymusic.R;

/**
 * create by Rainy on 2020/12/9.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class TestActivity  extends BaseActivity{
    @Override
    protected int layoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init() {
        findViewById(R.id.testcl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kjaskdjakls", "onClick: askld;aksl;dkas" );
            }
        });
    }
}
