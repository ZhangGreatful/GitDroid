package com.example.administrator.gitdroid.network;

import com.example.administrator.gitdroid.login.model.AccessTokenResult;
import com.example.administrator.gitdroid.login.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public interface GithubApi {
    String CALL_BACK = "gitdroid";

    String CLIENT_ID     = "93f6dc1215b62687f81c";
    String CLIENT_SECRET = "38afe932ff87ff9b513591d34fa8996eb484ea10";
    //      授权时申请的可访问域
    String INITIAL_SCOPE = "user,public_repo,repo";

    //    webview来加载此Url,用来显示Github的登录页面
    String AUTH_URL =
            "https://github.com/login/oauth/authorize?client_id=" +
                    CLIENT_ID + "&" + "scope=" + INITIAL_SCOPE;
    /**
     * 获取访问令牌API
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);

    @GET("user")
    Call<User> getUserInfo();
}
