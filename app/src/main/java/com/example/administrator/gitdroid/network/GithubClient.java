package com.example.administrator.gitdroid.network;

import com.example.administrator.gitdroid.login.model.AccessTokenResult;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class GithubClient {
    private static GithubClient sClient;

    public static GithubClient getInstance() {
        if (sClient == null) {
            sClient = new GithubClient();
        }
        return sClient;
    }

    private final GithubApi gitHubApi;

    private GithubClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        gitHubApi = retrofit.create(GithubApi.class);
    }

    public Call<AccessTokenResult> getOAuthToken(@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return gitHubApi.getOAuthToken(client, clientSecret, code);
    }
}
