package com.jl.jlapp.eneity;

/**
 * Created by 柳亚婷 on 2018/3/14 0014.
 */

public class VersionMessageModel {


    /**
     * msg : 请求成功
     * code : 200
     * resultData : {"id":1,"versionCode":1,"versionName":"1.2.0","description":null,"isAndroidOriOS":0,"isMustUpdate":1,"updateTime":1521449215000}
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
         * id : 1
         * versionCode : 1
         * versionName : 1.2.0
         * description : null
         * isAndroidOriOS : 0
         * isMustUpdate : 1
         * updateTime : 1521449215000
         */

        private int id;
        private int versionCode;
        private String versionName;
        private String description;
        private int isAndroidOriOS;
        private int isMustUpdate;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIsAndroidOriOS() {
            return isAndroidOriOS;
        }

        public void setIsAndroidOriOS(int isAndroidOriOS) {
            this.isAndroidOriOS = isAndroidOriOS;
        }

        public int getIsMustUpdate() {
            return isMustUpdate;
        }

        public void setIsMustUpdate(int isMustUpdate) {
            this.isMustUpdate = isMustUpdate;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
