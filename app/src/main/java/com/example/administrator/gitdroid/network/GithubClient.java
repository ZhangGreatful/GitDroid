package com.example.administrator.gitdroid.network;

import com.example.administrator.gitdroid.github.login.model.AccessTokenResult;
import com.example.administrator.gitdroid.github.login.model.User;
import com.example.administrator.gitdroid.github.hotrepo.pager.model.RepoResult;
import com.example.administrator.gitdroid.github.repo.RepoContentResult;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class GithubClient implements GithubApi {
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
                .addInterceptor(new TokenIntercepter())//添加拦截器,处理AccessToken
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

    @Override
    public Call<User> getUserInfo() {
        return gitHubApi.getUserInfo();
    }

    @Override
    public Call<RepoResult> searchRepo(@Query("q") String query, @Query("page") int pageId) {
        return gitHubApi.searchRepo(query, pageId);
    }

    @Override
    public Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo) {
        return gitHubApi.getReadme(owner, repo);
    }

    @Override
    public Call<ResponseBody> markdown(@Body RequestBody body) {
        return gitHubApi.markdown(body);
    }
}

