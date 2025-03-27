package com.ordering.agauthenticator.github.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GithubDao {
    @Query("SELECT * FROM git_repos")
    List<GithubModel> getAllRepos();

    @Query("SELECT * FROM git_repos")
    LiveData<List<GithubModel>> getAllReposLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepo(GithubModel repo);

    @Query("DELETE FROM git_repos")
    void deleteAllRepos();

    @Query("SELECT * FROM git_repos WHERE primaryKey = :pk")
    GithubModel getRepo(long pk);

    @Query("SELECT * FROM git_repos WHERE id = :repoId")
    GithubModel getRepoById(double repoId);
}
