package com.example.administrator.gitdroid.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.gitdroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 仓库列表Fragment
 * Created by Administrator on 2016/6/30 0030.
 */
public class RepoListFragment extends Fragment {
    @Bind(R.id.lvRepos)
    ListView              listView;
    @Bind(R.id.ptrClassicFramLayout)
    PtrClassicFrameLayout ptrClassicFramLayout;
    private        ArrayAdapter<String> adapter;
    private static int                  count;
    private List<String> datas = new ArrayList<>();

    public static RepoListFragment getInstance(String language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle args = new Bundle();
        args.putSerializable("key_language", language);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        ptrClassicFramLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(20);
            }
        });
    }

    private void loadData(final int size) {
//        停留3秒
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    datas.clear();
                    for (int i = 1; i <= size; i++) {
                        count++;
                        datas.add("我是第" + count + "条数据");
                    }
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


//                到UI线程中完成视图工作
                ptrClassicFramLayout.post(new Runnable() {
                    @Override
                    public void run() {
//                        清除数据
                        adapter.clear();
//                        添加数据
                        adapter.addAll(datas);
//                刷新数据
                        adapter.notifyDataSetChanged();
//                下拉刷新完成
                        ptrClassicFramLayout.refreshComplete();
                    }
                });

            }

        }).start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(getContext());
    }
}
