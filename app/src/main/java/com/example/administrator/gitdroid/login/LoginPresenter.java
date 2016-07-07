package com.example.administrator.gitdroid.login;

import com.example.administrator.gitdroid.login.model.AccessTokenResult;
import com.example.administrator.gitdroid.network.GithubApi;
import com.example.administrator.gitdroid.network.GithubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;

/**
 * 此类是处理登录操作的,并且在登录过程中,将触发调用LoginView
 * <p/>
 * 登录过程中遵循标准的OAuth2.0协议
 * <p/>
 * 用户通过WebView登录Github网站,如果登录成功,且用户给我们授权,github会访问我们的回调地址,给我们一个授权码
 * <p/>
 * 我们就能通过授权码去获得访问令牌,最终就有权利访问信息
 * Created by Administrator on 2016/7/6 0006.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call<AccessTokenResult> tokenCall;

    public void login(String code) {
//        显示进度条
        getView().showProgress();
        if (tokenCall != null) tokenCall.cancel();
        tokenCall = GithubClient.getInstance()
                .getOAuthToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code);
    }
}
