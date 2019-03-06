package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/29 0029.
 */

public class ShipAddressModel {

    /**
     * resultData : [{"id":4,"userId":22,"consigneeName":"aaaaaa","consigneeTel":"16898986868","region":"fdfg","detailedAddress":"fgd","isCommonlyUsed":0,"provinceCode":null,"cityCode":null,"countyCode":null,"ringCode":null,"operatorTime":1513751751000},{"id":5,"userId":22,"consigneeName":"rwr","consigneeTel":"1686868","region":"wer","detailedAddress":"rw","isCommonlyUsed":0,"provinceCode":null,"cityCode":null,"countyCode":null,"ringCode":null,"operatorTime":1513751816000},{"id":6,"userId":22,"consigneeName":"rwr","consigneeTel":"1686868","region":"wer","detailedAddress":"rw","isCommonlyUsed":0,"provinceCode":null,"cityCode":null,"countyCode":null,"ringCode":null,"operatorTime":1513757627000},{"id":7,"userId":22,"consigneeName":"rwr","consigneeTel":"1686868","region":"wer","detailedAddress":"rw","isCommonlyUsed":1,"provinceCode":"110000","cityCode":null,"countyCode":"110101","ringCode":"-1","operatorTime":1514369399000}]
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
         * id : 4
         * userId : 22
         * consigneeName : aaaaaa
         * consigneeTel : 16898986868
         * region : fdfg
         * detailedAddress : fgd
         * isCommonlyUsed : 0
         * provinceCode : null
         * cityCode : null
         * countyCode : null
         * ringCode : null
         * operatorTime : 1513751751000
         */

        private int id;
        private int userId;
        private String consigneeName;
        private String consigneeTel;
        private String region;
        private String detailedAddress;
        private int isCommonlyUsed;
        private String provinceCode;
        private String cityCode;
        private String countyCode;
        private String ringCode;
        private long operatorTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getConsigneeName() {
            return consigneeName;
        }

        public void setConsigneeName(String consigneeName) {
            this.consigneeName = consigneeName;
        }

        public String getConsigneeTel() {
            return consigneeTel;
        }

        public void setConsigneeTel(String consigneeTel) {
            this.consigneeTel = consigneeTel;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDetailedAddress() {
            return detailedAddress;
        }

        public void setDetailedAddress(String detailedAddress) {
            this.detailedAddress = detailedAddress;
        }

        public int getIsCommonlyUsed() {
            return isCommonlyUsed;
        }

        public void setIsCommonlyUsed(int isCommonlyUsed) {
            this.isCommonlyUsed = isCommonlyUsed;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCountyCode() {
            return countyCode;
        }

        public void setCountyCode(String countyCode) {
            this.countyCode = countyCode;
        }

        public String getRingCode() {
            return ringCode;
        }

        public void setRingCode(String ringCode) {
            this.ringCode = ringCode;
        }

        public long getOperatorTime() {
            return operatorTime;
        }

        public void setOperatorTime(long operatorTime) {
            this.operatorTime = operatorTime;
        }
    }
}
