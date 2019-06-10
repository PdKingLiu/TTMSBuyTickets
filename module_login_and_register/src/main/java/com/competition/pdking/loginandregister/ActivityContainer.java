package com.competition.pdking.loginandregister;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupeidong
 * Created on 2019/6/10 17:44
 */
public class ActivityContainer {

    private static List<Activity> list = new ArrayList<>();

    public void add(Activity activity) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (!list.contains(activity)) {
            list.add(activity);
        }
    }

    public void remove(Activity activity) {
        if (list == null) {
            list = new ArrayList<>();
            return;
        }
        activity.finish();
        list.remove(activity);
    }

    private void removeAll() {
        if (list == null) {
            list = new ArrayList<>();
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).finish();
        }
        list.clear();
    }

}
