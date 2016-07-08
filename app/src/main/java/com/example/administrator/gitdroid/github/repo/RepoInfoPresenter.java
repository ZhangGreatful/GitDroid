package com.example.administrator.gitdroid.github.repo;


import android.util.Base64;

import com.example.administrator.gitdroid.commons.LogUtils;
import com.example.administrator.gitdroid.github.hotrepo.pager.model.Repo;
import com.example.administrator.gitdroid.network.GithubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class RepoInfoPresenter extends MvpNullObjectBasePresenter<RepoInfoView> {

    private Call<RepoContentResult> repoContentCall;
    private Call<ResponseBody>      mdCall;

    /**
     * 获取指定仓库的Readme
     *
     * @param repo
     */
    public void getReadme(Repo repo) {
        getView().showProgress();
        String login = repo.getOwner().getLogin();
        String name = repo.getName();
        if (repoContentCall != null) repoContentCall.cancel();
        repoContentCall = GithubClient.getInstance().getReadme(login, name);
        repoContentCall.enqueue(repoContentCallBack);

    }

    private Callback<RepoContentResult> repoContentCallBack = new Callback<RepoContentResult>() {
        @Override
        public void onResponse(retrofit2.Call<RepoContentResult> call, Response<RepoContentResult> response) {
            String content = response.body().getContent();
//            BASE64解码
            byte[] data = Base64.decode(content, Base64.DEFAULT);
            String mdContent = new String(data);
            LogUtils.d("mdContent:---------" + mdContent);
//            根据Markdown格式的内容获取html格式的内容
//              可以将字符串/字节变成请求体,也就是说可以传入data/mdContent,
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), mdContent);
            if (mdCall != null) mdCall.cancel();
            mdCall = GithubClient.getInstance().markdown(body);
            mdCall.enqueue(mdCallback);
        }

        @Override
        public void onFailure(retrofit2.Call<RepoContentResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("Error:" + t.getMessage());
        }
    };
    private Callback<ResponseBody>      mdCallback          = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String htmlContent = response.body().string();
                getView().setData(htmlContent);
                getView().hideProgress();
            } catch (IOException e) {
                onFailure(call, e);
            }

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("Error:" + t.getMessage());
        }
    };
}
