package com.example.administrator.gitdroid.github.hotrepo.pager;

import com.example.administrator.gitdroid.github.hotrepo.Language;
import com.example.administrator.gitdroid.github.hotrepo.pager.model.Repo;
import com.example.administrator.gitdroid.github.hotrepo.pager.model.RepoResult;
import com.example.administrator.gitdroid.github.hotrepo.pager.view.PtrPageView;
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
    private int nextPage = 0;
    private Language language;

    public RepoListPresenter(Language language) {
        this.language = language;
    }

    //    下拉刷新视图层的业务逻辑-------------------------------------------------
    public void loadData() {
        getView().hideLoadMore();//隐藏加载更多的视图
        getView().showContentView();//显示内容
        nextPage = 1;//刷新永远是第一页
        repoCall = GithubClient.getInstance().searchRepo("language:" + language.getPath(), nextPage);
        repoCall.enqueue(reposCallback);
    }

    //    上拉加载更多视图层的业务逻辑---------------------------------------------
    public void loadMore() {
        getView().showLoadMoreLoading();//显示加载更多
        repoCall = GithubClient.getInstance().searchRepo("language:" + language.getPath(), nextPage);
        repoCall.enqueue(loadMoreCallBack);
    }

    private Callback<RepoResult> reposCallback    = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            getView().stopRefresh();//视图停止刷新
            RepoResult repoResult = response.body();
            if (repoResult == null) {
                getView().showMessage("result is null");
                return;
            }
//            当前搜索的语言下,没有任何仓库
            if (repoResult.getTotalCount() <= 0) {
                getView().refreshData(null);
                getView().showEmptyView();
                return;
            }
//              取出当前搜索的语言下,所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            getView().refreshData(repoList);//视图刷新数据
            nextPage = 2;

        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopRefresh();//视图停止刷新
            getView().showMessage(t.getMessage());//显示错误信息
        }
    };
    private Callback<RepoResult> loadMoreCallBack = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            getView().hideLoadMore();
            RepoResult result = response.body();
            if (result == null) {
                getView().showLoadMoreError("result is null");
                return;
            }
//            没有更多数据了
            if (result.getTotalCount() <= 0) {
                getView().showLoadMoreEnd();
                return;
            }
//            取出当前搜索的语言下,所有仓库
            List<Repo> list = result.getRepoList();
            getView().addMoreData(list);
            nextPage++;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopRefresh();//视图停止刷新
            getView().showMessage(t.getMessage());//显示错误信息
        }
    };

}
