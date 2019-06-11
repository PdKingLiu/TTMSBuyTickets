package com.competition.pdking.loginandregister;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.competition.pdking.common.Constant;
import com.competition.pdking.common.weight.TitleView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private EditText edAccount;
    private EditText edPassword;
    private EditText edRePassword;
    private EditText edEmail;
    private EditText edPhone;
    private EditText edIntro;
    private TitleView title;
    private Button btnLogin;
    private ProgressDialog dialog;
    private LinearLayout llUserIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityContainer.add(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在注册...");
        dialog.setTitle("注册中");
        dialog.setCancelable(false);
        initView();
        addLayoutListener(linearLayout, edIntro);
        setBar();
    }

    private void initView() {
        linearLayout = findViewById(R.id.ll_sum);
        edAccount = findViewById(R.id.ed_register_user_name);
        edPassword = findViewById(R.id.ed_input_password);
        edRePassword = findViewById(R.id.ed_re_password);
        edEmail = findViewById(R.id.ed_register_email);
        edPhone = findViewById(R.id.ed_register_phone);
        edIntro = findViewById(R.id.et_intro);
        title = findViewById(R.id.title);
        llUserIcon = findViewById(R.id.ll_user_icon);
        btnLogin = findViewById(R.id.btn_refister_and_login);
        btnLogin.setOnClickListener(this);
        llUserIcon.setOnClickListener(v -> showToast("暂未实现"));
        title.setLeftClickListener(() -> finish());
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

    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            scroll.getWindowVisibleDisplayFrame(rect);
            int mainInvisibleHeight = scroll.getRootView().getHeight() - rect.bottom;
            int screenHeight = scroll.getRootView().getHeight();
            if (mainInvisibleHeight > screenHeight / 4) {
                int[] location = new int[2];
                scroll.getLocationInWindow(location);
                int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                main.scrollTo(0, srollHeight);
            } else {
                main.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_refister_and_login) {
            startRegisterAndLogin();
        }
    }

    private void startRegisterAndLogin() {
        String url = ":8080/CHKusername?";
        String name = "name=";
        String Account = edAccount.getEditableText().toString();
        String Password = edPassword.getEditableText().toString();
        String RePassword = edRePassword.getEditableText().toString();
        String Email = edEmail.getEditableText().toString();
        String Phone = edPhone.getEditableText().toString();
        String Intro = edIntro.getEditableText().toString();
        if (Account.equals("") || Password.equals("") || RePassword.equals("") || Email.equals("") || Phone.equals("") || Intro.equals("")) {
            showToast("请输入完整的信息");
            return;
        }
        showProgressBar();
        new Handler().postDelayed(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constant.IP + url + name + Account)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    hideProgressBar();
                    showToast("网络错误");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String msg = response.body().string();
                    Log.d("Lpp", "CHKusername: " + msg);
                    try {
                        boolean isExist = Boolean.parseBoolean(msg);
                        if (isExist) {
                            hideProgressBar();
                            showToast("账号已存在");
                        } else {
                            register();
                        }
                    } catch (Exception e) {
                        hideProgressBar();
                        showToast("未知异常");
                        Log.d("Lpp", "CHKusername: " + e.getMessage());
                    }
                }
            });
        }, 1000);
    }

    private void register() {
        String Account = edAccount.getEditableText().toString();
        String Password = edPassword.getEditableText().toString();
        String RePassword = edRePassword.getEditableText().toString();
        String Email = edEmail.getEditableText().toString();
        String Phone = edPhone.getEditableText().toString();
        String Intro = edIntro.getEditableText().toString();
        String url = ":8080/user_regMobile?";
        String u_name = "u_name=";
        String u_password = "&u_password=";
        String u_repassword = "&u_repassword=";
        String u_email = "&u_email=";
        String u_tel = "&u_tel=";
        String u_introduce = "&u_introduce=";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.IP + url
                        + u_name + Account
                        + u_password + Password
                        + u_repassword + RePassword
                        + u_email + Email
                        + u_tel + Phone
                        + u_introduce + Intro)
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
                    if (status == 100) {
                        showToast("注册成功");
                        ARouter.getInstance().build("/theater_business_module/main_activity").navigation();
                        finish();
                        ActivityContainer.removeAll();
                    } else {
                        showToast("注册失败");
                    }
                } catch (Exception e) {
                    Log.d("Lpp", "onResponse: " + e.getMessage());
                    showToast("未知异常");
                }
            }
        });
    }


    private void showToast(final String text) {
        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_SHORT).show());
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

}
