package com.example.administrator.gitdroid.github.hotrepo.pager.view;

import com.example.administrator.gitdroid.github.hotrepo.pager.model.Repo;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public interface PtrPageView extends MvpView,PtrView<List<Repo>>,LoadMoreView<List<Repo>> {
}
