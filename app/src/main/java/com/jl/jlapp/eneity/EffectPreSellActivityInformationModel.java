package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 景雅倩 on 2018/6/29.
 */

public class EffectPreSellActivityInformationModel {

    /**
     * msg : 请求成功
     * code : 200
     * resultData : [{"id":75,"identifier":"JLACT20180629110751","name":"测试预售自动上线","activityType":5,"price":null,"discount":null,"maxNum":null,"introduction":"这是测试","messagePicUrl":"ActivityMessagePicture/a21ec057c9034336986e29c4e94c1ac5.jpg","showPicUrl":"ActivityShowPicture/3e521db53bdc43698fbd77aa44ec751c.jpg","budget":null,"beginValidityTime":1530201600000,"endValidityTime":1530288000000,"state":4,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1530241671000,"user":null,"goods":null,"goodsState":null,"coupon":null}]
     */

    private String msg;
    private int code;
    private List<ResultDataBean> resultData;

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

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        /**
         * id : 75
         * identifier : JLACT20180629110751
         * name : 测试预售自动上线
         * activityType : 5
         * price : null
         * discount : null
         * maxNum : null
         * introduction : 这是测试
         * messagePicUrl : ActivityMessagePicture/a21ec057c9034336986e29c4e94c1ac5.jpg
         * showPicUrl : ActivityShowPicture/3e521db53bdc43698fbd77aa44ec751c.jpg
         * budget : null
         * beginValidityTime : 1530201600000
         * endValidityTime : 1530288000000
         * state : 4
         * operatorIdentifier : JLADMIN20171113192605
         * operatorTime : 1530241671000
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
