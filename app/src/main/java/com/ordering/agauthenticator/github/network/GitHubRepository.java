package com.ordering.agauthenticator.github.network;

import androidx.annotation.NonNull;

import com.ordering.agauthenticator.github.entity.SearchResponse;
import com.ordering.agauthenticator.github.db.GithubModel;
import com.ordering.agauthenticator.github.helper.GITCONSTANTS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class GitHubRepository {
    private final GitHubApiService apiService;


    public GitHubRepository() {
        apiService = ApiClient.getClient().create(GitHubApiService.class);
    }

    public void fetchUserRepos(NetworkCallback<List<GithubModel>> callback) {
        apiService.getUserRepos(GITCONSTANTS.ACCESS_TOKEN).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GithubModel>> call, @NonNull Response<List<GithubModel>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<GithubModel>> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }

    public void searchRepo(String query, NetworkCallback<List<GithubModel>> callback) {
        apiService.searchRepositories(GITCONSTANTS.ACCESS_TOKEN, query).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().items);
                } else {
                    callback.onError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }
}
