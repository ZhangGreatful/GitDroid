package com.example.administrator.gitdroid.github.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 这是登录页面的视图抽象
 * Created by Administrator on 2016/7/6 0006.
 */
public interface LoginView extends MvpView {
    //    显示进度条
    void showProgress();

    //    重置WebView
    void resetWeb();

    //    显示信息
    void showMessage(String msg);

    //    导航至主页面
    void navigateToMain();
}
