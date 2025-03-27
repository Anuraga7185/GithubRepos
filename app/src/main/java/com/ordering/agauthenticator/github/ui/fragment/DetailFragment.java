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

        setDatatoUI();
        return binding.getRoot();
    }

    private void setDatatoUI() {
        if (getArguments() == null) {
            return;
        }
        GithubViewModel githubViewModel = new ViewModelProvider(requireActivity()).get(GithubViewModel.class);

        long pk = getArguments().getLong("repoPk");
        githubViewModel.getRepoByPK(pk, this::setRepoData);
    }

    private void setRepoData(GithubModel githubModel) {
        binding.description.setText(githubModel.description == null ? "No Description" : githubModel.description);
        binding.owner.setText(githubModel.owner == null ? "No Owner" : githubModel.owner.login);
        binding.repoName.setText(githubModel.name == null ? "No RepoName" : githubModel.name);
        binding.usages.setText(githubModel.topics == null ? "No usages" : githubModel.topics.stream().reduce("", (a, b) -> a + b));
        binding.starred.setText( String.valueOf(githubModel.watchers_count));
    }
}
