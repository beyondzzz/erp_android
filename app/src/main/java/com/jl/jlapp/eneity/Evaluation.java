package com.jl.jlapp.eneity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/20 0020.
 */

public class Evaluation {
    String userPhone;
    String evaluationMsg;
    Integer star;
    List images;
    private String date;
    private Integer isVip=0;
    private Integer goodsImg;

    public Integer getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(Integer goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getEvaluationMsg() {
        return evaluationMsg;
    }

    public void setEvaluationMsg(String evaluationMsg) {
        this.evaluationMsg = evaluationMsg;
    }

    public List getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }
}
