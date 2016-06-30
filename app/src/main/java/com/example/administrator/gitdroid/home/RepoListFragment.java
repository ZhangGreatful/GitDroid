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

/**
 * 仓库列表Fragment
 * Created by Administrator on 2016/6/30 0030.
 */
public class RepoListFragment extends Fragment {
    @Bind(R.id.lvRepos)
    ListView listView;
    private ArrayAdapter<String> adapter;
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
        for (int i = 1; i <= 10; i++) {
            datas.add("我是第" + i + "条数据");
        }
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

        adapter.addAll(datas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(getContext());
    }
}
