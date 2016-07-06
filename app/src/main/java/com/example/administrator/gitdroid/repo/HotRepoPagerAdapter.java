package com.example.administrator.gitdroid.repo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.gitdroid.repo.pager.RepoListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * TabLayout数据
 * Created by Administrator on 2016/6/30 0030.
 */
public class HotRepoPagerAdapter extends FragmentPagerAdapter {

    private final List<String> languages;
    public HotRepoPagerAdapter(FragmentManager fm) {
        super(fm);
        languages=new ArrayList<>();
        languages.add("java1");
        languages.add("java2");
        languages.add("java3");
        languages.add("java4");
        languages.add("java5");
        languages.add("java6");
        languages.add("java7");
    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position);
    }
}
