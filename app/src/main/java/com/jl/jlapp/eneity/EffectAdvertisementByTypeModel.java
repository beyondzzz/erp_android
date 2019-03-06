package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by THINK on 2018-01-26.
 */

public class EffectAdvertisementByTypeModel {

    /**
     * resultData : [{"id":271,"identifier":"JLAD20180112174630","type":4,"name":"火热促销","picUrl":"advertisementInformationPicture/1515555943379.png","urlType":null,"urlParameterId":null,"effectTime":null,"effect":0,"operatorIdentifier":null,"operatorTime":1515750400000,"user":null,"goodsDetails":null,"activityInformation":null},{"id":272,"identifier":"JLAD20180112174631","type":4,"name":"新品上架","picUrl":"advertisementInformationPicture/1515555707803.png","urlType":null,"urlParameterId":null,"effectTime":null,"effect":0,"operatorIdentifier":null,"operatorTime":1515750400000,"user":null,"goodsDetails":null,"activityInformation":null},{"id":273,"identifier":"JLAD20180112174632","type":4,"name":"食讯帮热卖","picUrl":"advertisementInformationPicture/1515555734479.png","urlType":null,"urlParameterId":null,"effectTime":null,"effect":0,"operatorIdentifier":null,"operatorTime":1515750400000,"user":null,"goodsDetails":null,"activityInformation":null},{"id":274,"identifier":"JLAD20180112174633","type":4,"name":"爆品预售","picUrl":"advertisementInformationPicture/1515555716211.png","urlType":null,"urlParameterId":null,"effectTime":null,"effect":0,"operatorIdentifier":null,"operatorTime":1515750400000,"user":null,"goodsDetails":null,"activityInformation":null}]
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
         * id : 271
         * identifier : JLAD20180112174630
         * type : 4
         * name : 火热促销
         * picUrl : advertisementInformationPicture/1515555943379.png
         * urlType : null
         * urlParameterId : null
         * effectTime : null
         * effect : 0
         * operatorIdentifier : null
         * operatorTime : 1515750400000
         * user : null
         * goodsDetails : null
         * activityInformation : null
         */

        private int id;
        private String identifier;
        private int type;
        private String name;
        private String picUrl;
        private Object urlType;
        private Object urlParameterId;
        private Object effectTime;
        private int effect;
        private Object operatorIdentifier;
        private long operatorTime;
        private Object user;
        private Object goodsDetails;
        private Object activityInformation;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public Object getUrlType() {
            return urlType;
        }

        public void setUrlType(Object urlType) {
            this.urlType = urlType;
        }

        public Object getUrlParameterId() {
            return urlParameterId;
        }

        public void setUrlParameterId(Object urlParameterId) {
            this.urlParameterId = urlParameterId;
        }

        public Object getEffectTime() {
            return effectTime;
        }

        public void setEffectTime(Object effectTime) {
            this.effectTime = effectTime;
        }

        public int getEffect() {
            return effect;
        }

        public void setEffect(int effect) {
            this.effect = effect;
        }

        public Object getOperatorIdentifier() {
            return operatorIdentifier;
        }

        public void setOperatorIdentifier(Object operatorIdentifier) {
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

        public Object getGoodsDetails() {
            return goodsDetails;
        }

        public void setGoodsDetails(Object goodsDetails) {
            this.goodsDetails = goodsDetails;
        }

        public Object getActivityInformation() {
            return activityInformation;
        }

        public void setActivityInformation(Object activityInformation) {
            this.activityInformation = activityInformation;
        }
    }
}
