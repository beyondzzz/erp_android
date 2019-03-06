package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/2/3 0003.
 */

public class InvoiceUnitAndVatInvoiceAptitude {


    /**
     * resultData : {"invoiceUnit":[{"id":3,"unitName":"小蚂蚁网络科技有限公司4","taxpayerIdentificationNumber":"1234567894","userId":22,"operatorTime":1513244262000}],"vatInvoiceAptitude":{"id":1,"identifier":"123456","unitName":"小蚂蚁网络科技有限公司","taxpayerIdentificationNumber":"123456789","registeredAddress":"河南洛阳","registeredTel":"67574123","depositBank":"中国银行","bankAccount":"6666000055551987456","businessLicenseUrl":"advertisementInformationPicture/2221512382496534.png","state":2,"userId":22,"operatorTime":1512376655000,"user":null}}
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
         * invoiceUnit : [{"id":3,"unitName":"小蚂蚁网络科技有限公司4","taxpayerIdentificationNumber":"1234567894","userId":22,"operatorTime":1513244262000}]
         * vatInvoiceAptitude : {"id":1,"identifier":"123456","unitName":"小蚂蚁网络科技有限公司","taxpayerIdentificationNumber":"123456789","registeredAddress":"河南洛阳","registeredTel":"67574123","depositBank":"中国银行","bankAccount":"6666000055551987456","businessLicenseUrl":"advertisementInformationPicture/2221512382496534.png","state":2,"userId":22,"operatorTime":1512376655000,"user":null}
         */

        private VatInvoiceAptitudeBean vatInvoiceAptitude;
        private List<InvoiceUnitBean> invoiceUnit;

        public VatInvoiceAptitudeBean getVatInvoiceAptitude() {
            return vatInvoiceAptitude;
        }

        public void setVatInvoiceAptitude(VatInvoiceAptitudeBean vatInvoiceAptitude) {
            this.vatInvoiceAptitude = vatInvoiceAptitude;
        }

        public List<InvoiceUnitBean> getInvoiceUnit() {
            return invoiceUnit;
        }

        public void setInvoiceUnit(List<InvoiceUnitBean> invoiceUnit) {
            this.invoiceUnit = invoiceUnit;
        }

        public static class VatInvoiceAptitudeBean {
            /**
             * id : 1
             * identifier : 123456
             * unitName : 小蚂蚁网络科技有限公司
             * taxpayerIdentificationNumber : 123456789
             * registeredAddress : 河南洛阳
             * registeredTel : 67574123
             * depositBank : 中国银行
             * bankAccount : 6666000055551987456
             * businessLicenseUrl : advertisementInformationPicture/2221512382496534.png
             * state : 2
             * userId : 22
             * operatorTime : 1512376655000
             * user : null
             */

            private int id;
            private String identifier;
            private String unitName;
            private String taxpayerIdentificationNumber;
            private String registeredAddress;
            private String registeredTel;
            private String depositBank;
            private String bankAccount;
            private String businessLicenseUrl;
            private int state;
            private int userId;
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

            public String getRegisteredAddress() {
                return registeredAddress;
            }

            public void setRegisteredAddress(String registeredAddress) {
                this.registeredAddress = registeredAddress;
            }

            public String getRegisteredTel() {
                return registeredTel;
            }

            public void setRegisteredTel(String registeredTel) {
                this.registeredTel = registeredTel;
            }

            public String getDepositBank() {
                return depositBank;
            }

            public void setDepositBank(String depositBank) {
                this.depositBank = depositBank;
            }

            public String getBankAccount() {
                return bankAccount;
            }

            public void setBankAccount(String bankAccount) {
                this.bankAccount = bankAccount;
            }

            public String getBusinessLicenseUrl() {
                return businessLicenseUrl;
            }

            public void setBusinessLicenseUrl(String businessLicenseUrl) {
                this.businessLicenseUrl = businessLicenseUrl;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
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

            public Object getUser() {
                return user;
            }

            public void setUser(Object user) {
                this.user = user;
            }
        }

        public static class InvoiceUnitBean {
            /**
             * id : 3
             * unitName : 小蚂蚁网络科技有限公司4
             * taxpayerIdentificationNumber : 1234567894
             * userId : 22
             * operatorTime : 1513244262000
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
}
