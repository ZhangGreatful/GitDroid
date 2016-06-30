package com.example.administrator.gitdroid.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 最热门仓库的Fragment
 * Created by Administrator on 2016/6/30 0030.
 */
public class HotRepoFragment extends Fragment {

    @Bind(R.id.viewPager)ViewPager viewPager;
    @Bind(R.id.tabLayout)TabLayout tabLayout;

    private HotRepoPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_repo,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapter=new HotRepoPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
//        将tablayout和viewPager绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();

    }
}
