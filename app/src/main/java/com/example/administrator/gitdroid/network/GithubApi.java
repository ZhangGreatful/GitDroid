package com.example.administrator.gitdroid.network;

import com.example.administrator.gitdroid.github.login.model.AccessTokenResult;
import com.example.administrator.gitdroid.github.login.model.User;
import com.example.administrator.gitdroid.github.repo.pager.model.RepoResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    //    GET /search/repositories
//            参数
//
//    名称	类型	描述
//    q	string	搜索关键字以及其它限定符。
//    sort	string	排序字段，可以是stars，fortks或updated。默认：结果符合程度。
//    order	string	排序顺序，可以是asc或desc，默认是desc。
//    参数q支持各种仓库搜索限定符，在我们的应用中使用的是language，即根据仓库使用的编程语言来搜索。
//    https://api.github.com/search/repositories?q=language:java&page=1

    /**
     * @param query  查询参数
     * @param pageId 查询页数,从1开始
     * @return 查询结果
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepo(
            @Query("q") String query,
            @Query("page") int pageId);
}
