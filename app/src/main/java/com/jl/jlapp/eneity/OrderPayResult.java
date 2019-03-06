package com.jl.jlapp.eneity;

/**
 * Created by 柳亚婷 on 2018/6/13.
 */

public class OrderPayResult {

    /**
     * resultData : {"trade_state":"SUCCESS","return_msg":"OK","is_subscribe":"N","appid":"wx037454184aee70e9","fee_type":"CNY","out_trade_no":"JLORDER20180613151521","nonce_str":"66jwHz5vH9ZDr0Sd","transaction_id":"4200000148201806134975169120","trade_type":"APP","sign":"2C1385A318703C070EDA06A1A20BFBAF","result_code":"SUCCESS","mch_id":"1500059192","attach":"","total_fee":"10","time_end":"20180613162539","openid":"ole4W0jG6j4wvzl97BhTpYTiuMOc","return_code":"SUCCESS","bank_type":"CFT","trade_state_desc":"支付成功","cash_fee":"10"}
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
         * trade_state : SUCCESS
         * return_msg : OK
         * is_subscribe : N
         * appid : wx037454184aee70e9
         * fee_type : CNY
         * out_trade_no : JLORDER20180613151521
         * nonce_str : 66jwHz5vH9ZDr0Sd
         * transaction_id : 4200000148201806134975169120
         * trade_type : APP
         * sign : 2C1385A318703C070EDA06A1A20BFBAF
         * result_code : SUCCESS
         * mch_id : 1500059192
         * attach :
         * total_fee : 10
         * time_end : 20180613162539
         * openid : ole4W0jG6j4wvzl97BhTpYTiuMOc
         * return_code : SUCCESS
         * bank_type : CFT
         * trade_state_desc : 支付成功
         * cash_fee : 10
         */

        private String trade_state;
        private String return_msg;
        private String is_subscribe;
        private String appid;
        private String fee_type;
        private String out_trade_no;
        private String nonce_str;
        private String transaction_id;
        private String trade_type;
        private String sign;
        private String result_code;
        private String mch_id;
        private String attach;
        private String total_fee;
        private String time_end;
        private String openid;
        private String return_code;
        private String bank_type;
        private String trade_state_desc;
        private String cash_fee;

        public String getTrade_state() {
            return trade_state;
        }

        public void setTrade_state(String trade_state) {
            this.trade_state = trade_state;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getIs_subscribe() {
            return is_subscribe;
        }

        public void setIs_subscribe(String is_subscribe) {
            this.is_subscribe = is_subscribe;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getFee_type() {
            return fee_type;
        }

        public void setFee_type(String fee_type) {
            this.fee_type = fee_type;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getTime_end() {
            return time_end;
        }

        public void setTime_end(String time_end) {
            this.time_end = time_end;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getBank_type() {
            return bank_type;
        }

        public void setBank_type(String bank_type) {
            this.bank_type = bank_type;
        }

        public String getTrade_state_desc() {
            return trade_state_desc;
        }

        public void setTrade_state_desc(String trade_state_desc) {
            this.trade_state_desc = trade_state_desc;
        }

        public String getCash_fee() {
            return cash_fee;
        }

        public void setCash_fee(String cash_fee) {
            this.cash_fee = cash_fee;
        }
    }
}
