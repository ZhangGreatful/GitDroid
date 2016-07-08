package com.example.administrator.gitdroid.github.hotrepo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.gitdroid.github.hotrepo.pager.RepoListFragment;

import java.util.List;

/**
 * TabLayout数据
 * Created by Administrator on 2016/6/30 0030.
 */
public class HotRepoPagerAdapter extends FragmentPagerAdapter {

    private final List<Language> languages;

    public HotRepoPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
//        从本地read出来
        languages = Language.getDefaultLanguage(context);
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
        return languages.get(position).getName();
    }
}
