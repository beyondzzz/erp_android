package com.jl.jlapp.eneity;

/**
 * Created by THINK on 2018-01-31.
 */

public class MessageCodeModel {
    /**
     * resultData : {"FailureTime":1517385909875,"MessageCode":"377660"}
     * msg : 短信发送成功
     * code : 200
     */

    private ResultDataBean resultData;
    private String msg;
    private int code;

    public ResultDataBean getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataBean resultData) {
        this.resultData = resultData;
    }

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

    public static class ResultDataBean {
        /**
         * FailureTime : 1517385909875
         * MessageCode : 377660
         */

        private long FailureTime;
        private String MessageCode;

        public long getFailureTime() {
            return FailureTime;
        }

        public void setFailureTime(long FailureTime) {
            this.FailureTime = FailureTime;
        }

        public String getMessageCode() {
            return MessageCode;
        }

        public void setMessageCode(String MessageCode) {
            this.MessageCode = MessageCode;
        }
    }
}
