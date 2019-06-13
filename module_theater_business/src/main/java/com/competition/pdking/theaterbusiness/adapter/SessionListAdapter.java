package com.competition.pdking.theaterbusiness.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.bean.SessionBean;

import java.util.ArrayList;

/**
 * @author liupeidong
 * Created on 2019/6/13 16:27
 */
public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<SessionBean> arrayList;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SessionListAdapter(Context context, ArrayList<SessionBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_session_list,
                viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(arrayList.get(i),i);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(v, (Integer) v.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setData(SessionBean sessionBean, int i) {
            view.setTag(i);
        }
    }


}
