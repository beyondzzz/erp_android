package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by THINK on 2018-03-01.
 */

public class ActivityInformationByIdModel {

    /**
     * resultData : {"couponInformation":[{"id":22,"identifier":"JLCOU20180207104928","name":"食品粮油优惠卷","price":15,"total":100,"useLimit":100,"count":100,"state":0,"rules":1,"beginValidityTime":1517932800000,"endValidityTime":1524844800000,"beginTime":1517932800000,"endTime":1521648000000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517971768000,"user":null}],"goods":[{"id":21,"classificationId":72,"name":"多力葵花籽油","keyWord":"粮油","introdution":"这是优选的压榨葵花籽油。","recom":1,"recomTime":1517970809000,"details":"<p><img src=\"http://117.158.178.202:8000/JLMIS/GoodsEditorPics/1517970221588.jpg\" style=\"max-width:100%;\"><\/p>","classification":null,"goodsSpecificationDetails":[{"id":32,"identifier":"JLGOODS20180207101813","specifications":"5L*1","saleId":1,"price":72.9,"goodsId":21,"salesCount":0,"state":0,"onShelvesTime":1517932800000,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517969893000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":1,"gxcGoodsStock":444,"gxcPurchase":30,"brand":"品牌一","goodsDisplayPictures":[{"id":96,"picUrl":"GoodsDisplayPicture/1517971120665.jpg","goodsSpecificationDetailId":32}],"goodsActivitys":[{"id":162,"goodsId":32,"activityInformationId":38,"state":0,"goodsSpecificationDetails":null,"goodsDetails":null,"classification":null,"activityInformation":{"id":38,"identifier":"JLACT20180207114303","name":"金狗迎春福气到 财源滚滚好事来","activityType":2,"price":null,"discount":66.6,"maxNum":1,"introduction":null,"messagePicUrl":null,"showPicUrl":null,"budget":null,"beginValidityTime":1518537600000,"endValidityTime":1522425600000,"state":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goods":null,"goodsState":null,"coupon":null}}]}],"saleCount":0,"goodsEvaluations":[],"goodsActivitys":[]}],"activityInformation":{"id":38,"identifier":"JLACT20180207114303","name":"金狗迎春福气到 财源滚滚好事来","activityType":2,"price":null,"discount":66.6,"maxNum":1,"introduction":"皑皑","messagePicUrl":"ActivityMessagePicture/1517974983138.jpg","showPicUrl":"ActivityShowPicture/1517974983154.jpg","budget":1563,"beginValidityTime":1518537600000,"endValidityTime":1522425600000,"state":4,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517974983000,"user":null,"goods":null,"goodsState":null,"coupon":null}}
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
         * couponInformation : [{"id":22,"identifier":"JLCOU20180207104928","name":"食品粮油优惠卷","price":15,"total":100,"useLimit":100,"count":100,"state":0,"rules":1,"beginValidityTime":1517932800000,"endValidityTime":1524844800000,"beginTime":1517932800000,"endTime":1521648000000,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517971768000,"user":null}]
         * goods : [{"id":21,"classificationId":72,"name":"多力葵花籽油","keyWord":"粮油","introdution":"这是优选的压榨葵花籽油。","recom":1,"recomTime":1517970809000,"details":"<p><img src=\"http://117.158.178.202:8000/JLMIS/GoodsEditorPics/1517970221588.jpg\" style=\"max-width:100%;\"><\/p>","classification":null,"goodsSpecificationDetails":[{"id":32,"identifier":"JLGOODS20180207101813","specifications":"5L*1","saleId":1,"price":72.9,"goodsId":21,"salesCount":0,"state":0,"onShelvesTime":1517932800000,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517969893000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":1,"gxcGoodsStock":444,"gxcPurchase":30,"brand":"品牌一","goodsDisplayPictures":[{"id":96,"picUrl":"GoodsDisplayPicture/1517971120665.jpg","goodsSpecificationDetailId":32}],"goodsActivitys":[{"id":162,"goodsId":32,"activityInformationId":38,"state":0,"goodsSpecificationDetails":null,"goodsDetails":null,"classification":null,"activityInformation":{"id":38,"identifier":"JLACT20180207114303","name":"金狗迎春福气到 财源滚滚好事来","activityType":2,"price":null,"discount":66.6,"maxNum":1,"introduction":null,"messagePicUrl":null,"showPicUrl":null,"budget":null,"beginValidityTime":1518537600000,"endValidityTime":1522425600000,"state":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goods":null,"goodsState":null,"coupon":null}}]}],"saleCount":0,"goodsEvaluations":[],"goodsActivitys":[]}]
         * activityInformation : {"id":38,"identifier":"JLACT20180207114303","name":"金狗迎春福气到 财源滚滚好事来","activityType":2,"price":null,"discount":66.6,"maxNum":1,"introduction":"皑皑","messagePicUrl":"ActivityMessagePicture/1517974983138.jpg","showPicUrl":"ActivityShowPicture/1517974983154.jpg","budget":1563,"beginValidityTime":1518537600000,"endValidityTime":1522425600000,"state":4,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517974983000,"user":null,"goods":null,"goodsState":null,"coupon":null}
         */

        private ActivityInformationBean activityInformation;
        private List<CouponInformationBean> couponInformation;
        private List<GoodsBean> goods;

        public ActivityInformationBean getActivityInformation() {
            return activityInformation;
        }

        public void setActivityInformation(ActivityInformationBean activityInformation) {
            this.activityInformation = activityInformation;
        }

        public List<CouponInformationBean> getCouponInformation() {
            return couponInformation;
        }

        public void setCouponInformation(List<CouponInformationBean> couponInformation) {
            this.couponInformation = couponInformation;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class ActivityInformationBean {
            /**
             * id : 38
             * identifier : JLACT20180207114303
             * name : 金狗迎春福气到 财源滚滚好事来
             * activityType : 2
             * price : null
             * discount : 66.6
             * maxNum : 1
             * introduction : 皑皑
             * messagePicUrl : ActivityMessagePicture/1517974983138.jpg
             * showPicUrl : ActivityShowPicture/1517974983154.jpg
             * budget : 1563
             * beginValidityTime : 1518537600000
             * endValidityTime : 1522425600000
             * state : 4
             * operatorIdentifier : JLADMIN20171113192605
             * operatorTime : 1517974983000
             * user : null
             * goods : null
             * goodsState : null
             * coupon : null
             */

            private int id;
            private String identifier;
            private String name;
            private int activityType;
            private double price;
            private double discount;
            private int maxNum;
            private String introduction;
            private String messagePicUrl;
            private String showPicUrl;
            private double budget;
            private long beginValidityTime;
            private long endValidityTime;
            private int state;
            private String operatorIdentifier;
            private long operatorTime;
            private Object user;
            private Object goods;
            private Object goodsState;
            private Object coupon;

            public int getId() {
                return id;
            }

            public double getPrice() {
                return price;
            }

            public double getBudget() {
                return budget;
            }

            public void setBudget(double budget) {
                this.budget = budget;
            }

            public void setPrice(double price) {
                this.price = price;
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

            public int getActivityType() {
                return activityType;
            }

            public void setActivityType(int activityType) {
                this.activityType = activityType;
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

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getMessagePicUrl() {
                return messagePicUrl;
            }

            public void setMessagePicUrl(String messagePicUrl) {
                this.messagePicUrl = messagePicUrl;
            }

            public String getShowPicUrl() {
                return showPicUrl;
            }

            public void setShowPicUrl(String showPicUrl) {
                this.showPicUrl = showPicUrl;
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

        public static class CouponInformationBean {
            /**
             * id : 22
             * identifier : JLCOU20180207104928
             * name : 食品粮油优惠卷
             * price : 15
             * total : 100
             * useLimit : 100
             * count : 100
             * state : 0
             * rules : 1
             * beginValidityTime : 1517932800000
             * endValidityTime : 1524844800000
             * beginTime : 1517932800000
             * endTime : 1521648000000
             * operatorIdentifier : JLADMIN20171113192605
             * operatorTime : 1517971768000
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



            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

//

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
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

        public static class GoodsBean {
            /**
             * id : 21
             * classificationId : 72
             * name : 多力葵花籽油
             * keyWord : 粮油
             * introdution : 这是优选的压榨葵花籽油。
             * recom : 1
             * recomTime : 1517970809000
             * details : <p><img src="http://117.158.178.202:8000/JLMIS/GoodsEditorPics/1517970221588.jpg" style="max-width:100%;"></p>
             * classification : null
             * goodsSpecificationDetails : [{"id":32,"identifier":"JLGOODS20180207101813","specifications":"5L*1","saleId":1,"price":72.9,"goodsId":21,"salesCount":0,"state":0,"onShelvesTime":1517932800000,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1517969893000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":1,"gxcGoodsStock":444,"gxcPurchase":30,"brand":"品牌一","goodsDisplayPictures":[{"id":96,"picUrl":"GoodsDisplayPicture/1517971120665.jpg","goodsSpecificationDetailId":32}],"goodsActivitys":[{"id":162,"goodsId":32,"activityInformationId":38,"state":0,"goodsSpecificationDetails":null,"goodsDetails":null,"classification":null,"activityInformation":{"id":38,"identifier":"JLACT20180207114303","name":"金狗迎春福气到 财源滚滚好事来","activityType":2,"price":null,"discount":66.6,"maxNum":1,"introduction":null,"messagePicUrl":null,"showPicUrl":null,"budget":null,"beginValidityTime":1518537600000,"endValidityTime":1522425600000,"state":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goods":null,"goodsState":null,"coupon":null}}]}]
             * saleCount : 0
             * goodsEvaluations : []
             * goodsActivitys : []
             */

            private int id;
            private int classificationId;
            private String name;
            private String keyWord;
            private String introdution;
            private int recom;
            private long recomTime;
            private String details;
            private Object classification;
            private int saleCount;
            private List<GoodsSpecificationDetailsBean> goodsSpecificationDetails;
            private List<?> goodsEvaluations;
            private List<?> goodsActivitys;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getClassificationId() {
                return classificationId;
            }

            public void setClassificationId(int classificationId) {
                this.classificationId = classificationId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKeyWord() {
                return keyWord;
            }

            public void setKeyWord(String keyWord) {
                this.keyWord = keyWord;
            }

            public String getIntrodution() {
                return introdution;
            }

            public void setIntrodution(String introdution) {
                this.introdution = introdution;
            }

            public int getRecom() {
                return recom;
            }

            public void setRecom(int recom) {
                this.recom = recom;
            }

            public long getRecomTime() {
                return recomTime;
            }

            public void setRecomTime(long recomTime) {
                this.recomTime = recomTime;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public Object getClassification() {
                return classification;
            }

            public void setClassification(Object classification) {
                this.classification = classification;
            }

            public int getSaleCount() {
                return saleCount;
            }

            public void setSaleCount(int saleCount) {
                this.saleCount = saleCount;
            }

            public List<GoodsSpecificationDetailsBean> getGoodsSpecificationDetails() {
                return goodsSpecificationDetails;
            }

            public void setGoodsSpecificationDetails(List<GoodsSpecificationDetailsBean> goodsSpecificationDetails) {
                this.goodsSpecificationDetails = goodsSpecificationDetails;
            }

            public List<?> getGoodsEvaluations() {
                return goodsEvaluations;
            }

            public void setGoodsEvaluations(List<?> goodsEvaluations) {
                this.goodsEvaluations = goodsEvaluations;
            }

            public List<?> getGoodsActivitys() {
                return goodsActivitys;
            }

            public void setGoodsActivitys(List<?> goodsActivitys) {
                this.goodsActivitys = goodsActivitys;
            }

            public static class GoodsSpecificationDetailsBean {
                /**
                 * id : 32
                 * identifier : JLGOODS20180207101813
                 * specifications : 5L*1
                 * saleId : 1
                 * price : 72.9
                 * goodsId : 21
                 * salesCount : 0
                 * state : 0
                 * onShelvesTime : 1517932800000
                 * offShelvesTime : null
                 * operatorIdentifier : JLADMIN20171113192605
                 * operatorTime : 1517969893000
                 * user : null
                 * goodsDetails : null
                 * participateActivities : null
                 * participateActivitieList : null
                 * gxcGoodsState : 1
                 * gxcGoodsStock : 444
                 * gxcPurchase : 30
                 * brand : 品牌一
                 * goodsDisplayPictures : [{"id":96,"picUrl":"GoodsDisplayPicture/1517971120665.jpg","goodsSpecificationDetailId":32}]
                 * goodsActivitys : [{"id":162,"goodsId":32,"activityInformationId":38,"state":0,"goodsSpecificationDetails":null,"goodsDetails":null,"classification":null,"activityInformation":{"id":38,"identifier":"JLACT20180207114303","name":"金狗迎春福气到 财源滚滚好事来","activityType":2,"price":null,"discount":66.6,"maxNum":1,"introduction":null,"messagePicUrl":null,"showPicUrl":null,"budget":null,"beginValidityTime":1518537600000,"endValidityTime":1522425600000,"state":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goods":null,"goodsState":null,"coupon":null}}]
                 */

                private int id;
                private String identifier;
                private String specifications;
                private int saleId;
                private double price;
                private double oldPrice;
                private int goodsId;
                private int salesCount;
                private int state;
                private long onShelvesTime;
                private Object offShelvesTime;
                private String operatorIdentifier;
                private long operatorTime;
                private Object user;
                private Object goodsDetails;
                private Object participateActivities;
                private Object participateActivitieList;
                private int gxcGoodsState;
                private int gxcGoodsStock;
                private double gxcPurchase;
                private String brand;
                private List<GoodsDisplayPicturesBean> goodsDisplayPictures;
                private List<GoodsActivitysBean> goodsActivitys;

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

                public String getSpecifications() {
                    return specifications;
                }

                public void setSpecifications(String specifications) {
                    this.specifications = specifications;
                }

                public int getSaleId() {
                    return saleId;
                }

                public void setSaleId(int saleId) {
                    this.saleId = saleId;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getOldPrice() {
                    return oldPrice;
                }

                public void setOldPrice(double oldPrice) {
                    this.oldPrice = oldPrice;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public int getSalesCount() {
                    return salesCount;
                }

                public void setSalesCount(int salesCount) {
                    this.salesCount = salesCount;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public long getOnShelvesTime() {
                    return onShelvesTime;
                }

                public void setOnShelvesTime(long onShelvesTime) {
                    this.onShelvesTime = onShelvesTime;
                }

                public Object getOffShelvesTime() {
                    return offShelvesTime;
                }

                public void setOffShelvesTime(Object offShelvesTime) {
                    this.offShelvesTime = offShelvesTime;
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

                public Object getGoodsDetails() {
                    return goodsDetails;
                }

                public void setGoodsDetails(Object goodsDetails) {
                    this.goodsDetails = goodsDetails;
                }

                public Object getParticipateActivities() {
                    return participateActivities;
                }

                public void setParticipateActivities(Object participateActivities) {
                    this.participateActivities = participateActivities;
                }

                public Object getParticipateActivitieList() {
                    return participateActivitieList;
                }

                public void setParticipateActivitieList(Object participateActivitieList) {
                    this.participateActivitieList = participateActivitieList;
                }

                public int getGxcGoodsState() {
                    return gxcGoodsState;
                }

                public void setGxcGoodsState(int gxcGoodsState) {
                    this.gxcGoodsState = gxcGoodsState;
                }

                public int getGxcGoodsStock() {
                    return gxcGoodsStock;
                }

                public void setGxcGoodsStock(int gxcGoodsStock) {
                    this.gxcGoodsStock = gxcGoodsStock;
                }

                public double getGxcPurchase() {
                    return gxcPurchase;
                }

                public void setGxcPurchase(double gxcPurchase) {
                    this.gxcPurchase = gxcPurchase;
                }

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }

                public List<GoodsDisplayPicturesBean> getGoodsDisplayPictures() {
                    return goodsDisplayPictures;
                }

                public void setGoodsDisplayPictures(List<GoodsDisplayPicturesBean> goodsDisplayPictures) {
                    this.goodsDisplayPictures = goodsDisplayPictures;
                }

                public List<GoodsActivitysBean> getGoodsActivitys() {
                    return goodsActivitys;
                }

                public void setGoodsActivitys(List<GoodsActivitysBean> goodsActivitys) {
                    this.goodsActivitys = goodsActivitys;
                }

                public static class GoodsDisplayPicturesBean {
                    /**
                     * id : 96
                     * picUrl : GoodsDisplayPicture/1517971120665.jpg
                     * goodsSpecificationDetailId : 32
                     */

                    private int id;
                    private String picUrl;
                    private int goodsSpecificationDetailId;

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

                    public int getGoodsSpecificationDetailId() {
                        return goodsSpecificationDetailId;
                    }

                    public void setGoodsSpecificationDetailId(int goodsSpecificationDetailId) {
                        this.goodsSpecificationDetailId = goodsSpecificationDetailId;
                    }
                }

                public static class GoodsActivitysBean {
                    /**
                     * id : 162
                     * goodsId : 32
                     * activityInformationId : 38
                     * state : 0
                     * goodsSpecificationDetails : null
                     * goodsDetails : null
                     * classification : null
                     * activityInformation : {"id":38,"identifier":"JLACT20180207114303","name":"金狗迎春福气到 财源滚滚好事来","activityType":2,"price":null,"discount":66.6,"maxNum":1,"introduction":null,"messagePicUrl":null,"showPicUrl":null,"budget":null,"beginValidityTime":1518537600000,"endValidityTime":1522425600000,"state":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goods":null,"goodsState":null,"coupon":null}
                     */

                    private int id;
                    private int goodsId;
                    private int activityInformationId;
                    private int state;
                    private Object goodsSpecificationDetails;
                    private Object goodsDetails;
                    private Object classification;
                    private ActivityInformationBeanX activityInformation;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getGoodsId() {
                        return goodsId;
                    }

                    public void setGoodsId(int goodsId) {
                        this.goodsId = goodsId;
                    }

                    public int getActivityInformationId() {
                        return activityInformationId;
                    }

                    public void setActivityInformationId(int activityInformationId) {
                        this.activityInformationId = activityInformationId;
                    }

                    public int getState() {
                        return state;
                    }

                    public void setState(int state) {
                        this.state = state;
                    }

                    public Object getGoodsSpecificationDetails() {
                        return goodsSpecificationDetails;
                    }

                    public void setGoodsSpecificationDetails(Object goodsSpecificationDetails) {
                        this.goodsSpecificationDetails = goodsSpecificationDetails;
                    }

                    public Object getGoodsDetails() {
                        return goodsDetails;
                    }

                    public void setGoodsDetails(Object goodsDetails) {
                        this.goodsDetails = goodsDetails;
                    }

                    public Object getClassification() {
                        return classification;
                    }

                    public void setClassification(Object classification) {
                        this.classification = classification;
                    }

                    public ActivityInformationBeanX getActivityInformation() {
                        return activityInformation;
                    }

                    public void setActivityInformation(ActivityInformationBeanX activityInformation) {
                        this.activityInformation = activityInformation;
                    }

                    public static class ActivityInformationBeanX {
                        /**
                         * id : 38
                         * identifier : JLACT20180207114303
                         * name : 金狗迎春福气到 财源滚滚好事来
                         * activityType : 2
                         * price : null
                         * discount : 66.6
                         * maxNum : 1
                         * introduction : null
                         * messagePicUrl : null
                         * showPicUrl : null
                         * budget : null
                         * beginValidityTime : 1518537600000
                         * endValidityTime : 1522425600000
                         * state : null
                         * operatorIdentifier : null
                         * operatorTime : null
                         * user : null
                         * goods : null
                         * goodsState : null
                         * coupon : null
                         */

                        private int id;
                        private String identifier;
                        private String name;
                        private int activityType;
                        private Object price;
                        private double discount;
                        private int maxNum;
                        private Object introduction;
                        private Object messagePicUrl;
                        private Object showPicUrl;
                        private Object budget;
                        private long beginValidityTime;
                        private long endValidityTime;
                        private Object state;
                        private Object operatorIdentifier;
                        private Object operatorTime;
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

                        public int getActivityType() {
                            return activityType;
                        }

                        public void setActivityType(int activityType) {
                            this.activityType = activityType;
                        }

                        public Object getPrice() {
                            return price;
                        }

                        public void setPrice(Object price) {
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

                        public Object getBudget() {
                            return budget;
                        }

                        public void setBudget(Object budget) {
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

                        public Object getState() {
                            return state;
                        }

                        public void setState(Object state) {
                            this.state = state;
                        }

                        public Object getOperatorIdentifier() {
                            return operatorIdentifier;
                        }

                        public void setOperatorIdentifier(Object operatorIdentifier) {
                            this.operatorIdentifier = operatorIdentifier;
                        }

                        public Object getOperatorTime() {
                            return operatorTime;
                        }

                        public void setOperatorTime(Object operatorTime) {
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
                }
            }
        }
    }
}
