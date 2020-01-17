package com.test.messeger;

import android.graphics.drawable.Drawable;

public class UserItemView {

    private Drawable iconDrawable;
    private String name;
    private String nickname;
    private String status;
    private String id;

    public String getName() {
        return name;
    }
    public UserItemView(){}
    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public UserItemView(String name, String nickname, String status, String id) {
        this.name = name;
        this.nickname = nickname;
        this.status = status;
        this.id = id;
    }
    public UserItemView(Drawable iconDrawable, String name, String nickname, String status, String id) {
        this.iconDrawable = iconDrawable;
        this.name = name;
        this.nickname = nickname;
        this.status = status;
        this.id = id;
    }

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }


}
