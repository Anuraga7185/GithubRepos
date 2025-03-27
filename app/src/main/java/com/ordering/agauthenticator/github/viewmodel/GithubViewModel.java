package com.ordering.agauthenticator.github.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ordering.agauthenticator.github.db.GithubDatabase;
import com.ordering.agauthenticator.github.db.GithubModel;

import java.util.List;
import java.util.concurrent.Executors;

public class GithubViewModel extends AndroidViewModel {
    private final GithubDatabase githubDatabase;
    private GithubModel githubModel = null;
    public MutableLiveData<List<GithubModel>> repos = new MutableLiveData<>();

    public GithubViewModel(@NonNull Application application) {
        super(application);

        githubDatabase = GithubDatabase.getGitDbInstance(application);
        setObserver();
    }

    private void setObserver() {
        githubDatabase.githubDao().getAllReposLiveData().observeForever(repos::setValue);
    }

    public LiveData<List<GithubModel>> getAllRepos() {
        return repos;
    }

    public void setSelected(GithubModel repos) {
        this.githubModel = repos;
    }

    public GithubModel getSelected() {
        return this.githubModel;
    }

    public void setRepos(List<GithubModel> repos) {
        this.repos.setValue(repos);
    }

    public void insertRepo(GithubModel repo) {
        Executors.newSingleThreadExecutor().execute(() -> githubDatabase.githubDao().insertRepo(repo));
    }

    public void getRepoByPK(long pk, DATA_CALLBACK dataCallback) {
        Executors.newSingleThreadExecutor().execute(() ->
                dataCallback.onSuccess(githubDatabase.githubDao().getRepo(pk)));
    }

    public void getRepoById(double repoId, DATA_CALLBACK dataCallback) {
        Executors.newSingleThreadExecutor().execute(() ->
                dataCallback.onSuccess(githubDatabase.githubDao().getRepoById(repoId)));
    }

    public void deleteRepos() {
        Executors.newSingleThreadExecutor().execute(() -> githubDatabase.githubDao().deleteAllRepos());
    }

    public interface DATA_CALLBACK {
        void onSuccess(GithubModel repos);
    }

}
