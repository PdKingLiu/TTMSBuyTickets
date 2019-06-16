package com.competition.pdking.theaterbusiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.competition.pdking.common.utils.SystemUtil;
import com.competition.pdking.common.weight.TitleView;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.bean.QueryMoiveListBean;
import com.competition.pdking.theaterbusiness.weight.SeatTable;

import java.util.ArrayList;

public class ChooseTicketActivity extends AppCompatActivity {

    private SeatTable seatTable;
    private TitleView title;
    private Button btnBuy;
    private int count = 0;
    private ProgressDialog dialog;
    private QueryMoiveListBean.RowsBean bean;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);
        SystemUtil.setTitleMode(getWindow());
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在订购...");
        dialog.setTitle("订购中");
        dialog.setCancelable(false);
        initView();
        initSeat();
    }

    private void initSeat() {
        seatTable.setScreenName("8号厅荧幕");
        seatTable.setMaxSelected(10);
        seatTable.setData(10, 15);
        ArrayList<String> list = new ArrayList<>();
        seatTable.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                for (int i = 0; i < list.size(); i++) {
                    String[] strings = list.get(i).split("_");
                    if (row == Integer.parseInt(strings[0]) && column == Integer.parseInt(strings[1])) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                count++;
            }

            @Override
            public void unCheck(int row, int column) {
                count--;
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
    }

    private void initView() {
        bean = (QueryMoiveListBean.RowsBean) getIntent().getBundleExtra("user").getSerializable(
                "user");
        seatTable = findViewById(R.id.st_seat);
        title = findViewById(R.id.title);
        btnBuy = findViewById(R.id.btn_buy);
        tvName = findViewById(R.id.tv_name);
        title.setLeftClickListener(() -> finish());
        title.setTitleText(bean.playName);
        tvName.setText(bean.playName);
        btnBuy.setOnClickListener(v -> {
            showProgressBar();
            new Handler().postDelayed(() -> {
                hideProgressBar();
                startActivity(new Intent(ChooseTicketActivity.this, BuyResultActivity.class));
            }, 3000);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.hide();
            }
            dialog.dismiss();
        }
    }

    private void hideProgressBar() {
        runOnUiThread(() -> {
            if (dialog != null) {
                dialog.hide();
            }
        });
    }

    private void showProgressBar() {
        runOnUiThread(() -> {
            if (dialog != null) {
                dialog.show();
            }
        });
    }

}
