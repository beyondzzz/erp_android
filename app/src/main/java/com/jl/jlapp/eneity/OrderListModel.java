package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/2/8 0008.
 */

public class OrderListModel {


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
        private int payType;
        private Object payTime;
        private double postage;
        private int postagePayType;
        private long deliverGoodsTime;
        private Object payMode;
        private Object preferentialType;
        private Object carIdSend;
        private Object carIdChangeReturn;
        private Object carIdChangeSend;
        private Object orderGoodsName;
        private Object orderGoodsNum;
        private Object orderGoodsPrice;
        private UserBean user;
        private Object isHasInvoice;
        private Object invoice;
        private Object orderStateDetails;
        private Object offlinePayment;
        private Object isFromShoppingCart;
        private Object isUseCoupon;
        private Object userCoupons;
        private int isHasEvaluation;
        private List<OrderDetailsBean> orderDetails;

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

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
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

        public int getPostagePayType() {
            return postagePayType;
        }

        public void setPostagePayType(int postagePayType) {
            this.postagePayType = postagePayType;
        }

        public long getDeliverGoodsTime() {
            return deliverGoodsTime;
        }

        public void setDeliverGoodsTime(long deliverGoodsTime) {
            this.deliverGoodsTime = deliverGoodsTime;
        }

        public Object getPayMode() {
            return payMode;
        }

        public void setPayMode(Object payMode) {
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

        public Object getOrderGoodsPrice() {
            return orderGoodsPrice;
        }

        public void setOrderGoodsPrice(Object orderGoodsPrice) {
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

        public Object getInvoice() {
            return invoice;
        }

        public void setInvoice(Object invoice) {
            this.invoice = invoice;
        }

        public Object getOrderStateDetails() {
            return orderStateDetails;
        }

        public void setOrderStateDetails(Object orderStateDetails) {
            this.orderStateDetails = orderStateDetails;
        }

        public Object getOfflinePayment() {
            return offlinePayment;
        }

        public void setOfflinePayment(Object offlinePayment) {
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

        public int getIsHasEvaluation() {
            return isHasEvaluation;
        }

        public void setIsHasEvaluation(int isHasEvaluation) {
            this.isHasEvaluation = isHasEvaluation;
        }

        public List<OrderDetailsBean> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(List<OrderDetailsBean> orderDetails) {
            this.orderDetails = orderDetails;
        }

        public static class UserBean {

            private int id;
            private Object identifier;
            private Object loginName;
            private String name;
            private Object password;
            private String phone;
            private Object userGroup;
            private Object administratorOrUser;
            private Object isVipTime;
            private int isVip;
            private String picUrl;
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

            public int getIsVip() {
                return isVip;
            }

            public void setIsVip(int isVip) {
                this.isVip = isVip;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
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

        public static class OrderDetailsBean {


            private int id;
            private Object goodsDetailsId;
            private int goodsQuantity;
            private Object goodsSpecificationDetailsId;
            private Object goodsPurchasingPrice;
            private double goodsOriginalPrice;
            private double goodsPaymentPrice;
            private String goodsName;
            private String goodsCoverUrl;
            private String goodsSpecificationName;
            private int orderId;
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

            public Object getGoodsSpecificationDetailsId() {
                return goodsSpecificationDetailsId;
            }

            public void setGoodsSpecificationDetailsId(Object goodsSpecificationDetailsId) {
                this.goodsSpecificationDetailsId = goodsSpecificationDetailsId;
            }

            public Object getGoodsPurchasingPrice() {
                return goodsPurchasingPrice;
            }

            public void setGoodsPurchasingPrice(Object goodsPurchasingPrice) {
                this.goodsPurchasingPrice = goodsPurchasingPrice;
            }

            public double getGoodsOriginalPrice() {
                return goodsOriginalPrice;
            }

            public void setGoodsOriginalPrice(double goodsOriginalPrice) {
                this.goodsOriginalPrice = goodsOriginalPrice;
            }

            public double getGoodsPaymentPrice() {
                return goodsPaymentPrice;
            }

            public void setGoodsPaymentPrice(double goodsPaymentPrice) {
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

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
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
    }
}
