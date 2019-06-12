package com.competition.pdking.theaterbusiness.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.bean.QueryMoiveListBean;

import java.util.ArrayList;

/**
 * @author liupeidong
 * Created on 2019/6/12 15:53
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<QueryMoiveListBean.RowsBean> arrayList;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MovieListAdapter(Context context, ArrayList<QueryMoiveListBean.RowsBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_list,
                viewGroup, false);
        view.setTag(i);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(arrayList.get(i));
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        ImageView ivRed;
        TextView tvName;
        TextView tvTime;
        TextView tvPrice;
        TextView tvLanguage;
        TextView tvRed;
        TextView tvNoRed;
        TextView tvKind;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_movie);
            ivRed = itemView.findViewById(R.id.iv_red);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvLanguage = itemView.findViewById(R.id.tv_language);
            tvRed = itemView.findViewById(R.id.tv_red);
            tvNoRed = itemView.findViewById(R.id.tv_no_red);
            tvKind = itemView.findViewById(R.id.tv_kind);
        }

        public void setData(QueryMoiveListBean.RowsBean rowsBean) {
            if (!rowsBean.playImage.equals("")) {
                Glide.with(context).load(rowsBean.playImage).into(ivIcon);
            }
            if (!rowsBean.playStatus.equals("")) {
                if (Integer.parseInt(rowsBean.playStatus) == -1) {
                    tvNoRed.setVisibility(View.VISIBLE);
                    tvRed.setVisibility(View.GONE);
                    ivRed.setVisibility(View.GONE);
                } else {
                    tvNoRed.setVisibility(View.GONE);
                    tvRed.setVisibility(View.VISIBLE);
                    ivRed.setVisibility(View.VISIBLE);
                }
            }
            tvName.setText(rowsBean.playName);
            tvTime.setText(rowsBean.playLength + " 分钟");
            tvPrice.setText(rowsBean.playTicketPrice + " 元");
            if (rowsBean.playLangId == 1) {
                tvLanguage.setText("中文");
            } else if (rowsBean.playLangId == 2) {
                tvLanguage.setText("英语");
            } else {
                tvLanguage.setText("其他语言");
            }
            switch (rowsBean.playTypeId) {
                case 1:
                    tvKind.setText("科幻");
                    break;
                case 2:
                    tvKind.setText("喜剧");
                    break;
                case 3:
                    tvKind.setText("冒险");
                    break;
                case 4:
                    tvKind.setText("幻想");
                    break;
                case 5:
                    tvKind.setText("悬念");
                    break;
                case 6:
                    tvKind.setText("惊悚");
                    break;
                case 7:
                    tvKind.setText("记录");
                    break;
                case 8:
                    tvKind.setText("战争");
                    break;
                case 9:
                    tvKind.setText("西部");
                    break;
                case 10:
                    tvKind.setText("爱情");
                    break;
                case 11:
                    tvKind.setText("剧情");
                    break;
                case 12:
                    tvKind.setText("恐怖");
                    break;
                case 13:
                    tvKind.setText("动作");
                    break;
                case 14:
                    tvKind.setText("音乐");
                    break;
                case 15:
                    tvKind.setText("家庭");
                    break;
                case 16:
                    tvKind.setText("其它类型");
                    break;
            }
        }
    }
}
