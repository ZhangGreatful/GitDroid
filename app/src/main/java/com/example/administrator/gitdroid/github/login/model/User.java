package com.example.administrator.gitdroid.github.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class User {
//    "login": "octocat",
//    "id": 1,
//    "avatar_url": "https://github.com/images/error/octocat_happy.gif",
//    "name": "monalisa octocat",

//    登录所用的账号
    @SerializedName("login")
    private String login;
//    id
    private int id;
//    用户名
    @SerializedName("name")
    private String name;
//    用户头像路径
    @SerializedName("avatar_url")
    private String avatar;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
