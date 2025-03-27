package com.ordering.agauthenticator.github.entity;

import com.ordering.agauthenticator.github.db.GithubModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {
    //    List<Repository> items = new ArrayList<>();
    public List<GithubModel> items = new ArrayList<>();
    public int total_count;
    public boolean incomplete_results;
}
