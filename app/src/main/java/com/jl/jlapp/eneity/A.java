package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/23 0023.
 */

public class A {


    /**
     * resultData : [{"id":1,"classificationId":38,"name":"福临门大米","keyWord":"米啊啊啊","introdution":"米米米啊啊啊","recom":null,"recomTime":null,"details":"<p><img src=\"http://172.16.1.190:8000/JLMIS/GoodsEditorPics/1516684845061.png\" style=\"max-width:100%;\"><\/p>","classification":null,"goodsSpecificationDetails":[{"id":1,"identifier":"JLYJFL24465465","specifications":"5kg","saleId":1,"price":10,"goodsId":1,"salesCount":23,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":null,"gxcGoodsStock":null,"brand":"品牌一","goodsDisplayPictures":[],"goodsActivitys":null}],"saleCount":29,"goodsEvaluations":null,"goodsActivitys":null},{"id":2,"classificationId":39,"name":"五香粉","keyWord":null,"introdution":null,"recom":1,"recomTime":1509917336000,"details":null,"classification":null,"goodsSpecificationDetails":[{"id":26,"identifier":"JLYJFL23692235","specifications":"50kg","saleId":3,"price":30,"goodsId":2,"salesCount":3,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":null,"gxcGoodsStock":null,"brand":"品牌三","goodsDisplayPictures":[],"goodsActivitys":null}],"saleCount":3,"goodsEvaluations":null,"goodsActivitys":null},{"id":3,"classificationId":51,"name":"金龙鱼调和油","keyWord":"油啊","introdution":"金龙鱼1:1调和油大甩卖啦！！！","recom":1,"recomTime":1511299716000,"details":"<p>金龙鱼1:1调和油大甩卖啦！！！啊啊啊啊啊啊啊啊啊啊啊啊啊<\/p><p style=\"text-align: center;\">发斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬<img src=\"http://localhost:8080/JLMIS/GoodsEditorPics/5e8a1156c94883e969d724ea4b724b311511243309829.jpg\" style=\"max-width:30%;\"><\/p>","classification":null,"goodsSpecificationDetails":[{"id":8,"identifier":"JLGOODS20171121160655","specifications":"100ml","saleId":2,"price":5,"goodsId":3,"salesCount":64,"state":0,"onShelvesTime":1511280000000,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511251615000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":null,"gxcGoodsStock":null,"brand":"品牌二","goodsDisplayPictures":[{"id":42,"picUrl":"GoodsDisplayPicture/1515555858515.png","goodsSpecificationDetailId":8}],"goodsActivitys":null}],"saleCount":145,"goodsEvaluations":null,"goodsActivitys":null},{"id":6,"classificationId":38,"name":"fghfghhgf","keyWord":"fghfg","introdution":"fghfgh","recom":1,"recomTime":1508707716000,"details":"<p><img src=\"http://172.16.1.190:8000/JLMIS/GoodsEditorPics/1516685430857.png\" style=\"max-width:100%;\"><img src=\"http://172.16.1.190:8000/JLMIS/GoodsEditorPics/1516685446223.png\" style=\"max-width: 100%;\"><img src=\"http://172.16.1.190:8000/JLMIS/GoodsEditorPics/1516685453711.png\" style=\"max-width: 100%;\"><\/p>","classification":null,"goodsSpecificationDetails":[{"id":7,"identifier":"JLGOODS20171121151704","specifications":"fghf","saleId":2,"price":4,"goodsId":6,"salesCount":34,"state":0,"onShelvesTime":1511280000000,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1511248624000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":null,"gxcGoodsStock":null,"brand":"品牌二","goodsDisplayPictures":[{"id":43,"picUrl":"GoodsDisplayPicture/1515555867968.png","goodsSpecificationDetailId":7}],"goodsActivitys":null}],"saleCount":34,"goodsEvaluations":null,"goodsActivitys":null},{"id":10,"classificationId":54,"name":"sdfdfsfsdf","keyWord":"sdf","introdution":"sdfsdf","recom":0,"recomTime":null,"details":"<p>请<b>删除此行<\/b>并在此处进行编辑。<\/p>","classification":null,"goodsSpecificationDetails":[{"id":16,"identifier":"JLGOODS20171212140332","specifications":"sdfs","saleId":1,"price":30,"goodsId":10,"salesCount":76,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":"JLADMIN20171113192605","operatorTime":1513058612000,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":null,"gxcGoodsStock":null,"brand":"品牌一","goodsDisplayPictures":[{"id":35,"picUrl":"GoodsDisplayPicture/1515555813883.png","goodsSpecificationDetailId":16}],"goodsActivitys":null}],"saleCount":76,"goodsEvaluations":null,"goodsActivitys":null}]
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
         * id : 1
         * classificationId : 38
         * name : 福临门大米
         * keyWord : 米啊啊啊
         * introdution : 米米米啊啊啊
         * recom : null
         * recomTime : null
         * details : <p><img src="http://172.16.1.190:8000/JLMIS/GoodsEditorPics/1516684845061.png" style="max-width:100%;"></p>
         * classification : null
         * goodsSpecificationDetails : [{"id":1,"identifier":"JLYJFL24465465","specifications":"5kg","saleId":1,"price":10,"goodsId":1,"salesCount":23,"state":0,"onShelvesTime":null,"offShelvesTime":null,"operatorIdentifier":null,"operatorTime":null,"user":null,"goodsDetails":null,"participateActivities":null,"participateActivitieList":null,"gxcGoodsState":null,"gxcGoodsStock":null,"brand":"品牌一","goodsDisplayPictures":[],"goodsActivitys":null}]
         * saleCount : 29
         * goodsEvaluations : null
         * goodsActivitys : null
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
        private Object goodsEvaluations;
        private Object goodsActivitys;
        private List<GoodsSpecificationDetailsBean> goodsSpecificationDetails;

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

        public Object getGoodsEvaluations() {
            return goodsEvaluations;
        }

        public void setGoodsEvaluations(Object goodsEvaluations) {
            this.goodsEvaluations = goodsEvaluations;
        }

        public Object getGoodsActivitys() {
            return goodsActivitys;
        }

        public void setGoodsActivitys(Object goodsActivitys) {
            this.goodsActivitys = goodsActivitys;
        }

        public List<GoodsSpecificationDetailsBean> getGoodsSpecificationDetails() {
            return goodsSpecificationDetails;
        }

        public void setGoodsSpecificationDetails(List<GoodsSpecificationDetailsBean> goodsSpecificationDetails) {
            this.goodsSpecificationDetails = goodsSpecificationDetails;
        }

        public static class GoodsSpecificationDetailsBean {
            /**
             * id : 1
             * identifier : JLYJFL24465465
             * specifications : 5kg
             * saleId : 1
             * price : 10
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
             * gxcGoodsState : null
             * gxcGoodsStock : null
             * brand : 品牌一
             * goodsDisplayPictures : []
             * goodsActivitys : null
             */

            private int id;
            private String identifier;
            private String specifications;
            private int saleId;
            private int price;
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
            private Object gxcGoodsState;
            private Object gxcGoodsStock;
            private String brand;
            private Object goodsActivitys;
            private List<?> goodsDisplayPictures;

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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
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

            public Object getGxcGoodsState() {
                return gxcGoodsState;
            }

            public void setGxcGoodsState(Object gxcGoodsState) {
                this.gxcGoodsState = gxcGoodsState;
            }

            public Object getGxcGoodsStock() {
                return gxcGoodsStock;
            }

            public void setGxcGoodsStock(Object gxcGoodsStock) {
                this.gxcGoodsStock = gxcGoodsStock;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public Object getGoodsActivitys() {
                return goodsActivitys;
            }

            public void setGoodsActivitys(Object goodsActivitys) {
                this.goodsActivitys = goodsActivitys;
            }

            public List<?> getGoodsDisplayPictures() {
                return goodsDisplayPictures;
            }

            public void setGoodsDisplayPictures(List<?> goodsDisplayPictures) {
                this.goodsDisplayPictures = goodsDisplayPictures;
            }
        }
    }
}
