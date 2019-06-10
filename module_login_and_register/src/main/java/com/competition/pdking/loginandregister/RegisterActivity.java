package com.competition.pdking.loginandregister;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.competition.pdking.common.weight.TitleView;

public class RegisterActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText editText;
    private TitleView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        addLayoutListener(linearLayout, editText);
        setBar();
    }

    private void initView() {
        linearLayout = findViewById(R.id.ll_sum);
        editText = findViewById(R.id.et_intro);
        title = findViewById(R.id.title);
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

}
