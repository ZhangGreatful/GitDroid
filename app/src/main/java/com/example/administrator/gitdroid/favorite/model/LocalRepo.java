package com.example.administrator.gitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 本地仓库表
 * Created by Administrator on 2016/7/10 0010.
 */
@DatabaseTable(tableName = "repositories")
public class LocalRepo {

    //    {
//        "id": 22374063,
//            "name": "android-best-practices",
//            "full_name": "futurice/android-best-practices",
//            "avatar_url": "https://avatars.githubusercontent.com/u/852157?v=3",
//            "description": "Do's and Don'ts for Android development, by Futurice developers",
//            "stargazers_count": 10469,
//            "forks_count": 1974
//    }
    @DatabaseField(id = true)
    private long   id;
    //    仓库名称
    @DatabaseField
    private String name;

    @DatabaseField(columnName = "full_name")
    @SerializedName("full_name")
    private String fullName;

    @DatabaseField(columnName = "avatar_url")
    @SerializedName("avatar_url")
    private String avatar;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "stargazers_count")
    @SerializedName("stargazers_count")
    private int starCount;


    @DatabaseField(columnName = "forks_count")
    @SerializedName("forks_count")
    private int forkCount;

    //    这是一个外键,从另外一个表中取数据
    @DatabaseField(canBeNull = true, foreign = true, columnName = COLUMN_GROUP_ID)
    @SerializedName("group")
    private RepoGroup repoGroup;
    public static final String COLUMN_GROUP_ID = "group_id";
//    "group":{
//        "id": 1,
//                "name": "网络连接"
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public RepoGroup getRepoGroup() {
        return repoGroup;
    }

    public void setRepoGroup(RepoGroup repoGroup) {
        this.repoGroup = repoGroup;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof LocalRepo && this.id == ((LocalRepo) o).getId();
    }

    //    读取本地json数据
    public static List<LocalRepo> getDefaultLocalRepos(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("defaultrepos.json");
//        将流转化为字符串
            String content = IOUtils.toString(inputStream);
//        将字符串转化为对象数组
            Gson gson = new Gson();
            return gson.fromJson(content, new TypeToken<List<LocalRepo>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
