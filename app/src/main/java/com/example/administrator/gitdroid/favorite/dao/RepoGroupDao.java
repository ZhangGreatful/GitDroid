package com.example.administrator.gitdroid.favorite.dao;

import com.example.administrator.gitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 在这里执行数据的增,删,改,查的工作
 * Created by Administrator on 2016/7/10 0010.
 */
public class RepoGroupDao {

    private Dao<RepoGroup, Long> dao;

    public RepoGroupDao(DbHelper dbHelper) {
        try {
            dao = dbHelper.getDao(RepoGroup.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatOrUpdate(RepoGroup repoGroup) {
        try {
            dao.createOrUpdate(repoGroup);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    添加和更新数据
    public void creatOrUpdate(List<RepoGroup> repoGroups) {
        for (RepoGroup repoGroup : repoGroups) {
            creatOrUpdate(repoGroup);
        }
    }

    //    查询所有的数据
    public List<RepoGroup> queryForAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RepoGroup queryForId(long id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
