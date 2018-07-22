package com.leo.myapplication.bean;

/**
 * Created by DELL on 2018/7/18.
 */
public class PhoneBook {

    private int id;
    private String phoneName;
    private Long phoneNumber;
    private String headPicPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }

    @Override
    public String toString() {
        return "PhoneBook{" +
                "id=" + id +
                ", phoneName='" + phoneName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", headPicPath='" + headPicPath + '\'' +
                '}';
    }
}
