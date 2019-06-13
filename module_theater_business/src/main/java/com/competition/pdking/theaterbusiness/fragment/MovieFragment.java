package com.competition.pdking.theaterbusiness.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.competition.pdking.common.Constant;
import com.competition.pdking.common.weight.TitleView;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.activity.MainActivity;
import com.competition.pdking.theaterbusiness.activity.MovieDetailsActivity;
import com.competition.pdking.theaterbusiness.adapter.MovieListAdapter;
import com.competition.pdking.theaterbusiness.bean.QueryMoiveListBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupeidong
 * Created on 2019/6/4 14:17
 */
public class MovieFragment extends Fragment {

    private static MovieFragment INSTANCE;
    private TitleView titleView;

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private MovieListAdapter adapter;
    private ArrayList<QueryMoiveListBean.RowsBean> list;

    public static MovieFragment getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MovieFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_movie_fragment,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleView = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.rv_movie_list);
        refreshLayout = view.findViewById(R.id.srl_flush);
        titleView.setLeftClickListener(() -> {
            MainActivity mainActivity = ((MainActivity) getActivity());
            if (mainActivity != null) {
                mainActivity.openDraw();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerAndRefresh();
        refreshLayout.autoRefresh();
    }

    private void initRecyclerAndRefresh() {
        list = new ArrayList<>();
        adapter = new MovieListAdapter(getActivity(), list);
        adapter.setListener((view, i) -> {
            Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", list.get(i));
            intent.putExtra("user", bundle);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.finishLoadMoreWithNoMoreData());
        refreshLayout.setOnRefreshListener(refreshLayout -> refresh());
    }

    private void refresh() {
        String url = ":8080/search_all_plays?page=1&rows=100";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.IP + url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Lpp", "onFailure: " + e.getMessage());
                refreshLayout.finishRefresh(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                try {
                    QueryMoiveListBean bean = new Gson().fromJson(msg, QueryMoiveListBean.class);
                    list.clear();
                    list.addAll(bean.rows);
                    refreshLayout.finishRefresh(true);
                    getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                } catch (Exception e) {
                    Log.d("Lpp", "onResponse: " + e.getMessage());
                    refreshLayout.finishRefresh(false);
                }
            }
        });
    }

}
