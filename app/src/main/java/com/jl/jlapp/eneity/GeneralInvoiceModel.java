package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/2/28 0028.
 */

public class GeneralInvoiceModel {


    /**
     * resultData : [{"id":5,"unitName":"洛阳AAAAAAAA","taxpayerIdentificationNumber":"XXXXXXXXX","userId":22,"operatorTime":1517658078000},{"id":6,"unitName":"啊啊啊啊啊啊","taxpayerIdentificationNumber":"SSSSSSSSS","userId":22,"operatorTime":1517658352000}]
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
         * id : 5
         * unitName : 洛阳AAAAAAAA
         * taxpayerIdentificationNumber : XXXXXXXXX
         * userId : 22
         * operatorTime : 1517658078000
         */

        private int id;
        private String unitName;
        private String taxpayerIdentificationNumber;
        private int userId;
        private long operatorTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getTaxpayerIdentificationNumber() {
            return taxpayerIdentificationNumber;
        }

        public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
            this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getOperatorTime() {
            return operatorTime;
        }

        public void setOperatorTime(long operatorTime) {
            this.operatorTime = operatorTime;
        }
    }
}
