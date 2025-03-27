package com.ordering.agauthenticator.github.network;


import com.ordering.agauthenticator.github.entity.SearchResponse;
import com.ordering.agauthenticator.github.db.GithubModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GitHubApiService {
    @GET("user/repos")
    Call<List<GithubModel>> getUserRepos(@Header("Authorization") String authToken);

    @GET("search/repositories")
    Call<SearchResponse> searchRepositories(@Header("Authorization") String authToken, @Query("q") String query);
}

