package com.competition.pdking.ttmsbuytickets;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.competition.pdking.ttmsbuytickets.fragment.MovieFragment;
import com.competition.pdking.ttmsbuytickets.fragment.OrderFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    private MovieFragment mMovieFragment;

    private OrderFragment mOrderFragment;

    private FragmentManager mFragmentManager;

    private int bottomFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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
    }

    private void initFragment() {
        mMovieFragment = MovieFragment.getINSTANCE();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fl_main, mMovieFragment);
        bottomFlag = R.id.bnv_movie;
        mFragmentTransaction.commit();
    }

    private void bottomNavigationViewListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bnv_movie:
                        if (bottomFlag == R.id.bnv_movie) {
                            break;
                        }
                        setFragmentPage(R.id.bnv_movie);
                        bottomFlag = R.id.bnv_movie;
                        break;
                    case R.id.bnv_order:
                        if (bottomFlag == R.id.bnv_order) {
                            break;
                        }
                        setFragmentPage(R.id.bnv_order);
                        bottomFlag = R.id.bnv_order;
                        break;
                }
                return true;
            }
        });
    }

    public void setFragmentPage(int fragmentPage) {
        hideFragmentPage();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (fragmentPage) {
            case R.id.bnv_movie:
                if (mMovieFragment == null) {
                    mMovieFragment = MovieFragment.getINSTANCE();
                    fragmentTransaction.add(R.id.fl_main, mMovieFragment);
                } else {
                    fragmentTransaction.show(mMovieFragment);
                }
                break;
            case R.id.bnv_order:
                if (mOrderFragment == null) {
                    mOrderFragment = OrderFragment.getINSTANCE();
                    fragmentTransaction.add(R.id.fl_main, mOrderFragment);
                } else {
                    fragmentTransaction.show(mOrderFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragmentPage() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (bottomFlag) {
            case R.id.bnv_movie:
                if (mMovieFragment != null && !mMovieFragment.isHidden()) {
                    fragmentTransaction.hide(mMovieFragment);
                }
                break;
            case R.id.bnv_order:
                if (mOrderFragment != null && !mOrderFragment.isHidden()) {
                    fragmentTransaction.hide(mOrderFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

}
