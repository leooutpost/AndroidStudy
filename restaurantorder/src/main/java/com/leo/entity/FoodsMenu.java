package com.leo.entity;

import java.io.Serializable;

/**
 * Created by DELL on 2018/8/6.
 */
public class FoodsMenu implements Serializable{

    private int foodsId;
    private String foodsName;
    private double foodsPrice;
    private int isProvide;//1 0
    private String foodsType;

    public int getFoodsId() {
        return foodsId;
    }

    public void setFoodsId(int foodsId) {
        this.foodsId = foodsId;
    }

    public String getFoodsName() {
        return foodsName;
    }

    public void setFoodsName(String foodsName) {
        this.foodsName = foodsName;
    }

    public double getFoodsPrice() {
        return foodsPrice;
    }

    public void setFoodsPrice(double foodsPrice) {
        this.foodsPrice = foodsPrice;
    }

    public int getIsProvide() {
        return isProvide;
    }

    public void setIsProvide(int isProvide) {
        this.isProvide = isProvide;
    }

    public String getFoodsType() {
        return foodsType;
    }

    public void setFoodsType(String foodsType) {
        this.foodsType = foodsType;
    }

    @Override
    public String toString() {
        return "FoodsMenu{" +
                "foodsId=" + foodsId +
                ", foodsName='" + foodsName + '\'' +
                ", foodsPrice=" + foodsPrice +
                ", isProvide=" + isProvide +
                ", foodsType='" + foodsType + '\'' +
                '}';
    }
}
