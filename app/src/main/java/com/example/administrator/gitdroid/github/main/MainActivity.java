package com.example.administrator.gitdroid.github.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.gitdroid.R;
import com.example.administrator.gitdroid.commons.ActivityUtils;
import com.example.administrator.gitdroid.favorite.FavoriteFragment;
import com.example.administrator.gitdroid.github.login.LoginActivity;
import com.example.administrator.gitdroid.github.login.model.CurrentUser;
import com.example.administrator.gitdroid.github.hotrepo.HotRepoFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.administrator.gitdroid.R.string.navigation_drawer_close;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout   drawerLayout;//抽屉(包括内容和侧滑菜单)
    @Bind(R.id.toolbar)
    Toolbar        toolBar;
    private ActivityUtils    activityUtils;
    private MenuItem         menuItem;
    //    热门仓库页面的Fragment
    private HotRepoFragment  hotRepoFragment;
    //          我的收藏页面的Fragment
    private FavoriteFragment favoriteFragment;
    private Button           btnLogin;
    private ImageView        ivIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        还没有授权登录
        if (CurrentUser.isEmpty()) {
            btnLogin.setText(R.string.login_github);
            return;
        }
//        已经授权登录
        btnLogin.setText(R.string.switch_account);
        getSupportActionBar().setTitle(CurrentUser.getUser().getLogin());
        Log.d(TAG, "onStart: **********" + CurrentUser.getUser().getName());
//        设置用户头像
//        使用其他第三方图像缓存加载图片
        String photoUrl = CurrentUser.getUser().getAvatar();
//        1.看内存里有没有图片
//        2.看硬盘里有没有图片
//        3.根据Url下载图片
//                1.下载后,存到硬盘
//                  2.下载后,存到内存
        ImageLoader.getInstance().displayImage(photoUrl, ivIcon);
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
        ivIcon = ButterKnife.findById(navigationView.getHeaderView(0), R.id.ivIcon);
//          登录
        btnLogin = ButterKnife.findById(navigationView.getHeaderView(0), R.id.btnLogin);
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
                if (!hotRepoFragment.isAdded()) {
                    replaceFragment(hotRepoFragment);
                }
                break;
//            case R.id.github_hot_coder:
//                activityUtils.showToast(R.string.hot_coder);
//                break;
//            case R.id.github_trend:
//                activityUtils.showToast(R.string.trend);
//                break;
            case R.id.arsenal_my_repo:
                if (favoriteFragment == null) favoriteFragment = new FavoriteFragment();
                if (!favoriteFragment.isAdded()) {
                    replaceFragment(favoriteFragment);
                }
                break;
//            case R.id.arsenal_recommend:
//                activityUtils.showToast(R.string.recommend);
//                break;
            case R.id.tips_daily:
//                activityUtils.showToast(R.string.tips_daily);
                break;
//            case R.id.tips_share:
//                break;

        }
//        关闭侧拉菜单,为了让关闭更加流畅,放在线程中执行
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
//        返回true,代表将该菜单项变为checked状态
        return true;
    }

    //      替换不同的Fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
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
