package com.example.administrator.gitdroid;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 需要在清单文件里注册
 * Created by Administrator on 2016/7/7 0007.
 */
public class GitdroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_avatar)
                .showImageOnLoading(R.drawable.ic_avatar)
                .showImageOnFail(R.drawable.ic_avatar)
                .displayer(new RoundedBitmapDisplayer(12))
                .cacheInMemory(true)//打开内存缓存
                .cacheOnDisk(true)//打开硬盘缓存
                .resetViewBeforeLoading(true)//在ImageView加载前清除它上面之前的图片
                .build();
//      ImageLoader进行配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(5 * 1024 * 1024)//设置内存缓存为5M
                .defaultDisplayImageOptions(options)//设置默认的显示选项
                .build();
//                初始化ImageLoader
        ImageLoader.getInstance().init(config);
    }
}
