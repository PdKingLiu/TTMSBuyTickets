package com.competition.pdking.theaterbusiness.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.competition.pdking.theaterbusiness.R;


/**
 * @author liupeidong
 * Created on 2019/6/4 14:51
 */
public class HistoryTicketsFragment extends Fragment {

    public static HistoryTicketsFragment newInstance() {
        return new HistoryTicketsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_history_buy_fragment,
                container, false);
        return view;
    }

}
