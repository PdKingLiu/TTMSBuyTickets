package com.competition.pdking.theaterbusiness.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private ArrayList<String> list1 = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    private ArrayList<String> list3 = new ArrayList<>();
    private ArrayList<String> list4 = new ArrayList<>();

    private static int anInt = 0;
    private static int anInt2 = 0;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SessionListAdapter(Context context, ArrayList<SessionBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        list1.add("9:00");
        list1.add("13:00");
        list1.add("15:00");
        list1.add("18:00");
        list1.add("20:00");
        list1.add("22:00");
        list1.add("0:00");

        list2.add("11:00");
        list2.add("15:00");
        list2.add("17:00");
        list2.add("20:00");
        list2.add("22:00");
        list2.add("0:00");
        list2.add("2:00");

        list3.add("1号3D剧目厅");
        list3.add("2号3D剧目厅");
        list3.add("3号3D剧目厅");
        list3.add("4号3D剧目厅");
        list3.add("5号3D剧目厅");
        list3.add("6号3D剧目厅");
        list3.add("7号3D剧目厅");

        list4.add("25");
        list4.add("27");
        list4.add("32");

        anInt2++;
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
        TextView tvStart;
        TextView tvEnd;
        TextView tvPlace;

        TextView tvPrice;

        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvStart = itemView.findViewById(R.id.tv_start_time);
            tvEnd = itemView.findViewById(R.id.tv_end_time);
            tvPlace = itemView.findViewById(R.id.tv_place);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStart.setText(list1.get(anInt % 7));
            tvEnd.setText(list2.get(anInt % 7) + "散场");
            tvPlace.setText(list3.get(anInt % 7));
            tvPrice.setText(list4.get(anInt2 % 3));
            anInt++;
        }

        public void setData(SessionBean sessionBean, int i) {
            view.setTag(i);
        }
    }


}
