package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by THINK on 2018-03-02.
 */

public class AllAvailableCouponModel {
    /**
     * resultData : [{"id":22,"identifier":"JLCOU20180207104928","name":"食品粮油优惠卷","price":15,"total":100,"useLimit":100,"count":98,"state":0,"rules":1,"beginValidityTime":1517932800000,"endValidityTime":1524844800000,"beginTime":1517932800000,"endTime":1521648000000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517971768000,"user":null}]
     * code : 200
     * msg : 请求成功
     */

    private int code;
    private String msg;
    private List<ResultDataBean> resultData;

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

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        /**
         * id : 22
         * identifier : JLCOU20180207104928
         * name : 食品粮油优惠卷
         * price : 15
         * total : 100
         * useLimit : 100
         * count : 98
         * state : 0
         * rules : 1
         * beginValidityTime : 1517932800000
         * endValidityTime : 1524844800000
         * beginTime : 1517932800000
         * endTime : 1521648000000
         * operatorIdentifier : JLADMIN20171113192605
         * operatorTime : 1517971768000
         * user : null
         */

        private int id;
        private String identifier;
        private String name;
        private double price;
        private int total;
        private double useLimit;
        private int count;
        private int state;
        private int rules;
        private long beginValidityTime;
        private long endValidityTime;
        private long beginTime;
        private long endTime;
        private String operatorIdentifier;
        private long operatorTime;
        private Object user;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public double getUseLimit() {
            return useLimit;
        }

        public void setUseLimit(double useLimit) {
            this.useLimit = useLimit;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getRules() {
            return rules;
        }

        public void setRules(int rules) {
            this.rules = rules;
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

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
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
    }
}
