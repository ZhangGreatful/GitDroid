package com.example.administrator.gitdroid.github.hotrepo.pager.model;

import com.example.administrator.gitdroid.github.login.model.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class Repo implements Serializable {

    //    仓库id
    @SerializedName("id")
    private int    id;
    //    仓库描述
    @SerializedName("name")
    private String name;
    //      仓库全称
    @SerializedName("full_name")
    private String fullName;
    //    仓库描述
    @SerializedName("description")
    private String description;
    //    本仓库的star数量
    @SerializedName("stargazers_count")
    private int    stargazersCount;
    //    本仓库的forks数量
    @SerializedName("forks_count")
    private int    forksCount;



    //      本仓库的拥有者
    @SerializedName("owner")
    private User   owner;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public int getForksCount() {
        return forksCount;
    }
    public User getOwner() {
        return owner;
    }

    //    "id": 507775,
//            "name": "elasticsearch",
//            "full_name": "elastic/elasticsearch",
//            "owner": {
//        "login": "elastic",
//                "id": 6764390,
//                "avatar_url": "https://avatars.githubusercontent.com/u/6764390?v=3",
//                "gravatar_id": "",
//                "url": "https://api.github.com/users/elastic",
//                "html_url": "https://github.com/elastic",
//    "stargazers_count": 17251,
//            "watchers_count": 17251,
//            "language": "Java",
//            "has_issues": true,
//            "has_downloads": true,
//            "has_wiki": false,
//            "has_pages": false,
//            "forks_count": 5839,
//            "mirror_url": null,
//            "open_issues_count": 1080,
//            "forks": 5839,
//            "open_issues": 1080,
}
