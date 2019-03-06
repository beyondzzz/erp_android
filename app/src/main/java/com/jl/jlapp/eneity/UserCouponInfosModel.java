package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/2/5 0005.
 */

public class UserCouponInfosModel {

    /**
     * msg : 请求成功
     * code : 200
     * resultData : {"usableCoupon":[{"id":21,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513934026000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":10,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":"可使用","useInstruction":"限购部分粮油类商品"},{"id":15,"userId":22,"couponInformationId":5,"status":0,"useTime":null,"getTime":1513836866000,"couponInformation":{"id":5,"identifier":"JLCOU20171124105645","name":"111","price":10,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null},"stateMsg":"可使用","useInstruction":"限购部分粮油类商品"}],"disabledCoupon":[{"id":20,"userId":22,"couponInformationId":2,"status":1,"useTime":1515490421000,"getTime":1513934026000,"couponInformation":{"id":2,"identifier":"JLCOU20171108103342","name":"生鲜优惠券","price":5,"total":500,"useLimit":20,"count":500,"state":0,"rules":2,"beginValidityTime":1512057600000,"endValidityTime":1517414400000,"beginTime":1512057600000,"endTime":1517414400000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1510108429000,"user":null},"stateMsg":"已使用","useInstruction":"限购部分粮油类、法规和法国类商品"}]}
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
        private List<UsableCouponBean> usableCoupon;
        private List<DisabledCouponBean> disabledCoupon;

        public List<UsableCouponBean> getUsableCoupon() {
            return usableCoupon;
        }

        public void setUsableCoupon(List<UsableCouponBean> usableCoupon) {
            this.usableCoupon = usableCoupon;
        }

        public List<DisabledCouponBean> getDisabledCoupon() {
            return disabledCoupon;
        }

        public void setDisabledCoupon(List<DisabledCouponBean> disabledCoupon) {
            this.disabledCoupon = disabledCoupon;
        }

        public static class UsableCouponBean {
            /**
             * id : 21
             * userId : 22
             * couponInformationId : 5
             * status : 0
             * useTime : null
             * getTime : 1513934026000
             * couponInformation : {"id":5,"identifier":"JLCOU20171124105645","name":"111","price":10,"total":333,"useLimit":5,"count":333,"state":0,"rules":0,"beginValidityTime":1511884800000,"endValidityTime":1518105600000,"beginTime":1511884800000,"endTime":1518105600000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511492210000,"user":null}
             * stateMsg : 可使用
             * useInstruction : 限购部分粮油类商品
             */

            private int id;
            private int userId;
            private int couponInformationId;
            private int status;
            private Object useTime;
            private long getTime;
            private CouponInformationBean couponInformation;
            private String stateMsg;
            private String useInstruction;

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

            public String getStateMsg() {
                return stateMsg;
            }

            public void setStateMsg(String stateMsg) {
                this.stateMsg = stateMsg;
            }

            public String getUseInstruction() {
                return useInstruction;
            }

            public void setUseInstruction(String useInstruction) {
                this.useInstruction = useInstruction;
            }

            public static class CouponInformationBean {
                /**
                 * id : 5
                 * identifier : JLCOU20171124105645
                 * name : 111
                 * price : 10
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

        public static class DisabledCouponBean {
            /**
             * id : 20
             * userId : 22
             * couponInformationId : 2
             * status : 1
             * useTime : 1515490421000
             * getTime : 1513934026000
             * couponInformation : {"id":2,"identifier":"JLCOU20171108103342","name":"生鲜优惠券","price":5,"total":500,"useLimit":20,"count":500,"state":0,"rules":2,"beginValidityTime":1512057600000,"endValidityTime":1517414400000,"beginTime":1512057600000,"endTime":1517414400000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1510108429000,"user":null}
             * stateMsg : 已使用
             * useInstruction : 限购部分粮油类、法规和法国类商品
             */

            private int id;
            private int userId;
            private int couponInformationId;
            private int status;
            private long useTime;
            private long getTime;
            private CouponInformationBeanX couponInformation;
            private String stateMsg;
            private String useInstruction;

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

            public long getUseTime() {
                return useTime;
            }

            public void setUseTime(long useTime) {
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

            public String getStateMsg() {
                return stateMsg;
            }

            public void setStateMsg(String stateMsg) {
                this.stateMsg = stateMsg;
            }

            public String getUseInstruction() {
                return useInstruction;
            }

            public void setUseInstruction(String useInstruction) {
                this.useInstruction = useInstruction;
            }

            public static class CouponInformationBeanX {
                /**
                 * id : 2
                 * identifier : JLCOU20171108103342
                 * name : 生鲜优惠券
                 * price : 5
                 * total : 500
                 * useLimit : 20
                 * count : 500
                 * state : 0
                 * rules : 2
                 * beginValidityTime : 1512057600000
                 * endValidityTime : 1517414400000
                 * beginTime : 1512057600000
                 * endTime : 1517414400000
                 * operatorIdentifier : JLADMIN20171113192605
                 * operatorTime : 1510108429000
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
