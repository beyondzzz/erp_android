package com.jl.jlapp.eneity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class Order implements Parcelable {
    String orderNo;
    Integer state;
    String goodsName;
    Integer price;
    Integer picUrl;
    Integer goodsNum;
    Integer combinedGoodsNum;
    Double postage;
    String goodsSpecification;
    Double combinedNum;
    Integer isHasActivity;
    Integer goodsStock;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
        // 2.序列化对象
        parcel.writeString(orderNo);
        parcel.writeInt(state);
        parcel.writeString(goodsName);
        parcel.writeInt(price);
        parcel.writeInt(picUrl);
        parcel.writeInt(goodsNum);
        parcel.writeInt(combinedGoodsNum);
        parcel.writeDouble(postage);
        parcel.writeString(goodsSpecification);
        parcel.writeDouble(combinedNum);
        parcel.writeInt(isHasActivity);
        parcel.writeInt(goodsStock);


    }

    // 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
    // android.os.BadParcelableException:
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person
    // 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
    // 5.反序列化对象
    public static final Parcelable.Creator<Order> CREATOR = new Creator(){

        @Override
        public Order createFromParcel(Parcel source) {

            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            Order o = new Order();
            o.setOrderNo(source.readString());
            o.setState(source.readInt());
            o.setGoodsName(source.readString());
            o.setPrice(source.readInt());
            o.setPicUrl(source.readInt());
            o.setGoodsNum(source.readInt());
            o.setCombinedGoodsNum(source.readInt());
            o.setPostage(source.readDouble());
            o.setGoodsSpecification(source.readString());
            o.setCombinedNum(source.readDouble());
            o.setIsHasActivity(source.readInt());
            o.setGoodsStock(source.readInt());
            return o;
        }

        @Override
        public Order[] newArray(int size) {

            return new Order[size];
        }
    };



    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(Integer picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getCombinedGoodsNum() {
        return combinedGoodsNum;
    }

    public void setCombinedGoodsNum(Integer combinedGoodsNum) {
        this.combinedGoodsNum = combinedGoodsNum;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public String getGoodsSpecification() {
        return goodsSpecification;
    }

    public void setGoodsSpecification(String goodsSpecification) {
        this.goodsSpecification = goodsSpecification;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getCombinedNum() {
        return combinedNum;
    }

    public void setCombinedNum(Double combinedNum) {
        this.combinedNum = combinedNum;
    }

    public Integer getIsHasActivity() {
        return isHasActivity;
    }

    public void setIsHasActivity(Integer isHasActivity) {
        this.isHasActivity = isHasActivity;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }
}
