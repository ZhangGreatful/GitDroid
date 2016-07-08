package com.example.administrator.gitdroid.github.hotrepo.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.gitdroid.R;
import com.example.administrator.gitdroid.commons.ActivityUtils;
import com.example.administrator.gitdroid.commons.LogUtils;
import com.example.administrator.gitdroid.components.FooterView;
import com.example.administrator.gitdroid.github.hotrepo.Language;
import com.example.administrator.gitdroid.github.hotrepo.pager.model.Repo;
import com.example.administrator.gitdroid.github.hotrepo.pager.view.PtrPageView;
import com.example.administrator.gitdroid.github.hotrepo.pager.view.RepoListAdapter;
import com.example.administrator.gitdroid.github.repo.RepoInfoActivity;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 实现下拉刷新和上拉加载,并在下拉刷新时添加一个动画效果
 * Created by Administrator on 2016/7/1 0001.
 */
public class RepoListFragment extends MvpFragment<PtrPageView, RepoListPresenter> implements PtrPageView {

    private static final String KEY_LANGUAGE = "key_language";

    /**
     * 获取(每次重新创建)当前的Fragment对象
     * <p/>
     * 当Fragment需要进行传递参数时,应该使用Bundle进行处理,我们这里就是将语言类型传入了(在获取语言仓库列表时要用到的)
     *
     * @param language
     * @return
     */
    public static RepoListFragment getInstance(Language language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LANGUAGE, language);
        fragment.setArguments(args);
        LogUtils.d("***************" + args.getString(KEY_LANGUAGE));
        return fragment;
    }

    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    @Bind(R.id.lvRepos)
    ListView              listView;
    @Bind(R.id.ptrClassicFramLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind(R.id.emptyView)
    TextView              emptyView;
    @Bind(R.id.errorView)
    TextView              errorView;
    @BindString(R.string.refresh_error)
    String                refreshError;

    private RepoListAdapter adpter;
    private FooterView      footerView;//添加更多视图
    private ActivityUtils   activityUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        adpter = new RepoListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    //      重写Mosby库父类MvpFragment的方法,返回当前视图所使用的Presenter对象
    @Override
    public RepoListPresenter createPresenter() {
        return new RepoListPresenter(getLanguage());
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        此处必须调用父类方法
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

//        下拉刷新
        listView.setAdapter(adpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               获取当前click的仓库
                Repo repo = adpter.getItem(position);
                RepoInfoActivity.open(getContext(), repo);

            }
        });
        //        初始下拉刷新
        initPullToRefresh();
//          初始上拉加载
        initLoadMoreScroll();
//      如果当前页面没有数据,开始自动刷新
        if (adpter.getCount() == 0) {
            ptrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrClassicFrameLayout.autoRefresh();
                }
            }, 200);
        }
//        autoRefresh();


    }

    private void initPullToRefresh() {
        //        使用本对象作为key,来记录上一次刷新的时间,如果两次下拉间隔太近,不会触发刷新方法
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
//        关闭header所需要的时长
        ptrClassicFrameLayout.setDurationToCloseHeader(1500);

//        添加header效果
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, 60, 0, 60);
        header.initWithString("I like " + getLanguage().getName());
        ptrClassicFrameLayout.setHeaderView(header);
        ptrClassicFrameLayout.addPtrUIHandler(header);
//        下拉刷新处理
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
//                执行加载数据的方法
                getPresenter().loadData();

            }
        });
    }

    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
//          依赖ListView的包 compile 'com.vinaysshenoy:mugen:1.0.2'
//        上拉加载更多(ListView滑动到最后的位置了,就可以LoadMore)
        Mugen.with(listView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
//                Toast.makeText(getContext(), "loadMore", Toast.LENGTH_SHORT).show();
//                执行加载更多的方法
                getPresenter().loadMore();
            }

            //              判断是否正在加载,此方法用来避免重复加载
            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            //          是否所有数据都已加载
            @Override
            public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }


    @OnClick({R.id.emptyView, R.id.errorView})
    public void autoRefresh() {
        ptrClassicFrameLayout.autoRefresh();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //    -----这是下拉刷新视图层的实现------------------------------------------------------
    @Override
    public void showContentView() {
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(String msg) {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }


    @Override
    public void refreshData(List<Repo> datas) {
        adpter.clear();
        if (adpter == null) return;
        adpter.addAll(datas);
//        adpter.addAll(datas);
//        adpter.notifyDataSetChanged();
    }

    @Override
    public void stopRefresh() {
        ptrClassicFrameLayout.refreshComplete();//下拉刷新完成
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    //    这是上拉加载更多视图层实现-----------------------------------------

    @Override
    public void addMoreData(List<Repo> datas) {
        if (datas == null) return;
        adpter.addAll(datas);

    }

    @Override
    public void hideLoadMore() {
        listView.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreLoading() {
//        避免重复加载,先进行判断
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void showLoadMoreError(String msg) {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError(msg);
    }

    @Override
    public void showLoadMoreEnd() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showComplete();
    }
}
