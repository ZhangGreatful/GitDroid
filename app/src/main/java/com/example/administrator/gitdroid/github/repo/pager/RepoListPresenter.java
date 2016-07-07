package com.example.administrator.gitdroid.github.repo.pager;

import com.example.administrator.gitdroid.github.repo.pager.model.Repo;
import com.example.administrator.gitdroid.github.repo.pager.model.RepoResult;
import com.example.administrator.gitdroid.github.repo.pager.view.PtrPageView;
import com.example.administrator.gitdroid.network.GithubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment在做视图工作(实现了PtrPagerView视图接口,也就是下拉和上拉功能视图接口)
 * <p/>
 * Presenter里做了具体要做的业务(下拉刷新获取数据,上拉加载更多数据),以及视图的触发
 * <p/>
 * 直接使用MVP库Mosby库
 * 让你的应用简单清晰的使用MVP架构方式来构建开发
 * <p/>
 * 依赖mosby
 * Created by Administrator on 2016/7/4 0004.
 */
public class RepoListPresenter extends MvpNullObjectBasePresenter<PtrPageView> {

    private Call<RepoResult> repoCall;

    //    下拉刷新视图层的业务逻辑-------------------------------------------------
    public void loadData() {
        getView().hideLoadMore();//隐藏加载更多的视图
        getView().showContentView();//显示内容
        repoCall = GithubClient.getInstance().searchRepo("language:" + "java", 1);
        repoCall.enqueue(reposCallback);
    }

    //    上拉加载更多视图层的业务逻辑---------------------------------------------
    public void loadMore() {

    }

    private Callback<RepoResult> reposCallback = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            getView().stopRefresh();//视图停止刷新
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                getView().showMessage("result is null");
                return;
            }

            List<Repo> repoList = repoResult.getRepoList();
            getView().refreshData(repoList);//视图刷新数据
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopRefresh();//视图停止刷新
            getView().showMessage(t.getMessage());//显示错误信息
        }
    };

}
