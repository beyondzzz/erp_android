package com.jl.jlapp.eneity;

/**
 * Created by 柳亚婷 on 2018/2/4 0004.
 */

public class PostageModel {


    /**
     * msg : 请求成功
     * code : 200
     * resultData : 10
     */

    private String msg;
    private int code;
    private double resultData;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getResultData() {
        return resultData;
    }

    public void setResultData(double resultData) {
        this.resultData = resultData;
    }
}
