package com.jl.jlapp.eneity;

/**
 * Created by 柳亚婷 on 2018/7/2.
 */

public class PresellMsg {

    /**
     * msg : 请求成功
     * code : 200
     * resultData : {"id":74,"identifier":"JLACT20180702111120","name":"预售活动","activityType":5,"price":null,"discount":null,"maxNum":null,"introduction":"预售","messagePicUrl":"ActivityMessagePicture/208b723507354332894bb32c075a9e9e.jpg","showPicUrl":"ActivityShowPicture/a49d33d905c342a19fb704441591e511.jpg","budget":null,"beginValidityTime":1530460800000,"endValidityTime":1533052800000,"state":4,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1530501080000,"user":null,"goods":null,"goodsState":null,"coupon":null}
     */

    private String msg;
    private int code;
    private ResultDataBean resultData;

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

    public ResultDataBean getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataBean resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        /**
         * id : 74
         * identifier : JLACT20180702111120
         * name : 预售活动
         * activityType : 5
         * price : null
         * discount : null
         * maxNum : null
         * introduction : 预售
         * messagePicUrl : ActivityMessagePicture/208b723507354332894bb32c075a9e9e.jpg
         * showPicUrl : ActivityShowPicture/a49d33d905c342a19fb704441591e511.jpg
         * budget : null
         * beginValidityTime : 1530460800000
         * endValidityTime : 1533052800000
         * state : 4
         * operatorIdentifier : JLADMIN20171113192605
         * operatorTime : 1530501080000
         * user : null
         * goods : null
         * goodsState : null
         * coupon : null
         */

        private int id;
        private String identifier;
        private String name;
        private int activityType;
        private Object price;
        private Object discount;
        private Object maxNum;
        private String introduction;
        private String messagePicUrl;
        private String showPicUrl;
        private Object budget;
        private long beginValidityTime;
        private long endValidityTime;
        private int state;
        private String operatorIdentifier;
        private long operatorTime;
        private Object user;
        private Object goods;
        private Object goodsState;
        private Object coupon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public Object getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(Object maxNum) {
            this.maxNum = maxNum;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getMessagePicUrl() {
            return messagePicUrl;
        }

        public void setMessagePicUrl(String messagePicUrl) {
            this.messagePicUrl = messagePicUrl;
        }

        public String getShowPicUrl() {
            return showPicUrl;
        }

        public void setShowPicUrl(String showPicUrl) {
            this.showPicUrl = showPicUrl;
        }

        public Object getBudget() {
            return budget;
        }

        public void setBudget(Object budget) {
            this.budget = budget;
        }

        public long getBeginValidityTime() {
            return beginValidityTime;
        }

        public void setBeginValidityTime(long beginValidityTime) {
            this.beginValidityTime = beginValidityTime;
        }

        public long getEndValidityTime() {
            return endValidityTime;
        }

        public void setEndValidityTime(long endValidityTime) {
            this.endValidityTime = endValidityTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getOperatorIdentifier() {
            return operatorIdentifier;
        }

        public void setOperatorIdentifier(String operatorIdentifier) {
            this.operatorIdentifier = operatorIdentifier;
        }

        public long getOperatorTime() {
            return operatorTime;
        }

        public void setOperatorTime(long operatorTime) {
            this.operatorTime = operatorTime;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }

        public Object getGoods() {
            return goods;
        }

        public void setGoods(Object goods) {
            this.goods = goods;
        }

        public Object getGoodsState() {
            return goodsState;
        }

        public void setGoodsState(Object goodsState) {
            this.goodsState = goodsState;
        }

        public Object getCoupon() {
            return coupon;
        }

        public void setCoupon(Object coupon) {
            this.coupon = coupon;
        }
    }
}
