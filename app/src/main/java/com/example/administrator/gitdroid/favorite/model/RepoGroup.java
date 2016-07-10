package com.example.administrator.gitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 本地收藏仓库类别表
 *
 * 此时表还没有建立
 * Created by Administrator on 2016/7/10 0010.
 */

@DatabaseTable(tableName = "repostiory_groups")//定义表名
public class RepoGroup {

    @DatabaseField(id = true)//主段
    private int id;

    @DatabaseField(columnName = "NAME")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static List<RepoGroup> DEFAULT_GROUPS;
//      读取本地json数据
    public static List<RepoGroup> getDefaultGroups(Context context) {
        if (DEFAULT_GROUPS != null) {
            return DEFAULT_GROUPS;
        }
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("repogroup.json");
            String content = IOUtils.toString(inputStream);

            Gson gson = new Gson();
            DEFAULT_GROUPS = gson.fromJson(content, new TypeToken<List<RepoGroup>>() {
            }.getType());
            return DEFAULT_GROUPS;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
