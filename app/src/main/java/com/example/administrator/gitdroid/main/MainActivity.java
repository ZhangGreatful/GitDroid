package com.example.administrator.gitdroid.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.administrator.gitdroid.R;
import com.example.administrator.gitdroid.commons.ActivityUtils;
import com.example.administrator.gitdroid.login.LoginActivity;
import com.example.administrator.gitdroid.repo.HotRepoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.administrator.gitdroid.R.string.navigation_drawer_close;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout   drawerLayout;//抽屉(包括内容和侧滑菜单)
    @Bind(R.id.toolbar)
    Toolbar        toolBar;
    private ActivityUtils   activityUtils;
    private MenuItem        menuItem;
    //    热门仓库页面的Fragment
    private HotRepoFragment hotRepoFragment;
    private Button btnLogin;


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
//        默认第一个menu项为选中项(最热门)
        menuItem = navigationView.getMenu().findItem(R.id.github_hot_repo);
        menuItem.setCheckable(true);
        //        处理ActionBar
        setSupportActionBar(toolBar);
        //        设置toolBar上左上角切换菜单的按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolBar, R.string.navigation_drawer_open, navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//          登录
        btnLogin=ButterKnife.findById(navigationView.getHeaderView(0),R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(LoginActivity.class);
            }
        });
//        设置默认显示的是hotRepoFragment热门仓库
        hotRepoFragment = new HotRepoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, hotRepoFragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        将默认选中项手动设置为false
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        }
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
