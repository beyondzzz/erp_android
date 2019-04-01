package com.jl.jlapp.eneity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THINK on 2018-02-01.
 */

public class OrderDetail {

    /**
     * resultData : {"id":30,"orderNo":"JLORDER20180103154622","orderOriginalPrice":345,"orderPresentPrice":280,"userId":18,"placeOrderTime":1514965583000,"orderState":6,"consigneeName":"XXX","consigneeTel":"18865656565","consigneeAddress":"XXXX","payType":1,"payTime":null,"postage":5,"postagePayType":0,"deliverGoodsTime":1513958400000,"payMode":null,"preferentialType":null,"carIdSend":null,"carIdChangeReturn":null,"carIdChangeSend":null,"orderGoodsName":null,"orderGoodsNum":null,"orderGoodsPrice":null,"orderDetails":[{"id":45,"goodsDetailsId":null,"goodsQuantity":3,"goodsSpecificationDetailsId":5,"goodsPurchasingPrice":35,"goodsOriginalPrice":85,"goodsPaymentPrice":200,"goodsName":"XXX","goodsCoverUrl":"GoodsDisplayPicture/1516956283213.png","goodsSpecificationName":"SDFSDFSD","orderId":null,"goodsDetails":null,"goodsEvaluation":null,"orderTable":null},{"id":46,"goodsDetailsId":null,"goodsQuantity":2,"goodsSpecificationDetailsId":8,"goodsPurchasingPrice":20,"goodsOriginalPrice":45,"goodsPaymentPrice":80,"goodsName":"rter","goodsCoverUrl":"GoodsDisplayPicture/1517205386608.png","goodsSpecificationName":"erytyui","orderId":null,"goodsDetails":null,"goodsEvaluation":null,"orderTable":null}],"user":{"id":18,"identifier":null,"loginName":null,"name":"13101793442","password":null,"phone":"13101793442","userGroup":null,"administratorOrUser":null,"isVipTime":null,"isVip":null,"picUrl":null,"isEffective":null,"operatorIdentifier":null,"createTime":null,"weixin":null,"zhifubao":null,"qq":null,"permissions":null,"operatorName":null,"historyPayMoney":null,"monthHistoryPayMoney":null,"historyPayNum":null,"monthHistoryPayNum":null},"isHasInvoice":null,"invoice":{"id":14,"orderId":null,"invoiceType":0,"invoiceContent":1,"invoiceLookedUpType":1,"unitName":null,"taxpayerIdentificationNumber":null,"registeredAddress":null,"registeredTel":null,"depositBank":null,"bankAccount":null,"businessLicenseUrl":null},"orderStateDetails":[{"id":26,"orderStateDetail":"订单提交成功","addTime":1514965583000,"orderId":null},{"id":36,"orderStateDetail":"订单支付成功","addTime":1515061442000,"orderId":null},{"id":37,"orderStateDetail":"用户申请退款/退货","addTime":1515381698000,"orderId":null}],"offlinePayment":{"id":16,"orderId":null,"payerName":"张三","payerTel":"16868686868","remitterName":"AA","remitterAccount":"6256568956898","payeeName":"百度","payeeAccount":"621288XXXXXXXXXX","payeeAccountDepositBank":"撒大声地","remittanceAmount":64646,"paymentVoucherUrlOne":"offlinePaymentPicture/1515061004981.jpg","paymentVoucherUrlTwo":null,"state":6},"isFromShoppingCart":null,"isUseCoupon":null,"userCoupons":null,"isHasEvaluation":null}
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
         * id : 30
         * orderNo : JLORDER20180103154622
         * orderOriginalPrice : 345
         * orderPresentPrice : 280
         * userId : 18
         * placeOrderTime : 1514965583000
         * orderState : 6
         * consigneeName : XXX
         * consigneeTel : 18865656565
         * consigneeAddress : XXXX
         * payType : 1
         * payTime : null
         * postage : 5
         * postagePayType : 0
         * deliverGoodsTime : 1513958400000
         * payMode : null
         * preferentialType : null
         * carIdSend : null
         * carIdChangeReturn : null
         * carIdChangeSend : null
         * orderGoodsName : null
         * orderGoodsNum : null
         * orderGoodsPrice : null
         * orderDetails : [{"id":45,"goodsDetailsId":null,"goodsQuantity":3,"goodsSpecificationDetailsId":5,"goodsPurchasingPrice":35,"goodsOriginalPrice":85,"goodsPaymentPrice":200,"goodsName":"XXX","goodsCoverUrl":"GoodsDisplayPicture/1516956283213.png","goodsSpecificationName":"SDFSDFSD","orderId":null,"goodsDetails":null,"goodsEvaluation":null,"orderTable":null},{"id":46,"goodsDetailsId":null,"goodsQuantity":2,"goodsSpecificationDetailsId":8,"goodsPurchasingPrice":20,"goodsOriginalPrice":45,"goodsPaymentPrice":80,"goodsName":"rter","goodsCoverUrl":"GoodsDisplayPicture/1517205386608.png","goodsSpecificationName":"erytyui","orderId":null,"goodsDetails":null,"goodsEvaluation":null,"orderTable":null}]
         * user : {"id":18,"identifier":null,"loginName":null,"name":"13101793442","password":null,"phone":"13101793442","userGroup":null,"administratorOrUser":null,"isVipTime":null,"isVip":null,"picUrl":null,"isEffective":null,"operatorIdentifier":null,"createTime":null,"weixin":null,"zhifubao":null,"qq":null,"permissions":null,"operatorName":null,"historyPayMoney":null,"monthHistoryPayMoney":null,"historyPayNum":null,"monthHistoryPayNum":null}
         * isHasInvoice : null
         * invoice : {"id":14,"orderId":null,"invoiceType":0,"invoiceContent":1,"invoiceLookedUpType":1,"unitName":null,"taxpayerIdentificationNumber":null,"registeredAddress":null,"registeredTel":null,"depositBank":null,"bankAccount":null,"businessLicenseUrl":null}
         * orderStateDetails : [{"id":26,"orderStateDetail":"订单提交成功","addTime":1514965583000,"orderId":null},{"id":36,"orderStateDetail":"订单支付成功","addTime":1515061442000,"orderId":null},{"id":37,"orderStateDetail":"用户申请退款/退货","addTime":1515381698000,"orderId":null}]
         * offlinePayment : {"id":16,"orderId":null,"payerName":"张三","payerTel":"16868686868","remitterName":"AA","remitterAccount":"6256568956898","payeeName":"百度","payeeAccount":"621288XXXXXXXXXX","payeeAccountDepositBank":"撒大声地","remittanceAmount":64646,"paymentVoucherUrlOne":"offlinePaymentPicture/1515061004981.jpg","paymentVoucherUrlTwo":null,"state":6}
         * isFromShoppingCart : null
         * isUseCoupon : null
         * userCoupons : null
         * isHasEvaluation : null
         */

        private int id;
        private String orderNo;
        private double orderOriginalPrice;
        private double orderPresentPrice;
        private int userId;
        private long placeOrderTime;
        private int orderState;
        private String consigneeName;
        private String consigneeTel;
        private String consigneeAddress;
        private Integer payType;
        private Object payTime;
        private double postage;
        private Integer postagePayType;
        private long deliverGoodsTime;
        private Integer payMode;
        private Object preferentialType;
        private Object carIdSend;
        private Object carIdChangeReturn;
        private Object carIdChangeSend;
        private Object orderGoodsName;
        private Object orderGoodsNum;
        private double orderGoodsPrice;
        private UserBean user;
        private Object isHasInvoice;
        private InvoiceBean invoice;
        private OfflinePaymentBean offlinePayment;
        private Object isFromShoppingCart;
        private Object isUseCoupon;
        private Object userCoupons;
        private Integer isHasEvaluation;
        private ArrayList<OrderDetailsBean> orderDetails;
        private ArrayList<OrderStateDetailsBean> orderStateDetails;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public double getOrderOriginalPrice() {
            return orderOriginalPrice;
        }

        public void setOrderOriginalPrice(double orderOriginalPrice) {
            this.orderOriginalPrice = orderOriginalPrice;
        }

        public double getOrderPresentPrice() {
            return orderPresentPrice;
        }

        public void setOrderPresentPrice(double orderPresentPrice) {
            this.orderPresentPrice = orderPresentPrice;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getPlaceOrderTime() {
            return placeOrderTime;
        }

        public void setPlaceOrderTime(long placeOrderTime) {
            this.placeOrderTime = placeOrderTime;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
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

        public String getConsigneeAddress() {
            return consigneeAddress;
        }

        public void setConsigneeAddress(String consigneeAddress) {
            this.consigneeAddress = consigneeAddress;
        }

        public Integer getPayType() {
            return payType;
        }

        public void setPayType(Integer payType) {
            this.payType = payType;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public double getPostage() {
            return postage;
        }

        public void setPostage(double postage) {
            this.postage = postage;
        }

        public Integer getPostagePayType() {
            return postagePayType;
        }

        public void setPostagePayType(Integer postagePayType) {
            this.postagePayType = postagePayType;
        }

        public long getDeliverGoodsTime() {
            return deliverGoodsTime;
        }

        public void setDeliverGoodsTime(long deliverGoodsTime) {
            this.deliverGoodsTime = deliverGoodsTime;
        }

        public Integer getPayMode() {
            return payMode;
        }

        public void setPayMode(Integer payMode) {
            this.payMode = payMode;
        }

        public Object getPreferentialType() {
            return preferentialType;
        }

        public void setPreferentialType(Object preferentialType) {
            this.preferentialType = preferentialType;
        }

        public Object getCarIdSend() {
            return carIdSend;
        }

        public void setCarIdSend(Object carIdSend) {
            this.carIdSend = carIdSend;
        }

        public Object getCarIdChangeReturn() {
            return carIdChangeReturn;
        }

        public void setCarIdChangeReturn(Object carIdChangeReturn) {
            this.carIdChangeReturn = carIdChangeReturn;
        }

        public Object getCarIdChangeSend() {
            return carIdChangeSend;
        }

        public void setCarIdChangeSend(Object carIdChangeSend) {
            this.carIdChangeSend = carIdChangeSend;
        }

        public Object getOrderGoodsName() {
            return orderGoodsName;
        }

        public void setOrderGoodsName(Object orderGoodsName) {
            this.orderGoodsName = orderGoodsName;
        }

        public Object getOrderGoodsNum() {
            return orderGoodsNum;
        }

        public void setOrderGoodsNum(Object orderGoodsNum) {
            this.orderGoodsNum = orderGoodsNum;
        }

        public double getOrderGoodsPrice() {
            return orderGoodsPrice;
        }

        public void setOrderGoodsPrice(double orderGoodsPrice) {
            this.orderGoodsPrice = orderGoodsPrice;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public Object getIsHasInvoice() {
            return isHasInvoice;
        }

        public void setIsHasInvoice(Object isHasInvoice) {
            this.isHasInvoice = isHasInvoice;
        }

        public InvoiceBean getInvoice() {
            return invoice;
        }

        public void setInvoice(InvoiceBean invoice) {
            this.invoice = invoice;
        }

        public OfflinePaymentBean getOfflinePayment() {
            return offlinePayment;
        }

        public void setOfflinePayment(OfflinePaymentBean offlinePayment) {
            this.offlinePayment = offlinePayment;
        }

        public Object getIsFromShoppingCart() {
            return isFromShoppingCart;
        }

        public void setIsFromShoppingCart(Object isFromShoppingCart) {
            this.isFromShoppingCart = isFromShoppingCart;
        }

        public Object getIsUseCoupon() {
            return isUseCoupon;
        }

        public void setIsUseCoupon(Object isUseCoupon) {
            this.isUseCoupon = isUseCoupon;
        }

        public Object getUserCoupons() {
            return userCoupons;
        }

        public void setUserCoupons(Object userCoupons) {
            this.userCoupons = userCoupons;
        }

        public Integer getIsHasEvaluation() {
            return isHasEvaluation;
        }

        public void setIsHasEvaluation(Integer isHasEvaluation) {
            this.isHasEvaluation = isHasEvaluation;
        }

        public ArrayList<OrderDetailsBean> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(ArrayList<OrderDetailsBean> orderDetails) {
            this.orderDetails = orderDetails;
        }

        public ArrayList<OrderStateDetailsBean> getOrderStateDetails() {
            return orderStateDetails;
        }

        public void setOrderStateDetails(ArrayList<OrderStateDetailsBean> orderStateDetails) {
            this.orderStateDetails = orderStateDetails;
        }

        public static class UserBean {
            /**
             * id : 18
             * identifier : null
             * loginName : null
             * name : 13101793442
             * password : null
             * phone : 13101793442
             * userGroup : null
             * administratorOrUser : null
             * isVipTime : null
             * isVip : null
             * picUrl : null
             * isEffective : null
             * operatorIdentifier : null
             * createTime : null
             * weixin : null
             * zhifubao : null
             * qq : null
             * permissions : null
             * operatorName : null
             * historyPayMoney : null
             * monthHistoryPayMoney : null
             * historyPayNum : null
             * monthHistoryPayNum : null
             */

            private int id;
            private Object identifier;
            private Object loginName;
            private String name;
            private Object password;
            private String phone;
            private Object userGroup;
            private Object administratorOrUser;
            private Object isVipTime;
            private Object isVip;
            private Object picUrl;
            private Object isEffective;
            private Object operatorIdentifier;
            private Object createTime;
            private Object weixin;
            private Object zhifubao;
            private Object qq;
            private Object permissions;
            private Object operatorName;
            private Object historyPayMoney;
            private Object monthHistoryPayMoney;
            private Object historyPayNum;
            private Object monthHistoryPayNum;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getIdentifier() {
                return identifier;
            }

            public void setIdentifier(Object identifier) {
                this.identifier = identifier;
            }

            public Object getLoginName() {
                return loginName;
            }

            public void setLoginName(Object loginName) {
                this.loginName = loginName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getPassword() {
                return password;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getUserGroup() {
                return userGroup;
            }

            public void setUserGroup(Object userGroup) {
                this.userGroup = userGroup;
            }

            public Object getAdministratorOrUser() {
                return administratorOrUser;
            }

            public void setAdministratorOrUser(Object administratorOrUser) {
                this.administratorOrUser = administratorOrUser;
            }

            public Object getIsVipTime() {
                return isVipTime;
            }

            public void setIsVipTime(Object isVipTime) {
                this.isVipTime = isVipTime;
            }

            public Object getIsVip() {
                return isVip;
            }

            public void setIsVip(Object isVip) {
                this.isVip = isVip;
            }

            public Object getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(Object picUrl) {
                this.picUrl = picUrl;
            }

            public Object getIsEffective() {
                return isEffective;
            }

            public void setIsEffective(Object isEffective) {
                this.isEffective = isEffective;
            }

            public Object getOperatorIdentifier() {
                return operatorIdentifier;
            }

            public void setOperatorIdentifier(Object operatorIdentifier) {
                this.operatorIdentifier = operatorIdentifier;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getWeixin() {
                return weixin;
            }

            public void setWeixin(Object weixin) {
                this.weixin = weixin;
            }

            public Object getZhifubao() {
                return zhifubao;
            }

            public void setZhifubao(Object zhifubao) {
                this.zhifubao = zhifubao;
            }

            public Object getQq() {
                return qq;
            }

            public void setQq(Object qq) {
                this.qq = qq;
            }

            public Object getPermissions() {
                return permissions;
            }

            public void setPermissions(Object permissions) {
                this.permissions = permissions;
            }

            public Object getOperatorName() {
                return operatorName;
            }

            public void setOperatorName(Object operatorName) {
                this.operatorName = operatorName;
            }

            public Object getHistoryPayMoney() {
                return historyPayMoney;
            }

            public void setHistoryPayMoney(Object historyPayMoney) {
                this.historyPayMoney = historyPayMoney;
            }

            public Object getMonthHistoryPayMoney() {
                return monthHistoryPayMoney;
            }

            public void setMonthHistoryPayMoney(Object monthHistoryPayMoney) {
                this.monthHistoryPayMoney = monthHistoryPayMoney;
            }

            public Object getHistoryPayNum() {
                return historyPayNum;
            }

            public void setHistoryPayNum(Object historyPayNum) {
                this.historyPayNum = historyPayNum;
            }

            public Object getMonthHistoryPayNum() {
                return monthHistoryPayNum;
            }

            public void setMonthHistoryPayNum(Object monthHistoryPayNum) {
                this.monthHistoryPayNum = monthHistoryPayNum;
            }
        }

        public static class InvoiceBean {
            /**
             * id : 14
             * orderId : null
             * invoiceType : 0
             * invoiceContent : 1
             * invoiceLookedUpType : 1
             * unitName : null
             * taxpayerIdentificationNumber : null
             * registeredAddress : null
             * registeredTel : null
             * depositBank : null
             * bankAccount : null
             * businessLicenseUrl : null
             */

            private int id;
            private Object orderId;
            private int invoiceType;
            private int invoiceContent;
            private int invoiceLookedUpType;
            private Object unitName;
            private Object taxpayerIdentificationNumber;
            private Object registeredAddress;
            private Object registeredTel;
            private Object depositBank;
            private Object bankAccount;
            private Object businessLicenseUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
                this.orderId = orderId;
            }

            public int getInvoiceType() {
                return invoiceType;
            }

            public void setInvoiceType(int invoiceType) {
                this.invoiceType = invoiceType;
            }

            public int getInvoiceContent() {
                return invoiceContent;
            }

            public void setInvoiceContent(int invoiceContent) {
                this.invoiceContent = invoiceContent;
            }

            public int getInvoiceLookedUpType() {
                return invoiceLookedUpType;
            }

            public void setInvoiceLookedUpType(int invoiceLookedUpType) {
                this.invoiceLookedUpType = invoiceLookedUpType;
            }

            public Object getUnitName() {
                return unitName;
            }

            public void setUnitName(Object unitName) {
                this.unitName = unitName;
            }

            public Object getTaxpayerIdentificationNumber() {
                return taxpayerIdentificationNumber;
            }

            public void setTaxpayerIdentificationNumber(Object taxpayerIdentificationNumber) {
                this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
            }

            public Object getRegisteredAddress() {
                return registeredAddress;
            }

            public void setRegisteredAddress(Object registeredAddress) {
                this.registeredAddress = registeredAddress;
            }

            public Object getRegisteredTel() {
                return registeredTel;
            }

            public void setRegisteredTel(Object registeredTel) {
                this.registeredTel = registeredTel;
            }

            public Object getDepositBank() {
                return depositBank;
            }

            public void setDepositBank(Object depositBank) {
                this.depositBank = depositBank;
            }

            public Object getBankAccount() {
                return bankAccount;
            }

            public void setBankAccount(Object bankAccount) {
                this.bankAccount = bankAccount;
            }

            public Object getBusinessLicenseUrl() {
                return businessLicenseUrl;
            }

            public void setBusinessLicenseUrl(Object businessLicenseUrl) {
                this.businessLicenseUrl = businessLicenseUrl;
            }
        }

        public static class OfflinePaymentBean {
            /**
             * id : 16
             * orderId : null
             * payerName : 张三
             * payerTel : 16868686868
             * remitterName : AA
             * remitterAccount : 6256568956898
             * payeeName : 百度
             * payeeAccount : 621288XXXXXXXXXX
             * payeeAccountDepositBank : 撒大声地
             * remittanceAmount : 64646
             * paymentVoucherUrlOne : offlinePaymentPicture/1515061004981.jpg
             * paymentVoucherUrlTwo : null
             * state : 6
             */

            private int id;
            private Object orderId;
            private String payerName;
            private String payerTel;
            private String remitterName;
            private String remitterAccount;
            private String payeeName;
            private String payeeAccount;
            private String payeeAccountDepositBank;
            private double remittanceAmount;
            private String paymentVoucherUrlOne;
            private String paymentVoucherUrlTwo;
            private int state;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
                this.orderId = orderId;
            }

            public String getPayerName() {
                return payerName;
            }

            public void setPayerName(String payerName) {
                this.payerName = payerName;
            }

            public String getPayerTel() {
                return payerTel;
            }

            public void setPayerTel(String payerTel) {
                this.payerTel = payerTel;
            }

            public String getRemitterName() {
                return remitterName;
            }

            public void setRemitterName(String remitterName) {
                this.remitterName = remitterName;
            }

            public String getRemitterAccount() {
                return remitterAccount;
            }

            public void setRemitterAccount(String remitterAccount) {
                this.remitterAccount = remitterAccount;
            }

            public String getPayeeName() {
                return payeeName;
            }

            public void setPayeeName(String payeeName) {
                this.payeeName = payeeName;
            }

            public String getPayeeAccount() {
                return payeeAccount;
            }

            public void setPayeeAccount(String payeeAccount) {
                this.payeeAccount = payeeAccount;
            }

            public String getPayeeAccountDepositBank() {
                return payeeAccountDepositBank;
            }

            public void setPayeeAccountDepositBank(String payeeAccountDepositBank) {
                this.payeeAccountDepositBank = payeeAccountDepositBank;
            }

            public double getRemittanceAmount() {
                return remittanceAmount;
            }

            public void setRemittanceAmount(double remittanceAmount) {
                this.remittanceAmount = remittanceAmount;
            }

            public String getPaymentVoucherUrlOne() {
                return paymentVoucherUrlOne;
            }

            public void setPaymentVoucherUrlOne(String paymentVoucherUrlOne) {
                this.paymentVoucherUrlOne = paymentVoucherUrlOne;
            }

            public String getPaymentVoucherUrlTwo() {
                return paymentVoucherUrlTwo;
            }

            public void setPaymentVoucherUrlTwo(String paymentVoucherUrlTwo) {
                this.paymentVoucherUrlTwo = paymentVoucherUrlTwo;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }

        public static class OrderDetailsBean {
            /**
             * id : 45
             * goodsDetailsId : null
             * goodsQuantity : 3
             * goodsSpecificationDetailsId : 5
             * goodsPurchasingPrice : 35
             * goodsOriginalPrice : 85
             * goodsPaymentPrice : 200
             * goodsName : XXX
             * goodsCoverUrl : GoodsDisplayPicture/1516956283213.png
             * goodsSpecificationName : SDFSDFSD
             * orderId : null
             * goodsDetails : null
             * goodsEvaluation : null
             * orderTable : null
             */

            private int id;
            private Object goodsDetailsId;
            private int goodsQuantity;
            private int goodsSpecificationDetailsId;
            private double goodsPurchasingPrice;
            private double goodsOriginalPrice;
            private double goodsPaymentPrice;
            private String goodsName;
            private String goodsCoverUrl;
            private String goodsSpecificationName;
            private Object orderId;
            private Object goodsDetails;
            private Object goodsEvaluation;
            private Object orderTable;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getGoodsDetailsId() {
                return goodsDetailsId;
            }

            public void setGoodsDetailsId(Object goodsDetailsId) {
                this.goodsDetailsId = goodsDetailsId;
            }

            public int getGoodsQuantity() {
                return goodsQuantity;
            }

            public void setGoodsQuantity(int goodsQuantity) {
                this.goodsQuantity = goodsQuantity;
            }

            public int getGoodsSpecificationDetailsId() {
                return goodsSpecificationDetailsId;
            }

            public void setGoodsSpecificationDetailsId(int goodsSpecificationDetailsId) {
                this.goodsSpecificationDetailsId = goodsSpecificationDetailsId;
            }

            public double getGoodsPurchasingPrice() {
                return goodsPurchasingPrice;
            }

            public void setGoodsPurchasingPrice(double goodsPurchasingPrice) {
                this.goodsPurchasingPrice = goodsPurchasingPrice;
            }

            public double getGoodsOriginalPrice() {
                return goodsOriginalPrice;
            }

            public void setGoodsOriginalPrice(int goodsOriginalPrice) {
                this.goodsOriginalPrice = goodsOriginalPrice;
            }

            public double getGoodsPaymentPrice() {
                return goodsPaymentPrice;
            }

            public void setGoodsPaymentPrice(int goodsPaymentPrice) {
                this.goodsPaymentPrice = goodsPaymentPrice;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsCoverUrl() {
                return goodsCoverUrl;
            }

            public void setGoodsCoverUrl(String goodsCoverUrl) {
                this.goodsCoverUrl = goodsCoverUrl;
            }

            public String getGoodsSpecificationName() {
                return goodsSpecificationName;
            }

            public void setGoodsSpecificationName(String goodsSpecificationName) {
                this.goodsSpecificationName = goodsSpecificationName;
            }

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
                this.orderId = orderId;
            }

            public Object getGoodsDetails() {
                return goodsDetails;
            }

            public void setGoodsDetails(Object goodsDetails) {
                this.goodsDetails = goodsDetails;
            }

            public Object getGoodsEvaluation() {
                return goodsEvaluation;
            }

            public void setGoodsEvaluation(Object goodsEvaluation) {
                this.goodsEvaluation = goodsEvaluation;
            }

            public Object getOrderTable() {
                return orderTable;
            }

            public void setOrderTable(Object orderTable) {
                this.orderTable = orderTable;
            }
        }

        public static class OrderStateDetailsBean {
            /**
             * id : 26
             * orderStateDetail : 订单提交成功
             * addTime : 1514965583000
             * orderId : null
             */

            private int id;
            private String orderStateDetail;
            private Long addTime;
            private Object orderId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderStateDetail() {
                return orderStateDetail;
            }

            public void setOrderStateDetail(String orderStateDetail) {
                this.orderStateDetail = orderStateDetail;
            }

            public Long getAddTime() {
                return addTime;
            }

            public void setAddTime(Long addTime) {
                this.addTime = addTime;
            }

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
                this.orderId = orderId;
            }
        }
    }
}
