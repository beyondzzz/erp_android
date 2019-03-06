package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/2/6 0006.
 */

public class DecisionInventoryResultModel {


    /**
     * msg : 请求成功
     * code : 200
     * resultData : {"goodsMsg":[{"msg":"库存满足","code":200,"goodsSpecificationDetailsId":12},{"msg":"库存满足","code":200,"goodsSpecificationDetailsId":5}],"couponMsg":{"msg":"优惠券不能使用","code":30002}}
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
         * goodsMsg : [{"msg":"库存满足","code":200,"goodsSpecificationDetailsId":12},{"msg":"库存满足","code":200,"goodsSpecificationDetailsId":5}]
         * couponMsg : {"msg":"优惠券不能使用","code":30002}
         */

        private CouponMsgBean couponMsg;
        private List<GoodsMsgBean> goodsMsg;

        public CouponMsgBean getCouponMsg() {
            return couponMsg;
        }

        public void setCouponMsg(CouponMsgBean couponMsg) {
            this.couponMsg = couponMsg;
        }

        public List<GoodsMsgBean> getGoodsMsg() {
            return goodsMsg;
        }

        public void setGoodsMsg(List<GoodsMsgBean> goodsMsg) {
            this.goodsMsg = goodsMsg;
        }

        public static class CouponMsgBean {
            /**
             * msg : 优惠券不能使用
             * code : 30002
             */

            private String msg;
            private int code;

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
        }

        public static class GoodsMsgBean {
            /**
             * msg : 库存满足
             * code : 200
             * goodsSpecificationDetailsId : 12
             */

            private String msg;
            private int code;
            private int goodsSpecificationDetailsId;
            private int stock;

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

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

            public int getGoodsSpecificationDetailsId() {
                return goodsSpecificationDetailsId;
            }

            public void setGoodsSpecificationDetailsId(int goodsSpecificationDetailsId) {
                this.goodsSpecificationDetailsId = goodsSpecificationDetailsId;
            }
        }
    }
}
