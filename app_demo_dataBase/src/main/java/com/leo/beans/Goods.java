package com.leo.beans;

/**
 * Created by DELL on 2018/7/16.
 */
public class Goods {

    private int code;
    private String goodsName;
    private String goodsNumber;
    private String goodsPrice;

    public Goods() {
    }

    public Goods(int code, String goodsName, String goodsNumber, String goodsPrice) {
        this.code = code;
        this.goodsName = goodsName;
        this.goodsNumber = goodsNumber;
        this.goodsPrice = goodsPrice;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
