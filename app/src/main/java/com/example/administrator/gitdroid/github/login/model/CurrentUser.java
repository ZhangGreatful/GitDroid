package com.example.administrator.gitdroid.github.login.model;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class CurrentUser {
    //    此类不可实例化
    private CurrentUser() {
    }

    private static String accessToken;
    private static User   user;

    public static void setUser(User user) {
        CurrentUser.user = user;
    }

    public static void setAccessToken(String accessToken) {
        CurrentUser.accessToken = accessToken;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static User getUser() {
        return user;
    }

    //清除缓存信息
    public static void clear() {
        accessToken = null;
        user = null;

    }

    //      当前是否有访问令牌
    public static boolean hasAccessToken() {
        return accessToken != null;
    }

    //    当前用户信息是否为空
    public static boolean isEmpty() {
        return accessToken == null || user == null;
    }
}
