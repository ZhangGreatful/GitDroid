package com.example.administrator.gitdroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.administrator.gitdroid.commons.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout   drawerLayout;//抽屉(包括内容和侧滑菜单)
    private ActivityUtils activityUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
//        设置navitationView的监听器
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.github_hot_repo:
                activityUtils.showToast(R.string.hot_repo);
                break;
            case R.id.github_hot_coder:
                activityUtils.showToast(R.string.hot_coder);
                break;
            case R.id.github_trend:
                activityUtils.showToast(R.string.trend);
                break;
            case R.id.arsenal_my_repo:
                activityUtils.showToast(R.string.my_repo);
                break;
            case R.id.arsenal_recommend:
                activityUtils.showToast(R.string.recommend);
                break;
            case R.id.tips_daily:
                activityUtils.showToast(R.string.tips_daily);
                break;
            case R.id.tips_share:
                break;

        }
//        返回true,代表将该菜单项变为checked状态
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        判断NavigationView是否打开
//        若是开着的,则关闭
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
//            若NavigationView是关闭着的,则退出当前的Activity
            super.onBackPressed();
        }

    }
}
