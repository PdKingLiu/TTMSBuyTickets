package com.competition.pdking.theaterbusiness.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author liupeidong
 * Created on 2019/6/11 17:08
 */
public class QueryUserBean {

    @SerializedName("total")
    public int total;
    @SerializedName("rows")
    public List<RowsBean> rows;

    public static class RowsBean {
        @SerializedName("u_name")
        public String uName;
        @SerializedName("u_id")
        public int uId;
        @SerializedName("u_email")
        public String uEmail;
        @SerializedName("u_introduce")
        public String uIntroduce;
        @SerializedName("u_activeCode")
        public String uActiveCode;
        @SerializedName("u_role")
        public String uRole;
        @SerializedName("u_state")
        public String uState;
        @SerializedName("u_age")
        public String uAge;
        @SerializedName("u_password")
        public String uPassword;
        @SerializedName("u_regDate")
        public String uRegDate;
        @SerializedName("u_tel")
        public String uTel;
    }
}
