package com.competition.pdking.theaterbusiness.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    private static int anInt = 0;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OrderListAdapter(Context context, ArrayList<OrderBean> arrayList,int kind) {
        this.context = context;
        this.arrayList = arrayList;
        this.kind = kind;
        list.add("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p457760035.webp");
        list.add("https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2551995207.webp");
        list.add("https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.jpg");
        list.add("https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541662397.webp");
        list.add("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480965695.webp");
        list.add("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p511118051.webp");
        list2.add("泰坦尼克号");
        list2.add("调音师");
        list2.add("海王");
        list2.add("大黄蜂");
        list2.add("心灵捕手");
        list2.add("这个杀手不太冷");
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

        TextView tvName;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnWrite = itemView.findViewById(R.id.btn_write);
            tvName = itemView.findViewById(R.id.tv_name);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            if (kind == 2) {
                tvStatus.setTypeface(Typeface.DEFAULT);
                tvStatus.setText("已完成");
                tvStatus.setTextSize(18);
                btnWrite.setVisibility(View.VISIBLE);
            }
        }

        public void setData(OrderBean bean, int i) {
            view.setTag(i);
            Glide.with(context).load(list.get(anInt % 6)).into(ivIcon);
            tvName.setText(list2.get(anInt % 6));
            anInt++;
        }
    }
}
