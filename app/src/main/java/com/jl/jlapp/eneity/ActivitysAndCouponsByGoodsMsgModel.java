package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/2/5 0005.
 */

public class ActivitysAndCouponsByGoodsMsgModel {
    /**
     * msg : 请求成功
     * code : 200
     * resultData : {"coupons":[{"userCoupons":{"id":21,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513934026000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null},"afterDiscountMoney":0},{"userCoupons":{"id":15,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513836866000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null},"afterDiscountMoney":0}],"activitys":[{"activityInformation":{"id":32,"identifier":null,"name":"1234","activityType":1,"price":0,"discount":20,"maxNum":1,"introduction":null,"messagePicUrl":null,"showPicUrl":null,"budget":0,"beginValidityTime":1514908800000,"endValidityTime":1520697600000,"state":4,"operatorIdentifier":null,"operatorTime":0,"user":null,"goods":null,"goodsState":null,"coupon":null},"afterDiscountMoney":20,"userCouponMsgList":[{"userCoupons":{"id":21,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513934026000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null},"afterDiscountMoney":15},{"userCoupons":{"id":15,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513836866000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null},"afterDiscountMoney":15}]}]}
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
        private List<CouponsBean> coupons;
        private List<ActivitysBean> activitys;

        public List<CouponsBean> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponsBean> coupons) {
            this.coupons = coupons;
        }

        public List<ActivitysBean> getActivitys() {
            return activitys;
        }

        public void setActivitys(List<ActivitysBean> activitys) {
            this.activitys = activitys;
        }

        public static class CouponsBean {
            /**
             * userCoupons : {"id":21,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513934026000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null}
             * afterDiscountMoney : 0
             */

            private UserCouponsBean userCoupons;
            private double afterDiscountMoney;

            public UserCouponsBean getUserCoupons() {
                return userCoupons;
            }

            public void setUserCoupons(UserCouponsBean userCoupons) {
                this.userCoupons = userCoupons;
            }

            public double getAfterDiscountMoney() {
                return afterDiscountMoney;
            }

            public void setAfterDiscountMoney(double afterDiscountMoney) {
                this.afterDiscountMoney = afterDiscountMoney;
            }

            public static class UserCouponsBean {
                /**
                 * id : 21
                 * userId : 22
                 * couponInformationId : 5
                 * status : 0
                 * useTime : null
                 * getTime : 1513934026000
                 * couponInformation : {"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null}
                 * stateMsg : null
                 * useInstruction : null
                 */

                private int id;
                private int userId;
                private int couponInformationId;
                private int status;
                private Object useTime;
                private long getTime;
                private CouponInformationBean couponInformation;
                private Object stateMsg;
                private Object useInstruction;

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

                public int getCouponInformationId() {
                    return couponInformationId;
                }

                public void setCouponInformationId(int couponInformationId) {
                    this.couponInformationId = couponInformationId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public Object getUseTime() {
                    return useTime;
                }

                public void setUseTime(Object useTime) {
                    this.useTime = useTime;
                }

                public long getGetTime() {
                    return getTime;
                }

                public void setGetTime(long getTime) {
                    this.getTime = getTime;
                }

                public CouponInformationBean getCouponInformation() {
                    return couponInformation;
                }

                public void setCouponInformation(CouponInformationBean couponInformation) {
                    this.couponInformation = couponInformation;
                }

                public Object getStateMsg() {
                    return stateMsg;
                }

                public void setStateMsg(Object stateMsg) {
                    this.stateMsg = stateMsg;
                }

                public Object getUseInstruction() {
                    return useInstruction;
                }

                public void setUseInstruction(Object useInstruction) {
                    this.useInstruction = useInstruction;
                }

                public static class CouponInformationBean {
                    /**
                     * id : 5
                     * identifier : JLCOU20171124105645
                     * name : 111
                     * price : 5
                     * total : 333
                     * useLimit : 5
                     * count : 333
                     * state : 0
                     * rules : 0
                     * beginValidityTime : 1511884800000
                     * endValidityTime : 1518105600000
                     * beginTime : 1511884800000
                     * endTime : 1518105600000
                     * operatorIdentifier : JLADMIN20171113192605
                     * operatorTime : 1511492210000
                     * user : null
                     */

                    private int id;
                    private String identifier;
                    private String name;
                    private double price;
                    private int total;
                    private double useLimit;
                    private int count;
                    private int state;
                    private int rules;
                    private long beginValidityTime;
                    private long endValidityTime;
                    private long beginTime;
                    private long endTime;
                    private String operatorIdentifier;
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

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
                        this.price = price;
                    }

                    public int getTotal() {
                        return total;
                    }

                    public void setTotal(int total) {
                        this.total = total;
                    }

                    public double getUseLimit() {
                        return useLimit;
                    }

                    public void setUseLimit(double useLimit) {
                        this.useLimit = useLimit;
                    }

                    public int getCount() {
                        return count;
                    }

                    public void setCount(int count) {
                        this.count = count;
                    }

                    public int getState() {
                        return state;
                    }

                    public void setState(int state) {
                        this.state = state;
                    }

                    public int getRules() {
                        return rules;
                    }

                    public void setRules(int rules) {
                        this.rules = rules;
                    }

                    public long getBeginValidityTime() {
                        return beginValidityTime;
                    }

                    public void setBeginValidityTime(long beginValidityTime) {
                        this.beginValidityTime = beginValidityTime;
                    }

                    public long getEndValidityTime() {
                        return endValidityTime;
                    }

                    public void setEndValidityTime(long endValidityTime) {
                        this.endValidityTime = endValidityTime;
                    }

                    public long getBeginTime() {
                        return beginTime;
                    }

                    public void setBeginTime(long beginTime) {
                        this.beginTime = beginTime;
                    }

                    public long getEndTime() {
                        return endTime;
                    }

                    public void setEndTime(long endTime) {
                        this.endTime = endTime;
                    }

                    public String getOperatorIdentifier() {
                        return operatorIdentifier;
                    }

                    public void setOperatorIdentifier(String operatorIdentifier) {
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
                }
            }
        }

        public static class ActivitysBean {
            /**
             * activityInformation : {"id":32,"identifier":null,"name":"1234","activityType":1,"price":0,"discount":20,"maxNum":1,"introduction":null,"messagePicUrl":null,"showPicUrl":null,"budget":0,"beginValidityTime":1514908800000,"endValidityTime":1520697600000,"state":4,"operatorIdentifier":null,"operatorTime":0,"user":null,"goods":null,"goodsState":null,"coupon":null}
             * afterDiscountMoney : 20
             * userCouponMsgList : [{"userCoupons":{"id":21,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513934026000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null},"afterDiscountMoney":15},{"userCoupons":{"id":15,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513836866000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null},"afterDiscountMoney":15}]
             */

            private ActivityInformationBean activityInformation;
            private double afterDiscountMoney;
            private List<Integer> goodsSpeIdList;
            private List<UserCouponMsgListBean> userCouponMsgList;

            public List<Integer> getGoodsSpeIdList() {
                return goodsSpeIdList;
            }

            public void setGoodsSpeIdList(List<Integer> goodsSpeIdList) {
                this.goodsSpeIdList = goodsSpeIdList;
            }

            public ActivityInformationBean getActivityInformation() {
                return activityInformation;
            }

            public void setActivityInformation(ActivityInformationBean activityInformation) {
                this.activityInformation = activityInformation;
            }

            public double getAfterDiscountMoney() {
                return afterDiscountMoney;
            }

            public void setAfterDiscountMoney(double afterDiscountMoney) {
                this.afterDiscountMoney = afterDiscountMoney;
            }

            public List<UserCouponMsgListBean> getUserCouponMsgList() {
                return userCouponMsgList;
            }

            public void setUserCouponMsgList(List<UserCouponMsgListBean> userCouponMsgList) {
                this.userCouponMsgList = userCouponMsgList;
            }

            public static class ActivityInformationBean {
                /**
                 * id : 32
                 * identifier : null
                 * name : 1234
                 * activityType : 1
                 * price : 0
                 * discount : 20
                 * maxNum : 1
                 * introduction : null
                 * messagePicUrl : null
                 * showPicUrl : null
                 * budget : 0
                 * beginValidityTime : 1514908800000
                 * endValidityTime : 1520697600000
                 * state : 4
                 * operatorIdentifier : null
                 * operatorTime : 0
                 * user : null
                 * goods : null
                 * goodsState : null
                 * coupon : null
                 */

                private int id;
                private Object identifier;
                private String name;
                private int activityType;
                private double price;
                private double discount;
                private int maxNum;
                private Object introduction;
                private Object messagePicUrl;
                private Object showPicUrl;
                private double budget;
                private long beginValidityTime;
                private long endValidityTime;
                private int state;
                private Object operatorIdentifier;
                private long operatorTime;
                private Object user;
                private Object goods;
                private Object goodsState;
                private Object coupon;

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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getActivityType() {
                    return activityType;
                }

                public void setActivityType(int activityType) {
                    this.activityType = activityType;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getDiscount() {
                    return discount;
                }

                public void setDiscount(double discount) {
                    this.discount = discount;
                }

                public int getMaxNum() {
                    return maxNum;
                }

                public void setMaxNum(int maxNum) {
                    this.maxNum = maxNum;
                }

                public Object getIntroduction() {
                    return introduction;
                }

                public void setIntroduction(Object introduction) {
                    this.introduction = introduction;
                }

                public Object getMessagePicUrl() {
                    return messagePicUrl;
                }

                public void setMessagePicUrl(Object messagePicUrl) {
                    this.messagePicUrl = messagePicUrl;
                }

                public Object getShowPicUrl() {
                    return showPicUrl;
                }

                public void setShowPicUrl(Object showPicUrl) {
                    this.showPicUrl = showPicUrl;
                }

                public double getBudget() {
                    return budget;
                }

                public void setBudget(double budget) {
                    this.budget = budget;
                }

                public long getBeginValidityTime() {
                    return beginValidityTime;
                }

                public void setBeginValidityTime(long beginValidityTime) {
                    this.beginValidityTime = beginValidityTime;
                }

                public long getEndValidityTime() {
                    return endValidityTime;
                }

                public void setEndValidityTime(long endValidityTime) {
                    this.endValidityTime = endValidityTime;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
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

                public Object getGoods() {
                    return goods;
                }

                public void setGoods(Object goods) {
                    this.goods = goods;
                }

                public Object getGoodsState() {
                    return goodsState;
                }

                public void setGoodsState(Object goodsState) {
                    this.goodsState = goodsState;
                }

                public Object getCoupon() {
                    return coupon;
                }

                public void setCoupon(Object coupon) {
                    this.coupon = coupon;
                }
            }

            public static class UserCouponMsgListBean {
                /**
                 * userCoupons : {"id":21,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513934026000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":null,"useInstruction":null}
                 * afterDiscountMoney : 15
                 */

                private UserCouponsBeanX userCoupons;
                private double afterDiscountMoney;

                public UserCouponsBeanX getUserCoupons() {
                    return userCoupons;
                }

                public void setUserCoupons(UserCouponsBeanX userCoupons) {
                    this.userCoupons = userCoupons;
                }

                public double getAfterDiscountMoney() {
                    return afterDiscountMoney;
                }

                public void setAfterDiscountMoney(double afterDiscountMoney) {
                    this.afterDiscountMoney = afterDiscountMoney;
                }

                public static class UserCouponsBeanX {
                    /**
                     * id : 21
                     * userId : 22
                     * couponInformationId : 5
                     * status : 0
                     * useTime : null
                     * getTime : 1513934026000
                     * couponInformation : {"id":5,"identifier":"JLCOU20171124105645","name":"111","price":5,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null}
                     * stateMsg : null
                     * useInstruction : null
                     */

                    private int id;
                    private int userId;
                    private int couponInformationId;
                    private int status;
                    private Object useTime;
                    private long getTime;
                    private CouponInformationBeanX couponInformation;
                    private Object stateMsg;
                    private Object useInstruction;

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

                    public int getCouponInformationId() {
                        return couponInformationId;
                    }

                    public void setCouponInformationId(int couponInformationId) {
                        this.couponInformationId = couponInformationId;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public Object getUseTime() {
                        return useTime;
                    }

                    public void setUseTime(Object useTime) {
                        this.useTime = useTime;
                    }

                    public long getGetTime() {
                        return getTime;
                    }

                    public void setGetTime(long getTime) {
                        this.getTime = getTime;
                    }

                    public CouponInformationBeanX getCouponInformation() {
                        return couponInformation;
                    }

                    public void setCouponInformation(CouponInformationBeanX couponInformation) {
                        this.couponInformation = couponInformation;
                    }

                    public Object getStateMsg() {
                        return stateMsg;
                    }

                    public void setStateMsg(Object stateMsg) {
                        this.stateMsg = stateMsg;
                    }

                    public Object getUseInstruction() {
                        return useInstruction;
                    }

                    public void setUseInstruction(Object useInstruction) {
                        this.useInstruction = useInstruction;
                    }

                    public static class CouponInformationBeanX {
                        /**
                         * id : 5
                         * identifier : JLCOU20171124105645
                         * name : 111
                         * price : 5
                         * total : 333
                         * useLimit : 5
                         * count : 333
                         * state : 0
                         * rules : 0
                         * beginValidityTime : 1511884800000
                         * endValidityTime : 1518105600000
                         * beginTime : 1511884800000
                         * endTime : 1518105600000
                         * operatorIdentifier : JLADMIN20171113192605
                         * operatorTime : 1511492210000
                         * user : null
                         */

                        private int id;
                        private String identifier;
                        private String name;
                        private double price;
                        private int total;
                        private double useLimit;
                        private int count;
                        private int state;
                        private int rules;
                        private long beginValidityTime;
                        private long endValidityTime;
                        private long beginTime;
                        private long endTime;
                        private String operatorIdentifier;
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

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public double getPrice() {
                            return price;
                        }

                        public void setPrice(double price) {
                            this.price = price;
                        }

                        public int getTotal() {
                            return total;
                        }

                        public void setTotal(int total) {
                            this.total = total;
                        }

                        public double getUseLimit() {
                            return useLimit;
                        }

                        public void setUseLimit(double useLimit) {
                            this.useLimit = useLimit;
                        }

                        public int getCount() {
                            return count;
                        }

                        public void setCount(int count) {
                            this.count = count;
                        }

                        public int getState() {
                            return state;
                        }

                        public void setState(int state) {
                            this.state = state;
                        }

                        public int getRules() {
                            return rules;
                        }

                        public void setRules(int rules) {
                            this.rules = rules;
                        }

                        public long getBeginValidityTime() {
                            return beginValidityTime;
                        }

                        public void setBeginValidityTime(long beginValidityTime) {
                            this.beginValidityTime = beginValidityTime;
                        }

                        public long getEndValidityTime() {
                            return endValidityTime;
                        }

                        public void setEndValidityTime(long endValidityTime) {
                            this.endValidityTime = endValidityTime;
                        }

                        public long getBeginTime() {
                            return beginTime;
                        }

                        public void setBeginTime(long beginTime) {
                            this.beginTime = beginTime;
                        }

                        public long getEndTime() {
                            return endTime;
                        }

                        public void setEndTime(long endTime) {
                            this.endTime = endTime;
                        }

                        public String getOperatorIdentifier() {
                            return operatorIdentifier;
                        }

                        public void setOperatorIdentifier(String operatorIdentifier) {
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
                    }
                }
            }
        }
    }
}
