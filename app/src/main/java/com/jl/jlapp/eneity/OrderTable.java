package com.jl.jlapp.eneity;

import java.util.Date;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/2/5 0005.
 */

public class OrderTable {


    /**
     * orderOriginalPrice : 96
     * orderPresentPrice : 60
     * userId : 18
     * consigneeName : XXX
     * consigneeTel : 18865656565
     * consigneeAddress : XXXX
     * payType : 1
     * postage : 5
     * postagePayType : 0
     * deliverGoodsTime : 2017-12-23
     * isHasInvoice : 1
     * isFromShoppingCart : 1
     * isUseCoupon : 1
     * orderDetails : [{"goodsDetailsId":3,"goodsQuantity":6,"goodsSpecificationDetailsId":8,"goodsPurchasingPrice":40,"goodsOriginalPrice":60,"goodsPaymentPrice":512,"goodsName":"XXX","goodsCoverUrl":"XXXXXX","goodsSpecificationName":"SDFSDFSD"},{"goodsDetailsId":3,"goodsQuantity":1,"goodsSpecificationDetailsId":5,"goodsPurchasingPrice":20,"goodsOriginalPrice":34,"goodsPaymentPrice":34,"goodsName":"rter","goodsCoverUrl":"dfgdf","goodsSpecificationName":"erytyui"}]
     * offlinePayment : {"payerName":"张三","payerTel":"16868686868"}
     * invoice : {"invoiceType":0,"invoiceContent":1,"invoiceLookedUpType":1}
     * userCoupons : {"id":1}
     */

    private double orderOriginalPrice;
    private double orderPresentPrice;
    private int userId;
    private String consigneeName;
    private String consigneeTel;
    private String consigneeAddress;
    private int payType;
    private double postage;
    private int postagePayType;
    private String deliverGoodsTime;
    private int isHasInvoice;
    private int isFromShoppingCart;
    private int isUseCoupon;
    private int activityId;
    private OfflinePaymentBean offlinePayment;
    private InvoiceBean invoice;
    private UserCouponsBean userCoupons;
    private List<OrderDetailsBean> orderDetails;

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

    public String getDeliverGoodsTime() {
        return deliverGoodsTime;
    }

    public void setDeliverGoodsTime(String deliverGoodsTime) {
        this.deliverGoodsTime = deliverGoodsTime;
    }

    public int getIsHasInvoice() {
        return isHasInvoice;
    }

    public void setIsHasInvoice(int isHasInvoice) {
        this.isHasInvoice = isHasInvoice;
    }

    public int getIsFromShoppingCart() {
        return isFromShoppingCart;
    }

    public void setIsFromShoppingCart(int isFromShoppingCart) {
        this.isFromShoppingCart = isFromShoppingCart;
    }

    public int getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(int isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public OfflinePaymentBean getOfflinePayment() {
        return offlinePayment;
    }

    public void setOfflinePayment(OfflinePaymentBean offlinePayment) {
        this.offlinePayment = offlinePayment;
    }

    public InvoiceBean getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceBean invoice) {
        this.invoice = invoice;
    }

    public UserCouponsBean getUserCoupons() {
        return userCoupons;
    }

    public void setUserCoupons(UserCouponsBean userCoupons) {
        this.userCoupons = userCoupons;
    }

    public List<OrderDetailsBean> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsBean> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public static class OfflinePaymentBean {
        /**
         * payerName : 张三
         * payerTel : 16868686868
         */

        private String payerName;
        private String payerTel;

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
    }

    public static class InvoiceBean {
        /**
         * invoiceType : 0
         * invoiceContent : 1
         * invoiceLookedUpType : 1
         */

        private int invoiceType;
        private int invoiceContent;
        private int invoiceLookedUpType;
        private String unitName;
        private String taxpayerIdentificationNumber;
        private String registeredAddress;
        private String registeredTel;
        private String depositBank;
        private String bankAccount;
        private String businessLicenseUrl;

        public String getBusinessLicenseUrl() {
            return businessLicenseUrl;
        }

        public void setBusinessLicenseUrl(String businessLicenseUrl) {
            this.businessLicenseUrl = businessLicenseUrl;
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
    }

    public static class UserCouponsBean {
        /**
         * id : 1
         */

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class OrderDetailsBean {
        /**
         * goodsDetailsId : 3
         * goodsQuantity : 6
         * goodsSpecificationDetailsId : 8
         * goodsPurchasingPrice : 40
         * goodsOriginalPrice : 60
         * goodsPaymentPrice : 512
         * goodsName : XXX
         * goodsCoverUrl : XXXXXX
         * goodsSpecificationName : SDFSDFSD
         */

        private int goodsDetailsId;
        private int goodsQuantity;
        private int goodsSpecificationDetailsId;
        private double goodsPurchasingPrice;
        private double goodsOriginalPrice;
        private double goodsPaymentPrice;
        private String goodsName;
        private String goodsCoverUrl;
        private String goodsSpecificationName;

        public int getGoodsDetailsId() {
            return goodsDetailsId;
        }

        public void setGoodsDetailsId(int goodsDetailsId) {
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
    }
}
