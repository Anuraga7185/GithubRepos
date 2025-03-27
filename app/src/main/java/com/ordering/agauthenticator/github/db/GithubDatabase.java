package com.ordering.agauthenticator.github.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GithubModel.class}, version = 1, exportSchema = false)
abstract public class GithubDatabase extends RoomDatabase {
    abstract public GithubDao githubDao();

    private static GithubDatabase GIT_DB_INSTANCE;

    public static GithubDatabase getGitDbInstance(Context context) {
        if (GIT_DB_INSTANCE == null) {
            synchronized (GithubDatabase.class) {
                GIT_DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                GithubDatabase.class, "git_db")
                        .build();
            }
        }
        return GIT_DB_INSTANCE;
    }
}
