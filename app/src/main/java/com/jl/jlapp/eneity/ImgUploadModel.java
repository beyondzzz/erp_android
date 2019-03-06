package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by THINK on 2018-02-02.
 */

public class ImgUploadModel {

    /**
     * resultData : ["uploadImages/1517565162972.png","uploadImages/1517565162975.png"]
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
