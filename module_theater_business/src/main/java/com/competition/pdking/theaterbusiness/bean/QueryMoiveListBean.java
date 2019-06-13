package com.competition.pdking.theaterbusiness.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author liupeidong
 * Created on 2019/6/12 16:21
 */
public class QueryMoiveListBean {
    @SerializedName("total")
    public int total;
    @SerializedName("rows")
    public List<RowsBean> rows;

    public static class RowsBean  implements Serializable {
        @SerializedName("play_id")
        public int playId;
        @SerializedName("play_name")
        public String playName;
        @SerializedName("play_introduction")
        public String playIntroduction;
        @SerializedName("play_type_id")
        public int playTypeId;
        @SerializedName("play_lang_id")
        public int playLangId;
        @SerializedName("play_length")
        public int playLength;
        @SerializedName("play_ticket_price")
        public float playTicketPrice;
        @SerializedName("play_image")
        public String playImage;
        @SerializedName("play_status")
        public String playStatus;
    }
}
