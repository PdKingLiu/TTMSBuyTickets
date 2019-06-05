package com.competition.pdking.ttmsbuytickets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        ARouter.getInstance().build("/login_and_register_module/login_activity").navigation(this);
        finish();
    }

}
