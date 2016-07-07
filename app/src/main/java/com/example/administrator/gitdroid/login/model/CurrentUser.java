package com.example.administrator.gitdroid.login.model;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class CurrentUser {
    //    此类不可实例化
    private CurrentUser() {
    }

    private static String accessToken;

    public static void setAccessToken(String accessToken) {
        CurrentUser.accessToken = accessToken;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    //清除缓存信息
    public static void clear() {
        accessToken = null;
    }

    //      当前是否有访问令牌
    public static boolean hasAccessToken() {
        return accessToken != null;
    }
}
