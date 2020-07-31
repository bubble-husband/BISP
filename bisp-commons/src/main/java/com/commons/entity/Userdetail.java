package com.commons.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Userdetail {
    private Integer userdetailId;

    private Integer userId;

    private String nickname;
   
    @JSONField(format="yyyy-MM-dd")
    private Date birthday;

    private String sex;

    private String photo;

    public Integer getUserdetailId() {
        return userdetailId;
    }

    public void setUserdetailId(Integer userdetailId) {
        this.userdetailId = userdetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }
}