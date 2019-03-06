package com.jl.jlapp.eneity;

/**
 * Created by 柳亚婷 on 2018/3/1 0001.
 */

public class MessageNoReadNumModel {


    /**
     * resultData : {"orderNO":"JLORDER20180301144917","UserMessageTime":null,"ActivityTime":1518537602000,"UserMessageNum":0,"orderNum":26,"orderTime":1519886958000,"ActivityName":"金狗迎春福气到 财源滚滚好事来","ActivityNum":15}
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
         * orderNO : JLORDER20180301144917
         * UserMessageTime : null
         * ActivityTime : 1518537602000
         * UserMessageNum : 0
         * orderNum : 26
         * orderTime : 1519886958000
         * ActivityName : 金狗迎春福气到 财源滚滚好事来
         * ActivityNum : 15
         */

        private String orderNO;
        private long UserMessageTime;
        private long ActivityTime;
        private int UserMessageNum;
        private int orderNum;
        private long orderTime;
        private String ActivityName;
        private int ActivityNum;

        public String getOrderNO() {
            return orderNO;
        }

        public void setOrderNO(String orderNO) {
            this.orderNO = orderNO;
        }

        public long getUserMessageTime() {
            return UserMessageTime;
        }

        public void setUserMessageTime(long UserMessageTime) {
            this.UserMessageTime = UserMessageTime;
        }

        public long getActivityTime() {
            return ActivityTime;
        }

        public void setActivityTime(long ActivityTime) {
            this.ActivityTime = ActivityTime;
        }

        public int getUserMessageNum() {
            return UserMessageNum;
        }

        public void setUserMessageNum(int UserMessageNum) {
            this.UserMessageNum = UserMessageNum;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(long orderTime) {
            this.orderTime = orderTime;
        }

        public String getActivityName() {
            return ActivityName;
        }

        public void setActivityName(String ActivityName) {
            this.ActivityName = ActivityName;
        }

        public int getActivityNum() {
            return ActivityNum;
        }

        public void setActivityNum(int ActivityNum) {
            this.ActivityNum = ActivityNum;
        }
    }
}
