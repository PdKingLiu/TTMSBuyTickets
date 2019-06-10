package com.competition.pdking.theaterbusiness.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.fragment.MovieFragment;
import com.competition.pdking.theaterbusiness.fragment.OrderFragment;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private int bottomFlag = -1;

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
        initView();
        mFragmentManager = getSupportFragmentManager();
        bottomNavigationViewListener();
        initFragment();
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.bnv);
        mDrawerLayout = findViewById(R.id.dl);
        nv = findViewById(R.id.nv);
        ll = (LinearLayout) nv.getHeaderView(0);
        civ = ll.findViewById(R.id.civ_nav);
        Glide.with(this).load(getResources().getDrawable(R.mipmap.user_icon)).into(civ);
        ll.setBackground(getResources().getDrawable(R.drawable.shape_gradient_title));
        nv.getMenu().findItem(R.id.nav_exit).setOnMenuItemClickListener(item -> {
            finish();
            return true;
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
