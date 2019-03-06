package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by THINK on 2018-01-25.
 */

public class OrderStateDetailModel {

    /**
     * resultData : [{"id":37,"orderStateDetail":"用户申请退款/退货","addTime":1515381698000,"orderId":30},{"id":36,"orderStateDetail":"订单支付成功","addTime":1515061442000,"orderId":30},{"id":26,"orderStateDetail":"订单提交成功","addTime":1514965583000,"orderId":30}]
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
         * id : 37
         * orderStateDetail : 用户申请退款/退货
         * addTime : 1515381698000
         * orderId : 30
         */

        private int id;
        private String orderStateDetail;
        private long addTime;
        private int orderId;

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

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }
}
