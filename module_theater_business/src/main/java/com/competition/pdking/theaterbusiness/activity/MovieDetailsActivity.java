package com.competition.pdking.theaterbusiness.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.common.Constant;
import com.competition.pdking.common.utils.SystemUtil;
import com.competition.pdking.common.weight.TitleView;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.adapter.MovieListAdapter;
import com.competition.pdking.theaterbusiness.adapter.SessionListAdapter;
import com.competition.pdking.theaterbusiness.bean.QueryMoiveListBean;
import com.competition.pdking.theaterbusiness.bean.SessionBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView ivBackground;
    private ImageView ivIcon;
    private QueryMoiveListBean.RowsBean bean;
    private TitleView title;
    private TextView tvName;
    private TextView tvPoint;
    private TextView tvKind;
    private TextView tvPrice;

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private SessionListAdapter adapter;
    private ArrayList<SessionBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        SystemUtil.setTitleMode(getWindow());
        bean = (QueryMoiveListBean.RowsBean) getIntent().getBundleExtra("user").getSerializable(
                "user");
        initView();
        initRecyclerAndRefresh();
        Glide.with(this).load(zoomImg())
                .apply(bitmapTransform(new BlurTransformation(15, 3)))
                .into(ivBackground);
    }

    private void initRecyclerAndRefresh() {
        list = new ArrayList<>();
        list.add(new SessionBean());
        list.add(new SessionBean());
        list.add(new SessionBean());
        list.add(new SessionBean());
        list.add(new SessionBean());
        adapter = new SessionListAdapter(this, list);
        adapter.setListener((view, i) -> {
            Intent intent = new Intent(MovieDetailsActivity.this, ChooseTicketActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", bean);
            intent.putExtra("user", bundle);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.finishLoadMoreWithNoMoreData());
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.finishRefresh(true));
    }

    public Bitmap zoomImg() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.room_background);
        float newHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f,
                getResources().getDisplayMetrics()) + 2;
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int newWidth = outMetrics.widthPixels;
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();
        return newBitmap;
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
                refreshLayout.finishRefresh(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                try {
                    list.clear();
                    refreshLayout.finishRefresh(true);
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                } catch (Exception e) {
                    refreshLayout.finishRefresh(false);
                }
            }
        });
    }


    private void initView() {
        recyclerView = findViewById(R.id.rv_session_list);
        refreshLayout = findViewById(R.id.srl_flush);
        ivIcon = findViewById(R.id.iv_icon);
        if (!bean.playImage.equals("")) {
            Glide.with(this).load(bean.playImage).into(ivIcon);
        }
        tvName = findViewById(R.id.tv_name);
        tvName.setText(bean.playName);
        tvPoint = findViewById(R.id.tv_point);
        Random random = new Random();
        float point = random.nextFloat();
        point = 7 + point * 3;
        tvPoint.setText(String.format("%.1f分", point));
        tvKind = findViewById(R.id.tv_kind);
        String kind = "";
        if (bean.playLangId == 1) {
            kind = "中文";
        } else if (bean.playLangId == 2) {
            kind = "英语";
        } else {
            kind = "其他语言";
        }
        switch (bean.playTypeId) {
            case 1:
                kind = kind + "," + "科幻";
                break;
            case 2:
                kind = kind + "," + "喜剧";
                break;
            case 3:
                kind = kind + "," + "冒险";
                break;
            case 4:
                kind = kind + "," + "";
                tvKind.setText("幻想");
                break;
            case 5:
                kind = kind + "," + "悬念";
                break;
            case 6:
                kind = kind + "," + "惊悚";
                break;
            case 7:
                kind = kind + "," + "记录";
                break;
            case 8:
                kind = kind + "," + "战争";
                break;
            case 9:
                kind = kind + "," + "西部";
                break;
            case 10:
                kind = kind + "," + "爱情";
                break;
            case 11:
                kind = kind + "," + "剧情";
                break;
            case 12:
                kind = kind + "," + "恐怖";
                break;
            case 13:
                kind = kind + "," + "动作";
                break;
            case 14:
                kind = kind + "," + "音乐";
                break;
            case 15:
                kind = kind + "," + "家庭";
                break;
            case 16:
                kind = kind + "," + "其它类型";
                break;
        }
        tvKind.setText(kind);
        tvPrice = findViewById(R.id.tv_price);
        String price = "";
        price = bean.playTicketPrice + "元/" + bean.playLength + "分钟";
        tvPrice.setText(price);
        ivBackground = findViewById(R.id.ll_background);
        title = findViewById(R.id.title);
        title.setTitleText(bean.playName);
        title.setLeftClickListener(() -> finish());
    }

}
