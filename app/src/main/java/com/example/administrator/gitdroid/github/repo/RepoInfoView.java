package com.example.administrator.gitdroid.github.repo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 仓库详情信息显示页面的视图抽象
 * Created by Administrator on 2016/7/7 0007.
 */
public interface RepoInfoView extends MvpView{

    void showProgress();

    void hideProgress();
//      设置数据(html格式的Readme文件)
    void setData(String data);

    void showMessage(String msg);


}
