package com.competition.pdking.theaterbusiness.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.bean.OrderBean;

import java.util.ArrayList;

/**
 * @author liupeidong
 * Created on 2019/6/13 18:45
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<OrderBean> arrayList;
    private OnItemClickListener listener;
    private int kind;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OrderListAdapter(Context context, ArrayList<OrderBean> arrayList,int kind) {
        this.context = context;
        this.arrayList = arrayList;
        this.kind = kind;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_list,
                        viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(arrayList.get(i), i);
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
        TextView tvStatus;
        Button btnWrite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnWrite = itemView.findViewById(R.id.btn_write);
            if (kind == 2) {
                tvStatus.setTypeface(Typeface.DEFAULT);
                tvStatus.setText("已完成");
                tvStatus.setTextSize(18);
                btnWrite.setVisibility(View.VISIBLE);
            }
        }

        public void setData(OrderBean bean, int i) {
            view.setTag(i);
        }
    }
}
