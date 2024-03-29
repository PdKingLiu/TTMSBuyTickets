package com.competition.pdking.loginandregister;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.competition.pdking.common.Constant;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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

    @Autowired
    String is_from_main_exit;

    private String[] permissicns = new
            String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setBar();
        ARouter.getInstance().inject(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在登录...");
        dialog.setTitle("登录中");
        dialog.setCancelable(false);
        if (!checkPermission()) {
            applyPermission();
        }
        autoLogin();
    }

    private void autoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String account = sharedPreferences.getString("user_account", "null");
        String password = sharedPreferences.getString("user_password", "null");
        if (account.equals("null") || password.equals("null")) {
            return;
        }
        edPhone.setText(account);
        edPassword.setText(password);
        if (is_from_main_exit != null && is_from_main_exit.equals("true")) {
        } else {
            btLogin.callOnClick();
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
            ActivityContainer.add(this);
        } else if (i == R.id.bt_login_find_password) {
            Toast.makeText(this, "暂未实现", Toast.LENGTH_SHORT).show();
        }
    }


    private void startLogin() {
        final String phone = edPhone.getText().toString();
        final String password = edPassword.getText().toString();
        if (phone.equals("") || password.equals("")) {
            Toast.makeText(this, "账号或密码有误", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = ":8080/user_loginMobile?";
        String u_name = "u_name=";
        String u_password = "&u_password=";
        showProgressBar();
        new Handler().postDelayed(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constant.IP + url + u_name + phone + u_password + password)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    hideProgressBar();
                    showToast("网络错误");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    hideProgressBar();
                    String msg = response.body().string();
                    try {
                        int status = Integer.parseInt(msg);
                        if (status == 10001) {
                            SharedPreferences.Editor editor =
                                    LoginActivity.this.getSharedPreferences("user_data",
                                            MODE_PRIVATE).edit();
                            editor.clear();
                            editor.putString("user_account", phone);
                            editor.putString("user_password", password);
                            editor.apply();
                            ARouter.getInstance().build("/theater_business_module/main_activity").withString("user",phone).navigation();
                            finish();
                        } else if (status == 30013) {
                            showToast("账号或密码有误");
                        } else {
                            showToast("未知错误");
                        }
                    } catch (Exception e) {
                        showToast("未知异常");
                    }
                }
            });
        }, 1000);
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
