package com.competition.pdking.ttmsbuytickets;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * @author liupeidong
 * Created on 2019/6/5 13:54
 */
public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
