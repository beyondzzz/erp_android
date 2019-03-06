package com.jl.jlapp.eneity;

/**
 * Created by THINK on 2018-02-01.
 */

public class LoginModel {

    /**
     * resultData : {"userPicUrl":null,"message":"登录成功","flag":1,"userId":35,"userPhone":"18860230196"}
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
         * userPicUrl : null
         * message : 登录成功
         * flag : 1
         * userId : 35
         * userPhone : 18860230196
         */

        private String userPicUrl;
        private String message;
        private int flag;
        private int userId;
        private String userPhone;
        private String userName;
        private int isVIP;

        public String getUserPicUrl() {
            return userPicUrl;
        }

        public void setUserPicUrl(String userPicUrl) {
            this.userPicUrl = userPicUrl;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getIsVIP() {
            return isVIP;
        }

        public void setIsVIP(int isVIP) {
            this.isVIP = isVIP;
        }
    }
}
