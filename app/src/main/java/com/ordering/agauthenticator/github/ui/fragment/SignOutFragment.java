package com.ordering.agauthenticator.github.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ordering.agauthenticator.ApplicationHelper;
import com.ordering.agauthenticator.R;
import com.ordering.agauthenticator.databinding.SignOutLayoutBinding;
import com.ordering.agauthenticator.github.viewmodel.GithubViewModel;
import com.ordering.agauthenticator.github.util.AuthPreferences;

public class SignOutFragment extends Fragment {
    private SignOutLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_out_layout, container, false);
        binding = SignOutLayoutBinding.bind(view);
        setUpScreen();
        return binding.getRoot();
    }

    public void setUpScreen() {
        binding.signoutContainer.setOnClickListener(v -> {
            GithubViewModel githubViewModel = new ViewModelProvider(requireActivity()).get(GithubViewModel.class);
            githubViewModel.deleteRepos();

            AuthPreferences authPreferences = new AuthPreferences(requireContext());
            authPreferences.clearTokens();
            ApplicationHelper.getCurrentActivity().openLoginActivity();
        });

    }
}
