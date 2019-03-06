package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by THINK on 2018-01-29.
 */

public class News_ListModel {
    /**
     * resultData : [{"goodsDetails":null,"picUrl":"TwoClassificationPicture/1517205275879.png","sort":null,"twoClassifications":null,"keyWord":"生鲜","id":68,"parentId":null,"name":"生鲜","operatorIdentifier":null,"user":null,"identifier":null,"operatorTime":null,"parentName":null},{"goodsDetails":null,"picUrl":null,"sort":null,"twoClassifications":null,"keyWord":"油","id":26,"parentId":null,"name":"粮油","operatorIdentifier":null,"user":null,"identifier":null,"operatorTime":null,"parentName":null},{"goodsDetails":null,"picUrl":null,"sort":null,"twoClassifications":null,"keyWord":"发给","id":29,"parentId":null,"name":"法规和法国","operatorIdentifier":null,"user":null,"identifier":null,"operatorTime":null,"parentName":null},{"goodsDetails":null,"picUrl":null,"sort":null,"twoClassifications":null,"keyWord":"沃尔沃","id":30,"parentId":null,"name":"werwe","operatorIdentifier":null,"user":null,"identifier":null,"operatorTime":null,"parentName":null}]
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
         * goodsDetails : null
         * picUrl : TwoClassificationPicture/1517205275879.png
         * sort : null
         * twoClassifications : null
         * keyWord : 生鲜
         * id : 68
         * parentId : null
         * name : 生鲜
         * operatorIdentifier : null
         * user : null
         * identifier : null
         * operatorTime : null
         * parentName : null
         */

        private Object goodsDetails;
        private String picUrl;
        private Object sort;
        private Object twoClassifications;
        private String keyWord;
        private int id;
        private Object parentId;
        private String name;
        private Object operatorIdentifier;
        private Object user;
        private Object identifier;
        private Object operatorTime;
        private Object parentName;

        public Object getGoodsDetails() {
            return goodsDetails;
        }

        public void setGoodsDetails(Object goodsDetails) {
            this.goodsDetails = goodsDetails;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getTwoClassifications() {
            return twoClassifications;
        }

        public void setTwoClassifications(Object twoClassifications) {
            this.twoClassifications = twoClassifications;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getOperatorIdentifier() {
            return operatorIdentifier;
        }

        public void setOperatorIdentifier(Object operatorIdentifier) {
            this.operatorIdentifier = operatorIdentifier;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }

        public Object getIdentifier() {
            return identifier;
        }

        public void setIdentifier(Object identifier) {
            this.identifier = identifier;
        }

        public Object getOperatorTime() {
            return operatorTime;
        }

        public void setOperatorTime(Object operatorTime) {
            this.operatorTime = operatorTime;
        }

        public Object getParentName() {
            return parentName;
        }

        public void setParentName(Object parentName) {
            this.parentName = parentName;
        }
    }
}
