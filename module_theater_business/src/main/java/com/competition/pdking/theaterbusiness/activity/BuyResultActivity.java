package com.competition.pdking.theaterbusiness.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.competition.pdking.common.utils.SystemUtil;
import com.competition.pdking.common.weight.TitleView;
import com.competition.pdking.theaterbusiness.R;

public class BuyResultActivity extends AppCompatActivity {


    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_result);
        SystemUtil.setTitleMode(getWindow());
        titleView = findViewById(R.id.title);
        titleView.setLeftClickListener(() -> finish());
    }
}
