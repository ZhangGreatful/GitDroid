package com.example.administrator.gitdroid.network;

import com.example.administrator.gitdroid.login.model.CurrentUser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class TokenIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        每次请求,都加一个头进去Authorization: token+值
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
//        是否有Token,有的话,在当前这次请求中加入token这个请求头
        if (CurrentUser.hasAccessToken()) {
            builder.header("Authorization", "token " + CurrentUser.getAccessToken());
        }

        Response response = chain.proceed(builder.build());
        if (response.isSuccessful()) {
            return response;
        }
        if (response.code() == 401 || response.code() == 403) {
            throw new IOException("未经授权的!限制是每分钟10次");
        } else {
            throw new IOException("响应码:" + response.code());
        }
    }
}
