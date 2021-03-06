package com.example.administrator.gitdroid.github.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.gitdroid.R;
import com.example.administrator.gitdroid.commons.ActivityUtils;
import com.example.administrator.gitdroid.commons.LogUtils;
import com.example.administrator.gitdroid.github.main.MainActivity;
import com.example.administrator.gitdroid.network.GithubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @Bind(R.id.toolbar)
    Toolbar      toolbar;
    @Bind(R.id.webView)
    WebView      webview;
    //    显示一个Gif图片作为加载动画
    @Bind(R.id.gifImageView)
    GifImageView gifImageView;
    private ActivityUtils activityUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
//        设置ToolBar
        setSupportActionBar(toolbar);
//        显示标题栏左上角的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        初始化WebView
        initWebView();
    }

    private void initWebView() {
//
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();


        webview.loadUrl(GithubApi.AUTH_URL);
        webview.setFocusable(true);
        webview.setFocusableInTouchMode(true);
        webview.setWebChromeClient(webChromeClient);
        webview.setWebViewClient(webViewClient);


    }

    private WebViewClient   webViewClient   = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
//            检测加载到的新URL是否是用我们规定好的CALL_BACK开头的
            if (GithubApi.CALL_BACK.equals(uri.getScheme())) {
//                获取授权码
                String code = uri.getQueryParameter("code");
                LogUtils.d("code---------------" + code);
//                执行登录的操作Presenter
                getPresenter().login(code);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                gifImageView.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
            }
        }
    };

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void navigateToMain() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }


}
