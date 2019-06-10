package com.competition.pdking.loginandregister;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;


@Route(path = "/login_and_register_module/login_activity")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btRegister;
    private Button btFindPassword;
    private Button btLogin;
    private TextInputEditText edPhone;
    private TextInputEditText edPassword;

    private ProgressDialog dialog;
    private AlertDialog dia;
    private boolean[] flag = {false, false};

    private String[] permissicns = new
            String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setBar();
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在登录...");
        dialog.setTitle("登录中");
        dialog.setCancelable(false);
        if (!checkPermission()) {
            applyPermission();
        }
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
            if (!(flag[0] && flag[1])) {
                dia = new AlertDialog.Builder(this)
                        .setTitle("警告")
                        .setMessage("拒绝权限软件将无法使用")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create();
                dia.show();
            }
        }
    }

    public boolean checkPermission() {
        boolean[] flag = {false, false};
        for (int i = 0; i < permissicns.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissicns[i])
                    != PackageManager.PERMISSION_GRANTED) {
                flag[i] = false;
            } else {
                flag[i] = true;
            }
        }
        return flag[0] && flag[1];
    }

    private void initView() {
        btRegister = findViewById(R.id.bt_login_register);
        btFindPassword = findViewById(R.id.bt_login_find_password);
        btLogin = findViewById(R.id.btn_login);
        btRegister.setOnClickListener(this);
        btFindPassword.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        edPhone = findViewById(R.id.ed_input_phone);
        edPassword = findViewById(R.id.ed_input_password);
    }

    private void setBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_login) {
            startLogin();
        } else if (i == R.id.bt_login_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        } else if (i == R.id.bt_login_find_password) {
            Toast.makeText(this, "暂未实现", Toast.LENGTH_SHORT).show();
        }
    }


    private void startLogin() {
        final String phone = edPhone.getText().toString();
        final String password = edPassword.getText().toString();
        ARouter.getInstance().build("/theater_business_module/main_activity").navigation();
    }

    private void showToast(final String text) {
        runOnUiThread(() -> Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show());
    }

    private void hideProgressBar() {
        runOnUiThread(() -> {
            if (dialog != null) {
                dialog.hide();
            }
        });
    }

    private void showProgressBar() {
        runOnUiThread(() -> {
            if (dialog != null) {
                dialog.show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.hide();
            }
            dialog.dismiss();
        }
        if (dia != null) {
            if (dia.isShowing()) {
                dia.hide();
            }
            dia.dismiss();
        }
    }

    private void applyPermission() {
        ActivityCompat.requestPermissions(this, permissicns, 1);
    }

}
