package com.example.administrator.gitdroid.github.hotrepo.pager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 热门仓库API结果
 * Created by Administrator on 2016/7/7 0007.
 */
public class RepoResult {
    //    "total_count":2074901,
//    "incomplete_results":false,
//    "items":[]
    @SerializedName("total_count")
    private int     totalCount;
    @SerializedName("incomplete_results")
    private boolean incompleteResults;
    @SerializedName("items")
    private List<Repo>  repoList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }
}
