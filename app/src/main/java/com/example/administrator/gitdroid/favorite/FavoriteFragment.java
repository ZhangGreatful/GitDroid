package com.example.administrator.gitdroid.favorite;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.administrator.gitdroid.R;
import com.example.administrator.gitdroid.commons.ActivityUtils;
import com.example.administrator.gitdroid.favorite.dao.DbHelper;
import com.example.administrator.gitdroid.favorite.dao.RepoGroupDao;
import com.example.administrator.gitdroid.favorite.model.RepoGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class FavoriteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    @Bind(R.id.tvGroupType)
    TextView tvGroupType;
    @Bind(R.id.listView)
    ListView listView;
    private RepoGroupDao  repoGroupDao;
    private ActivityUtils activityUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repoGroupDao = new RepoGroupDao(DbHelper.getInstance(getContext()));
        activityUtils = new ActivityUtils(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    //    点击ImageButton弹出PopupMenu
    @OnClick(R.id.btnFilter)
    public void showPopupMenu(View view) {

//        PopupMenu
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
//        menu项,上面默认只有全部和未分类
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        popupMenu.setOnMenuItemClickListener(this);
//        拿到menu对象,上面默认只有全部和未分类  +   添加上我们自己的
        Menu menu = popupMenu.getMenu();
//        拿到我们本地数据库内的所有类别数据
        List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
        for (RepoGroup repoGroup : repoGroups) {
//            groupid不要,item的id和repoGroup的id,name相同
            menu.add(Menu.NONE, repoGroup.getId(), Menu.NONE, repoGroup.getName());
        }
        popupMenu.show();
    }

    private int repoGroupID;

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        tvGroupType.setText(item.getTitle());
        repoGroupID = item.getItemId();
        resetData();
        return false;
    }

    private void resetData() {
//        ListView控件,上面的数据要重置更新数据
        switch (repoGroupID) {
            case R.id.repo_group_all:
                activityUtils.showToast("全部");
                break;
            case R.id.repo_group_no:
                activityUtils.showToast("未分类");
                break;
            default:
                activityUtils.showToast("id:" + repoGroupID);
                break;
        }
    }
}
