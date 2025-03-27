package com.ordering.agauthenticator.github.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ordering.agauthenticator.databinding.DetailLayoutBinding;
import com.ordering.agauthenticator.github.db.GithubModel;
import com.ordering.agauthenticator.github.viewmodel.GithubViewModel;

public class DetailFragment extends Fragment {
    private DetailLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailLayoutBinding.inflate(inflater, container, false);

        setDataToUI();

        return binding.getRoot();
    }

    private void setDataToUI() {
        GithubViewModel githubViewModel = new ViewModelProvider(requireActivity()).get(GithubViewModel.class);
        setRepoData(githubViewModel.getSelected());
    }

    private void setRepoData(GithubModel githubModel) {
        binding.description.setText(githubModel == null || githubModel.description == null ? "No Description" : githubModel.description);
        binding.owner.setText(githubModel == null || githubModel.owner == null ? "No Owner" : githubModel.owner.login);
        binding.repoName.setText(githubModel == null || githubModel.name == null ? "No RepoName" : githubModel.name);
        binding.usages.setText(githubModel == null || githubModel.topics == null || githubModel.topics.isEmpty() ? "No usages" : String.join(", ", githubModel.topics));
        binding.starred.setText(String.valueOf(githubModel == null ? 0 : githubModel.watchers_count));
    }
}
