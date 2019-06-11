package com.competition.pdking.theaterbusiness.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.competition.pdking.common.weight.TitleView;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.activity.MainActivity;

/**
 * @author liupeidong
 * Created on 2019/6/4 14:17
 */
public class MovieFragment extends Fragment {

    private static MovieFragment INSTANCE;
    private TitleView titleView;

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
        titleView.setLeftClickListener(() -> {
            MainActivity mainActivity = ((MainActivity) getActivity());
            if (mainActivity != null) {
                mainActivity.openDraw();
            }
        });
    }

}
