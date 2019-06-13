package com.competition.pdking.theaterbusiness.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.activity.ChooseTicketActivity;
import com.competition.pdking.theaterbusiness.activity.MovieDetailsActivity;
import com.competition.pdking.theaterbusiness.adapter.OrderListAdapter;
import com.competition.pdking.theaterbusiness.adapter.SessionListAdapter;
import com.competition.pdking.theaterbusiness.bean.OrderBean;
import com.competition.pdking.theaterbusiness.bean.SessionBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author liupeidong
 * Created on 2019/6/4 14:51
 */
public class HadBuyTicketsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private OrderListAdapter adapter;
    private ArrayList<OrderBean> list;

    public static HadBuyTicketsFragment newInstance() {
        return new HadBuyTicketsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_had_buy_fragment,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_order_list_had);
        refreshLayout = view.findViewById(R.id.srl_flush);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerAndRefresh();
    }

    private void initRecyclerAndRefresh() {
        list = new ArrayList<>();
        list.add(new OrderBean());
        list.add(new OrderBean());
        list.add(new OrderBean());
        list.add(new OrderBean());
        list.add(new OrderBean());
        list.add(new OrderBean());
        list.add(new OrderBean());
        list.add(new OrderBean());
        adapter = new OrderListAdapter(getContext(), list,1);
        adapter.setListener((view, i) -> {
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.finishLoadMoreWithNoMoreData());
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.finishRefresh(true));
    }

}
