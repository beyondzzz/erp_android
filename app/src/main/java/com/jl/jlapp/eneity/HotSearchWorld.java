package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/25 0025.
 */

public class HotSearchWorld {


    /**
     * resultData : ["油啊","tyu","sdf","fghfg","米啊啊啊","fdgdf",null]
     * code : 200
     * msg : 请求成功
     */

    private int code;
    private String msg;
    private List<String> resultData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getResultData() {
        return resultData;
    }

    public void setResultData(List<String> resultData) {
        this.resultData = resultData;
    }
}
