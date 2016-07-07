package com.example.administrator.gitdroid.github.login;

import android.util.Log;

import com.example.administrator.gitdroid.github.login.model.AccessTokenResult;
import com.example.administrator.gitdroid.github.login.model.CurrentUser;
import com.example.administrator.gitdroid.github.login.model.User;
import com.example.administrator.gitdroid.network.GithubApi;
import com.example.administrator.gitdroid.network.GithubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private static final String TAG = "LoginPresenter";
    private Call<AccessTokenResult> tokenCall;
    private Call<User>              userCall;

    public void login(String code) {
//        显示进度条
        getView().showProgress();
        if (tokenCall != null) tokenCall.cancel();
        tokenCall = GithubClient.getInstance()
                .getOAuthToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code);
        tokenCall.enqueue(tokenCallBack);
    }

    private Callback<AccessTokenResult> tokenCallBack = new Callback<AccessTokenResult>() {
        @Override
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
//            保存token到内存里面
            String token = response.body().getAccess_token();
            CurrentUser.setAccessToken(token);
//            再次业务操作
//            使用这个token来获取当前已认证的用户信息
//            从而拿到名称,头像
            if (userCall != null) userCall.cancel();
            userCall = GithubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallBack);
        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            getView().showMessage("Fail :" + t.getMessage());
//            失败,重置WebView
            getView().showProgress();
            getView().resetWeb();
        }
    };
    private Callback<User>              userCallBack  = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
//        保存用户user到内存里
            User user = response.body();
            Log.d(TAG, "onResponse: ***********"+user.getAvatar());
            CurrentUser.setUser(user);
            Log.d(TAG, "onResponse: +++++++++++"+CurrentUser.getUser().getName());
//            导航至主页面
            getView().showMessage("登录成功");
            getView().navigateToMain();

        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
//            清除缓存的用户信息,并重置webview
            CurrentUser.clear();
            getView().showMessage("Fail:" + t.getMessage());
            getView().showProgress();
            getView().resetWeb();
        }
    };

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            if (tokenCall != null) tokenCall.cancel();
            if (userCall != null) userCall.cancel();
        }
    }
}
