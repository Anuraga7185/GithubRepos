package com.ordering.agauthenticator.github.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ordering.agauthenticator.github.entity.Owner;

import java.util.ArrayList;

@Entity(tableName = "git_repos")
public class GithubModel {

    @PrimaryKey(autoGenerate = true)
    public long primaryKey;

    public String name;
    public String description;
    public double id;
    @Ignore
    public Owner owner;
    @Ignore
    public ArrayList<String> topics;

    public int watchers_count;


}
