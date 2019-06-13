package com.competition.pdking.theaterbusiness.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.competition.pdking.common.utils.SystemUtil;
import com.competition.pdking.common.weight.TitleView;
import com.competition.pdking.theaterbusiness.R;
import com.competition.pdking.theaterbusiness.weight.SeatTable;

import java.util.ArrayList;

public class ChooseTicketActivity extends AppCompatActivity {

    private SeatTable seatTable;
    private TitleView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);
        SystemUtil.setTitleMode(getWindow());
        initView();
        initSeat();
    }

    private void initSeat() {
        seatTable.setScreenName("8号厅荧幕");
        seatTable.setMaxSelected(10);
        seatTable.setData(10, 15);
        ArrayList<String> list = new ArrayList<>();
        list.add("1_2");
        list.add("2_3");
        list.add("3_4");
        list.add("4_5");
        list.add("5_6");
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

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
    }

    private void initView() {
        seatTable = findViewById(R.id.st_seat);
        title = findViewById(R.id.title);
        title.setLeftClickListener(() -> finish());
    }
}
