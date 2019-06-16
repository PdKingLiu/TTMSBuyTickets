package com.competition.pdking.theaterbusiness.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.competition.pdking.common.utils.SystemUtil;
import com.competition.pdking.common.weight.TitleView;
import com.competition.pdking.theaterbusiness.R;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvTime;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    private static int anInt = 0;
    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        SystemUtil.setTitleMode(getWindow());
        tvName = findViewById(R.id.tv_name);
        tvTime = findViewById(R.id.tv_time);
        titleView = findViewById(R.id.title);
        titleView.setLeftClickListener(() -> finish());
        list.add("泰坦尼克号");
        list.add("调音师");
        list.add("海王");
        list.add("大黄蜂");
        list.add("心灵捕手");
        list.add("这个杀手不太冷");
        list2.add("2019-6-13 9:30 - 11:00 英语3D");
        list2.add("2019-6-13 11:30 - 12:00 英语3D");
        list2.add("2019-6-13 13:30 - 15:00 英语3D");
        list2.add("2019-6-13 15:30 - 17:00 英语3D");
        list2.add("2019-6-13 18:30 - 20:00 英语3D");
        list2.add("2019-6-13 20:30 - 23:00 英语3D");
        tvName.setText(list.get(anInt % 6));
        tvTime.setText(list2.get(anInt % 6));
        anInt++;
    }
}
