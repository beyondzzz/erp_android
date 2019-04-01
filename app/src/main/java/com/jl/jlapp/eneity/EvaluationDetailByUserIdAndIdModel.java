package com.jl.jlapp.eneity;


import java.util.List;

/**
 * Created by THINK on 2018-02-10.
 */

public class EvaluationDetailByUserIdAndIdModel {

    /**
     * msg : 请求成功
     * code : 200
     * resultData : {"id":15,"orderDetailId":46,"evaluationContent":"1234","score":3,"evaluationTime":1530943051000,"evaluationContentWordNum":4,"evaluationPics":[{"id":17,"picUrl":"uploadImages/8b9bd5ed0625432b83c912c9f1310afc.jpg","goodsEvaluationId":15}],"user":{"id":11,"identifier":null,"loginName":null,"name":"百度1530786301114","password":null,"phone":"13101793442","userGroup":null,"administratorOrUser":null,"isVipTime":null,"isVip":0,"picUrl":null,"isEffective":null,"operatorIdentifier":null,"createTime":null,"weixin":null,"zhifubao":null,"qq":null,"permissions":null,"operatorName":null,"historyPayMoney":null,"monthHistoryPayMoney":null,"historyPayNum":null,"monthHistoryPayNum":null},"orderDetail":{"id":46,"goodsDetailsId":112,"goodsQuantity":1,"goodsSpecificationDetailsId":130,"goodsPurchasingPrice":30,"goodsOriginalPrice":500000,"goodsPaymentPrice":500000,"goodsName":"900G带鱼这个把您在吃啥好的地方is电话费会计师大会副科级圣诞红包查收到不能吃啥都会出接口的搜索交付void是没吧看的反馈了数据","goodsCoverUrl":"GoodsDisplayPicture/6ac5669aa82f4239b9aed544807e3dcf.jpg","goodsSpecificationName":"袋","orderId":50,"goodsDetails":null,"goodsEvaluation":null,"orderTable":{"id":50,"orderNo":"JLORDER20180706104645","orderOriginalPrice":26,"orderPresentPrice":0.01,"userId":11,"placeOrderTime":1530845206000,"orderState":3,"consigneeName":"123","consigneeTel":"15238628954","consigneeAddress":"北京市东城区三环她要是","payType":0,"payTime":1530845299000,"postage":0,"postagePayType":0,"deliverGoodsTime":1531065600000,"payMode":2,"preferentialType":null,"carIdSend":"京A4578945","carIdChangeReturn":null,"carIdChangeSend":null,"orderGoodsName":null,"orderGoodsNum":null,"orderGoodsPrice":null,"orderDetails":null,"user":null,"isHasInvoice":null,"invoice":null,"orderStateDetails":null,"offlinePayment":null,"afterSaleDetail":null,"isFromShoppingCart":null,"isUseCoupon":null,"userCoupons":null,"isHasEvaluation":null,"activityId":null}}}
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
         * id : 15
         * orderDetailId : 46
         * evaluationContent : 1234
         * score : 3
         * evaluationTime : 1530943051000
         * evaluationContentWordNum : 4
         * evaluationPics : [{"id":17,"picUrl":"uploadImages/8b9bd5ed0625432b83c912c9f1310afc.jpg","goodsEvaluationId":15}]
         * user : {"id":11,"identifier":null,"loginName":null,"name":"百度1530786301114","password":null,"phone":"13101793442","userGroup":null,"administratorOrUser":null,"isVipTime":null,"isVip":0,"picUrl":null,"isEffective":null,"operatorIdentifier":null,"createTime":null,"weixin":null,"zhifubao":null,"qq":null,"permissions":null,"operatorName":null,"historyPayMoney":null,"monthHistoryPayMoney":null,"historyPayNum":null,"monthHistoryPayNum":null}
         * orderDetail : {"id":46,"goodsDetailsId":112,"goodsQuantity":1,"goodsSpecificationDetailsId":130,"goodsPurchasingPrice":30,"goodsOriginalPrice":500000,"goodsPaymentPrice":500000,"goodsName":"900G带鱼这个把您在吃啥好的地方is电话费会计师大会副科级圣诞红包查收到不能吃啥都会出接口的搜索交付void是没吧看的反馈了数据","goodsCoverUrl":"GoodsDisplayPicture/6ac5669aa82f4239b9aed544807e3dcf.jpg","goodsSpecificationName":"袋","orderId":50,"goodsDetails":null,"goodsEvaluation":null,"orderTable":{"id":50,"orderNo":"JLORDER20180706104645","orderOriginalPrice":26,"orderPresentPrice":0.01,"userId":11,"placeOrderTime":1530845206000,"orderState":3,"consigneeName":"123","consigneeTel":"15238628954","consigneeAddress":"北京市东城区三环她要是","payType":0,"payTime":1530845299000,"postage":0,"postagePayType":0,"deliverGoodsTime":1531065600000,"payMode":2,"preferentialType":null,"carIdSend":"京A4578945","carIdChangeReturn":null,"carIdChangeSend":null,"orderGoodsName":null,"orderGoodsNum":null,"orderGoodsPrice":null,"orderDetails":null,"user":null,"isHasInvoice":null,"invoice":null,"orderStateDetails":null,"offlinePayment":null,"afterSaleDetail":null,"isFromShoppingCart":null,"isUseCoupon":null,"userCoupons":null,"isHasEvaluation":null,"activityId":null}}
         */

        private int id;
        private int orderDetailId;
        private String evaluationContent;
        private int score;
        private long evaluationTime;
        private int evaluationContentWordNum;
        private UserBean user;
        private OrderDetailBean orderDetail;
        private List<EvaluationPicsBean> evaluationPics;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderDetailId() {
            return orderDetailId;
        }

        public void setOrderDetailId(int orderDetailId) {
            this.orderDetailId = orderDetailId;
        }

        public String getEvaluationContent() {
            return evaluationContent;
        }

        public void setEvaluationContent(String evaluationContent) {
            this.evaluationContent = evaluationContent;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public long getEvaluationTime() {
            return evaluationTime;
        }

        public void setEvaluationTime(long evaluationTime) {
            this.evaluationTime = evaluationTime;
        }

        public int getEvaluationContentWordNum() {
            return evaluationContentWordNum;
        }

        public void setEvaluationContentWordNum(int evaluationContentWordNum) {
            this.evaluationContentWordNum = evaluationContentWordNum;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public OrderDetailBean getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(OrderDetailBean orderDetail) {
            this.orderDetail = orderDetail;
        }

        public List<EvaluationPicsBean> getEvaluationPics() {
            return evaluationPics;
        }

        public void setEvaluationPics(List<EvaluationPicsBean> evaluationPics) {
            this.evaluationPics = evaluationPics;
        }

        public static class UserBean {
            /**
             * id : 11
             * identifier : null
             * loginName : null
             * name : 百度1530786301114
             * password : null
             * phone : 13101793442
             * userGroup : null
             * administratorOrUser : null
             * isVipTime : null
             * isVip : 0
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
            private int isVip;
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

            public int getIsVip() {
                return isVip;
            }

            public void setIsVip(int isVip) {
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

        public static class OrderDetailBean {
            /**
             * id : 46
             * goodsDetailsId : 112
             * goodsQuantity : 1
             * goodsSpecificationDetailsId : 130
             * goodsPurchasingPrice : 30
             * goodsOriginalPrice : 500000
             * goodsPaymentPrice : 500000
             * goodsName : 900G带鱼这个把您在吃啥好的地方is电话费会计师大会副科级圣诞红包查收到不能吃啥都会出接口的搜索交付void是没吧看的反馈了数据
             * goodsCoverUrl : GoodsDisplayPicture/6ac5669aa82f4239b9aed544807e3dcf.jpg
             * goodsSpecificationName : 袋
             * orderId : 50
             * goodsDetails : null
             * goodsEvaluation : null
             * orderTable : {"id":50,"orderNo":"JLORDER20180706104645","orderOriginalPrice":26,"orderPresentPrice":0.01,"userId":11,"placeOrderTime":1530845206000,"orderState":3,"consigneeName":"123","consigneeTel":"15238628954","consigneeAddress":"北京市东城区三环她要是","payType":0,"payTime":1530845299000,"postage":0,"postagePayType":0,"deliverGoodsTime":1531065600000,"payMode":2,"preferentialType":null,"carIdSend":"京A4578945","carIdChangeReturn":null,"carIdChangeSend":null,"orderGoodsName":null,"orderGoodsNum":null,"orderGoodsPrice":null,"orderDetails":null,"user":null,"isHasInvoice":null,"invoice":null,"orderStateDetails":null,"offlinePayment":null,"afterSaleDetail":null,"isFromShoppingCart":null,"isUseCoupon":null,"userCoupons":null,"isHasEvaluation":null,"activityId":null}
             */

            private int id;
            private int goodsDetailsId;
            private int goodsQuantity;
            private int goodsSpecificationDetailsId;
            private double goodsPurchasingPrice;
            private double goodsOriginalPrice;
            private double goodsPaymentPrice;
            private String goodsName;
            private String goodsCoverUrl;
            private String goodsSpecificationName;
            private int orderId;
            private Object goodsDetails;
            private Object goodsEvaluation;
            private OrderTableBean orderTable;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public OrderTableBean getOrderTable() {
                return orderTable;
            }

            public void setOrderTable(OrderTableBean orderTable) {
                this.orderTable = orderTable;
            }

            public static class OrderTableBean {
                /**
                 * id : 50
                 * orderNo : JLORDER20180706104645
                 * orderOriginalPrice : 26
                 * orderPresentPrice : 0.01
                 * userId : 11
                 * placeOrderTime : 1530845206000
                 * orderState : 3
                 * consigneeName : 123
                 * consigneeTel : 15238628954
                 * consigneeAddress : 北京市东城区三环她要是
                 * payType : 0
                 * payTime : 1530845299000
                 * postage : 0
                 * postagePayType : 0
                 * deliverGoodsTime : 1531065600000
                 * payMode : 2
                 * preferentialType : null
                 * carIdSend : 京A4578945
                 * carIdChangeReturn : null
                 * carIdChangeSend : null
                 * orderGoodsName : null
                 * orderGoodsNum : null
                 * orderGoodsPrice : null
                 * orderDetails : null
                 * user : null
                 * isHasInvoice : null
                 * invoice : null
                 * orderStateDetails : null
                 * offlinePayment : null
                 * afterSaleDetail : null
                 * isFromShoppingCart : null
                 * isUseCoupon : null
                 * userCoupons : null
                 * isHasEvaluation : null
                 * activityId : null
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
                private int payType;
                private long payTime;
                private int postage;
                private int postagePayType;
                private long deliverGoodsTime;
                private int payMode;
                private Object preferentialType;
                private String carIdSend;
                private Object carIdChangeReturn;
                private Object carIdChangeSend;
                private Object orderGoodsName;
                private Object orderGoodsNum;
                private double orderGoodsPrice;
                private Object orderDetails;
                private Object user;
                private Object isHasInvoice;
                private Object invoice;
                private Object orderStateDetails;
                private Object offlinePayment;
                private Object afterSaleDetail;
                private Object isFromShoppingCart;
                private Object isUseCoupon;
                private Object userCoupons;
                private Object isHasEvaluation;
                private Object activityId;

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

                public long getPayTime() {
                    return payTime;
                }

                public void setPayTime(long payTime) {
                    this.payTime = payTime;
                }

                public int getPostage() {
                    return postage;
                }

                public void setPostage(int postage) {
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

                public int getPayMode() {
                    return payMode;
                }

                public void setPayMode(int payMode) {
                    this.payMode = payMode;
                }

                public Object getPreferentialType() {
                    return preferentialType;
                }

                public void setPreferentialType(Object preferentialType) {
                    this.preferentialType = preferentialType;
                }

                public String getCarIdSend() {
                    return carIdSend;
                }

                public void setCarIdSend(String carIdSend) {
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

                public Object getOrderDetails() {
                    return orderDetails;
                }

                public void setOrderDetails(Object orderDetails) {
                    this.orderDetails = orderDetails;
                }

                public Object getUser() {
                    return user;
                }

                public void setUser(Object user) {
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

                public Object getAfterSaleDetail() {
                    return afterSaleDetail;
                }

                public void setAfterSaleDetail(Object afterSaleDetail) {
                    this.afterSaleDetail = afterSaleDetail;
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

                public Object getIsHasEvaluation() {
                    return isHasEvaluation;
                }

                public void setIsHasEvaluation(Object isHasEvaluation) {
                    this.isHasEvaluation = isHasEvaluation;
                }

                public Object getActivityId() {
                    return activityId;
                }

                public void setActivityId(Object activityId) {
                    this.activityId = activityId;
                }
            }
        }

        public static class EvaluationPicsBean {
            /**
             * id : 17
             * picUrl : uploadImages/8b9bd5ed0625432b83c912c9f1310afc.jpg
             * goodsEvaluationId : 15
             */

            private int id;
            private String picUrl;
            private int goodsEvaluationId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getGoodsEvaluationId() {
                return goodsEvaluationId;
            }

            public void setGoodsEvaluationId(int goodsEvaluationId) {
                this.goodsEvaluationId = goodsEvaluationId;
            }
        }
    }
}


