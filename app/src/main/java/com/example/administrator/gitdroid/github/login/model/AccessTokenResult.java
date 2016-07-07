package com.example.administrator.gitdroid.github.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class AccessTokenResult {

    @SerializedName("access_token")
    private String access_token;

    @SerializedName("token_type")
    private String token_type;

    @SerializedName("scope")
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getScope() {
        return scope;
    }
}
