package com.ordering.agauthenticator.github.ui.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ordering.agauthenticator.ApplicationHelper;
import com.ordering.agauthenticator.databinding.ItemRepooBinding;
import com.ordering.agauthenticator.github.db.GithubModel;
import com.ordering.agauthenticator.github.ui.fragment.DetailFragment;
import com.ordering.agauthenticator.github.viewmodel.GithubViewModel;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private List<GithubModel> repos;

    public RepoAdapter(List<GithubModel> repos) {
        this.repos = repos == null ? new ArrayList<>() : repos;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoViewHolder(ItemRepooBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        GithubModel repo = repos.get(position);
        holder.binding.getRoot().setOnClickListener(v -> {
            GithubViewModel githubModel = new ViewModelProvider(ApplicationHelper.getCurrentActivity()).get(GithubViewModel.class);
            githubModel.setSelected(repo);
            DetailFragment fragment = new DetailFragment();
            ApplicationHelper.getCurrentActivity().updateFragment(fragment);
        });
        holder.binding.repoName.setText(repo.name);
        holder.binding.repoDescription.setText(repo.description);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRepos(List<GithubModel> repos) {
        this.repos = repos;
        notifyDataSetChanged();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        public ItemRepooBinding binding;

        public RepoViewHolder(@NonNull ItemRepooBinding itemRepooBinding) {
            super(itemRepooBinding.getRoot());
            binding = itemRepooBinding;
        }
    }
}