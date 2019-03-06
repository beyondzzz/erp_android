package com.jl.jlapp.eneity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 柳亚婷 on 2018/6/13.
 */

public class WXOrderInfo {


    /**
     * resultData : {"sign":"97B4F3951AD4D26BA23F94351BCA5E04","timestamp":"1528874652","noncestr":"g3ri4rd32kbveva320dj8fe3dpm9icw9","partnerid":"1500059192","prepayid":"wx13152409686905fb0515069c1284864723","package":"Sign=WXPay","appid":"wx037454184aee70e9"}
     * code : 200
     * msg : 请求成功
     */

    private ResultDataBean resultData;
    private int code;
    private String msg;



    public ResultDataBean getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataBean resultData) {
        this.resultData = resultData;
    }

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

    public static class ResultDataBean {
        /**
         * sign : 97B4F3951AD4D26BA23F94351BCA5E04
         * timestamp : 1528874652
         * noncestr : g3ri4rd32kbveva320dj8fe3dpm9icw9
         * partnerid : 1500059192
         * prepayid : wx13152409686905fb0515069c1284864723
         * package : Sign=WXPay
         * appid : wx037454184aee70e9
         */

        private String sign;
        private String timestamp;
        private String noncestr;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String appid;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        @Override
        public String toString() {
            return "appid:"+appid+" partnerid:"+partnerid+" package:"+packageX+" noncestr:"+noncestr+" timestamp:"+timestamp+" prepayid:"+prepayid+" sign:"+sign;
        }
    }
}
