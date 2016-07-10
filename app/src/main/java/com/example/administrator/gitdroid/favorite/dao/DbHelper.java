package com.example.administrator.gitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.gitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "repostiory_groups.db";

    private static final int VERSION = 1;
    private static DbHelper sInstance;

    //    采用单例模式
    public static synchronized DbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private final Context context;

    private DbHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            //      创建表
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);
            //      添加本地默认类别数据到表里
            new RepoGroupDao(this).creatOrUpdate(RepoGroup.getDefaultGroups(context));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
//            首先,删除表
            TableUtils.dropTable(connectionSource, RepoGroup.class, true);
//            重新创建表
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
