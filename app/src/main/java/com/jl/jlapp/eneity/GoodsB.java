package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/29 0029.
 */

public class GoodsB {


    /**
     * resultData : {"id":1,"classificationId":38,"name":"福临门大米","keyWord":"米啊啊啊","introdution":"米米米啊啊啊","recom":null,"recomTime":null,"details":"<p style=\"text-align: center;\"><img src=\"http://117.158.178.202:8000/JLMIS/GoodsEditorPics/1517212150561.png\" style=\"max-width:100%;\"><\/p><p style=\"text-align: center;\"><img src=\"http://117.158.178.202:8000/JLMIS/GoodsEditorPics/1517212161622.png\" style=\"max-width:100%;\"><\/p>","classification":null,"goodsSpecificationDetails":[{"id":1,"identifier":"JLYJFL24465465","specifications":"5kg","saleId":1,"price":10,"goodsId":1,"salesCount":23,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":1,"gxcGoodsStock":10,"brand":"品牌一","goodsDisplayPictures":[{"id":83,"picUrl":"GoodsDisplayPicture/1516957929546.png","goodsSpecificationDetailId":1}],"goodsActivitys":[]},{"id":4,"identifier":"JLGOODS20171121113219","specifications":"15kg","saleId":2,"price":12.5,"goodsId":1,"salesCount":2,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511235140000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":0,"gxcGoodsStock":274,"brand":"品牌二","goodsDisplayPictures":[{"id":62,"picUrl":"GoodsDisplayPicture/1516786738730.png","goodsSpecificationDetailId":4},{"id":63,"picUrl":"GoodsDisplayPicture/1516786738761.png","goodsSpecificationDetailId":4}],"goodsActivitys":[]},{"id":15,"identifier":"JLGOODS20171121201314","specifications":"12kg","saleId":1,"price":13,"goodsId":1,"salesCount":null,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511266394000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":1,"gxcGoodsStock":10,"brand":"品牌一","goodsDisplayPictures":[{"id":68,"picUrl":"GoodsDisplayPicture/1516954563232.png","goodsSpecificationDetailId":15},{"id":86,"picUrl":"GoodsDisplayPicture/1516958074517.png","goodsSpecificationDetailId":15},{"id":87,"picUrl":"GoodsDisplayPicture/1516958074517.png","goodsSpecificationDetailId":15},{"id":88,"picUrl":"GoodsDisplayPicture/1516958074533.png","goodsSpecificationDetailId":15},{"id":89,"picUrl":"GoodsDisplayPicture/1516958074533.png","goodsSpecificationDetailId":15}],"goodsActivitys":[]},{"id":2,"identifier":"JLYJFL28798545","specifications":"10kg","saleId":2,"price":20,"goodsId":1,"salesCount":4,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":0,"gxcGoodsStock":274,"brand":"品牌二","goodsDisplayPictures":[{"id":82,"picUrl":"GoodsDisplayPicture/1516957909391.png","goodsSpecificationDetailId":2}],"goodsActivitys":[]}],"saleCount":29,"goodsEvaluations":[],"goodsActivitys":[]}
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
         * id : 1
         * classificationId : 38
         * name : 福临门大米
         * keyWord : 米啊啊啊
         * introdution : 米米米啊啊啊
         * recom : null
         * recomTime : null
         * details : <p style="text-align: center;"><img src="http://117.158.178.202:8000/JLMIS/GoodsEditorPics/1517212150561.png" style="max-width:100%;"></p><p style="text-align: center;"><img src="http://117.158.178.202:8000/JLMIS/GoodsEditorPics/1517212161622.png" style="max-width:100%;"></p>
         * classification : null
         * goodsSpecificationDetails : [{"id":1,"identifier":"JLYJFL24465465","specifications":"5kg","saleId":1,"price":10,"goodsId":1,"salesCount":23,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":1,"gxcGoodsStock":10,"brand":"品牌一","goodsDisplayPictures":[{"id":83,"picUrl":"GoodsDisplayPicture/1516957929546.png","goodsSpecificationDetailId":1}],"goodsActivitys":[]},{"id":4,"identifier":"JLGOODS20171121113219","specifications":"15kg","saleId":2,"price":12.5,"goodsId":1,"salesCount":2,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511235140000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":0,"gxcGoodsStock":274,"brand":"品牌二","goodsDisplayPictures":[{"id":62,"picUrl":"GoodsDisplayPicture/1516786738730.png","goodsSpecificationDetailId":4},{"id":63,"picUrl":"GoodsDisplayPicture/1516786738761.png","goodsSpecificationDetailId":4}],"goodsActivitys":[]},{"id":15,"identifier":"JLGOODS20171121201314","specifications":"12kg","saleId":1,"price":13,"goodsId":1,"salesCount":null,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511266394000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":1,"gxcGoodsStock":10,"brand":"品牌一","goodsDisplayPictures":[{"id":68,"picUrl":"GoodsDisplayPicture/1516954563232.png","goodsSpecificationDetailId":15},{"id":86,"picUrl":"GoodsDisplayPicture/1516958074517.png","goodsSpecificationDetailId":15},{"id":87,"picUrl":"GoodsDisplayPicture/1516958074517.png","goodsSpecificationDetailId":15},{"id":88,"picUrl":"GoodsDisplayPicture/1516958074533.png","goodsSpecificationDetailId":15},{"id":89,"picUrl":"GoodsDisplayPicture/1516958074533.png","goodsSpecificationDetailId":15}],"goodsActivitys":[]},{"id":2,"identifier":"JLYJFL28798545","specifications":"10kg","saleId":2,"price":20,"goodsId":1,"salesCount":4,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":0,"gxcGoodsStock":274,"brand":"品牌二","goodsDisplayPictures":[{"id":82,"picUrl":"GoodsDisplayPicture/1516957909391.png","goodsSpecificationDetailId":2}],"goodsActivitys":[]}]
         * saleCount : 29
         * goodsEvaluations : []
         * goodsActivitys : []
         */

        private int id;
        private int classificationId;
        private String name;
        private String keyWord;
        private String introdution;
        private Object recom;
        private Object recomTime;
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

        public Object getRecom() {
            return recom;
        }

        public void setRecom(Object recom) {
            this.recom = recom;
        }

        public Object getRecomTime() {
            return recomTime;
        }

        public void setRecomTime(Object recomTime) {
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
             * id : 1
             * identifier : JLYJFL24465465
             * specifications : 5kg
             * saleId : 1
             * price : 10.0
             * goodsId : 1
             * salesCount : 23
             * state : 0
             * onShelvesTime : null
             * offShelvesTime : null
             * operatorIdentifier : null
             * operatorTime : null
             * user : null
             * goodsDetails : null
             * participateActivities : null
             * participateActivitieList : null
             * gxcGoodsState : 1
             * gxcGoodsStock : 10
             * brand : 品牌一
             * goodsDisplayPictures : [{"id":83,"picUrl":"GoodsDisplayPicture/1516957929546.png","goodsSpecificationDetailId":1}]
             * goodsActivitys : []
             */

            private int id;
            private String identifier;
            private String specifications;
            private int saleId;
            private double price;
            private int goodsId;
            private int salesCount;
            private int state;
            private Object onShelvesTime;
            private Object offShelvesTime;
            private Object operatorIdentifier;
            private Object operatorTime;
            private Object user;
            private Object goodsDetails;
            private Object participateActivities;
            private Object participateActivitieList;
            private int gxcGoodsState;
            private int gxcGoodsStock;
            private String brand;
            private List<GoodsDisplayPicturesBean> goodsDisplayPictures;
            private List<?> goodsActivitys;

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

            public Object getOnShelvesTime() {
                return onShelvesTime;
            }

            public void setOnShelvesTime(Object onShelvesTime) {
                this.onShelvesTime = onShelvesTime;
            }

            public Object getOffShelvesTime() {
                return offShelvesTime;
            }

            public void setOffShelvesTime(Object offShelvesTime) {
                this.offShelvesTime = offShelvesTime;
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

            public List<?> getGoodsActivitys() {
                return goodsActivitys;
            }

            public void setGoodsActivitys(List<?> goodsActivitys) {
                this.goodsActivitys = goodsActivitys;
            }

            public static class GoodsDisplayPicturesBean {
                /**
                 * id : 83
                 * picUrl : GoodsDisplayPicture/1516957929546.png
                 * goodsSpecificationDetailId : 1
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
        }
    }
}
