package com.competition.pdking.theaterbusiness.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.competition.pdking.common.Constant;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.bean.QueryUserBean;
import com.competition.pdking.theaterbusiness.fragment.MovieFragment;
import com.competition.pdking.theaterbusiness.fragment.OrderFragment;
import com.google.gson.Gson;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Route(path = "/theater_business_module/main_activity")
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    private MovieFragment mMovieFragment;
    private OrderFragment mOrderFragment;
    private FragmentManager mFragmentManager;
    private DrawerLayout mDrawerLayout;
    private NavigationView nv;
    private LinearLayout ll;
    private CircleImageView civ;
    private TextView tvName;
    private TextView tvEmail;
    private int bottomFlag = -1;

    @Autowired
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ARouter.getInstance().inject(this);
        initView();
        mFragmentManager = getSupportFragmentManager();
        bottomNavigationViewListener();
        initFragment();
    }

    public void openDraw() {
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.bnv);
        mDrawerLayout = findViewById(R.id.dl);
        nv = findViewById(R.id.nv);
        ll = (LinearLayout) nv.getHeaderView(0);
        civ = ll.findViewById(R.id.civ_nav);
        tvName = ll.findViewById(R.id.tv_name);
        tvEmail = ll.findViewById(R.id.tv_email);
        Log.d("Lpp", "initView: " + tvName + tvEmail);
        Glide.with(this).load(getResources().getDrawable(R.mipmap.user_icon)).into(civ);
        ll.setBackground(getResources().getDrawable(R.drawable.shape_gradient_title));
        nv.getMenu().findItem(R.id.nav_exit).setOnMenuItemClickListener(item -> {
            finish();
            return true;
        });
        nv.getMenu().findItem(R.id.nav_exit_account).setOnMenuItemClickListener(item -> {
            ARouter.getInstance().build("/login_and_register_module/login_activity").withString(
                    "is_from_main_exit", "true").navigation(MainActivity.this);
            finish();
            return true;
        });
        nv.getMenu().findItem(R.id.nav_account).setOnMenuItemClickListener(item -> {
            mDrawerLayout.closeDrawers();
            return true;
        });
        nv.getMenu().findItem(R.id.nav_age).setOnMenuItemClickListener(item -> {
            mDrawerLayout.closeDrawers();
            return true;
        });
        nv.getMenu().findItem(R.id.nav_introduce).setOnMenuItemClickListener(item -> {
            mDrawerLayout.closeDrawers();
            return true;
        });
        initDrawer();
    }

    private void initDrawer() {
        String url = ":8080/search_user?name=u_name&type=schu";
        String value = "&value=";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.IP + url
                        + value + user)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                try {
                    QueryUserBean bean = new Gson().fromJson(msg, QueryUserBean.class);
                    setUserData(bean.rows.get(0));
                } catch (Exception e) {

                }
                getApplication();
            }
        });
    }

    private void setUserData(QueryUserBean.RowsBean rowsBean) {
        runOnUiThread(() -> {
            tvName.setText("购票员");
            if (rowsBean.uEmail.equals("")) {
                tvEmail.setText("暂未设置邮箱");
            } else {
                tvEmail.setText(rowsBean.uEmail);
            }
            nv.getMenu().findItem(R.id.nav_account).setTitle(rowsBean.uName);
            if (rowsBean.uAge.equals("")) {
                nv.getMenu().findItem(R.id.nav_age).setTitle("暂未设置生日");
            } else {
                nv.getMenu().findItem(R.id.nav_age).setTitle(rowsBean.uAge);
            }
            if (rowsBean.uIntroduce.equals("")) {
                nv.getMenu().findItem(R.id.nav_introduce).setTitle("暂未设置介绍");
            } else {
                nv.getMenu().findItem(R.id.nav_introduce).setTitle(rowsBean.uIntroduce);
            }
        });
    }

    private void initFragment() {
        mMovieFragment = MovieFragment.getINSTANCE();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fl_main, mMovieFragment);
        bottomFlag = R.id.bnv_movie;
        mFragmentTransaction.commit();
    }

    private void bottomNavigationViewListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int i = menuItem.getItemId();
            if (i == R.id.bnv_movie) {
                if (bottomFlag == R.id.bnv_movie) {
                    return true;
                }
                setFragmentPage(R.id.bnv_movie);
                bottomFlag = R.id.bnv_movie;
            } else if (i == R.id.bnv_order) {
                if (bottomFlag == R.id.bnv_order) {
                    return true;
                }
                setFragmentPage(R.id.bnv_order);
                bottomFlag = R.id.bnv_order;
            }
            return true;
        });
    }

    public void setFragmentPage(int fragmentPage) {
        hideFragmentPage();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (fragmentPage == R.id.bnv_movie) {
            if (mMovieFragment == null) {
                mMovieFragment = MovieFragment.getINSTANCE();
                fragmentTransaction.add(R.id.fl_main, mMovieFragment);
            } else {
                fragmentTransaction.show(mMovieFragment);
            }
        } else if (fragmentPage == R.id.bnv_order) {
            if (mOrderFragment == null) {
                mOrderFragment = OrderFragment.getINSTANCE();
                fragmentTransaction.add(R.id.fl_main, mOrderFragment);
            } else {
                fragmentTransaction.show(mOrderFragment);
            }
        }
        fragmentTransaction.commit();
    }

    private void hideFragmentPage() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (bottomFlag == R.id.bnv_movie) {
            if (mMovieFragment != null && !mMovieFragment.isHidden()) {
                fragmentTransaction.hide(mMovieFragment);
            }
        } else if (bottomFlag == R.id.bnv_order) {
            if (mOrderFragment != null && !mOrderFragment.isHidden()) {
                fragmentTransaction.hide(mOrderFragment);
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

}
