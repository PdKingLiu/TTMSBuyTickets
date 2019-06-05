package com.competition.pdking.ttmsbuytickets;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

public class AppMainActivity extends AppCompatActivity {

    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        if (!checkPermission()) {
            applyPermission();
        }
    }

    public boolean checkPermission() {
        boolean[] flag = {false, false};
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                flag[i] = false;
            } else {
                flag[i] = true;
            }
        }
        return flag[0] && flag[1];
    }

    private void applyPermission() {
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0) {
            boolean[] flag = {false, false};
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    flag[i] = true;
                }
            }
            if ((flag[0] && flag[1])) {
                ARouter.getInstance().build("/theater_business_module/main_activity").navigation();
                finish();
            }
        }
    }
}
