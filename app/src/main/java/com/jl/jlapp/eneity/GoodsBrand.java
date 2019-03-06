package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/6/30.
 */

public class GoodsBrand {

    /**
     * msg : 请求成功
     * code : 200
     * resultData : ["大连","山东","测试"]
     */

    private String msg;
    private int code;
    private List<String> resultData;

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

    public List<String> getResultData() {
        return resultData;
    }

    public void setResultData(List<String> resultData) {
        this.resultData = resultData;
    }
}
