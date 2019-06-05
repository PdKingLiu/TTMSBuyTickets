package com.competition.pdking.theaterbusiness.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.competition.pdking.theaterbusiness.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupeidong
 * Created on 2019/6/4 14:18
 */
public class OrderFragment extends Fragment {

    private static OrderFragment INSTANCE;

    private List<Fragment> fragmentList;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    private String[] strings = {"已购影票", "历史订单"};

    private FragmentPagerAdapter pagerAdapter;

    private boolean[] isFirst = {false, true};

    public static OrderFragment getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new OrderFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_order_fragment,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View mView) {
        mTabLayout = mView.findViewById(R.id.tl_title);
        mViewPager = mView.findViewById(R.id.vp_meeting_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPagerAndTabLayout();
    }

    private void initPagerAndTabLayout() {
        fragmentList = new ArrayList<>();
        fragmentList.add(HadBuyTicketsFragment.newInstance());
        fragmentList.add(HistoryTicketsFragment.newInstance());
        pagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(R.layout.item_table_item);
            if (i == 1) {
                tab.select();
                tab.getCustomView().findViewById(R.id.tv_tab).setSelected(true);
                ((TextView) tab.getCustomView().findViewById(R.id.tv_tab)).setTextSize(22);
                ((TextView) tab.getCustomView().findViewById(R.id.tv_tab)).setTypeface(Typeface
                        .defaultFromStyle(Typeface.BOLD));
            }
            ((TextView) tab.getCustomView().findViewById(R.id.tv_tab)).setText(strings[i]);
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                loadFirstDate(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void updateTabView(TabLayout.Tab tab, boolean b) {
        if (b) {
            if (tab != null) {
                tab.select();
                ((TextView) tab.getCustomView().findViewById(R.id.tv_tab)).setTextSize(22);
                ((TextView) tab.getCustomView().findViewById(R.id.tv_tab)).setTypeface(Typeface
                        .defaultFromStyle(Typeface.BOLD));
            }
        } else {
            if (tab != null) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv_tab)).setTextSize(20);
                ((TextView) tab.getCustomView().findViewById(R.id.tv_tab)).setTypeface(Typeface
                        .defaultFromStyle(Typeface.NORMAL));
            }
        }
    }

    private void loadFirstDate(int i) {
        switch (i) {
            case 0:
                if (isFirst[0]) {
//                    ((HadBuyTicketsFragment) fragmentList.get(0)).autoRefresh();
                    isFirst[0] = false;
                }
                break;
            case 1:
                if (isFirst[1]) {
//                    ((HistoryTicketsFragment) fragmentList.get(1)).autoRefresh();
                    isFirst[1] = false;
                }
                break;
        }
    }
}
