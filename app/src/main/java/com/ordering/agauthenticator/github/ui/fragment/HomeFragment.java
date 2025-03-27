package com.ordering.agauthenticator.github.ui.fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ordering.agauthenticator.ApplicationHelper;
import com.ordering.agauthenticator.R;
import com.ordering.agauthenticator.github.helper.NetworkChangeReceiver;
import com.ordering.agauthenticator.github.ui.adapter.RepoAdapter;
import com.ordering.agauthenticator.databinding.DashboardLayoutGitBinding;
import com.ordering.agauthenticator.github.db.GithubModel;
import com.ordering.agauthenticator.github.network.GitHubRepository;
import com.ordering.agauthenticator.github.network.NetworkCallback;
import com.ordering.agauthenticator.github.viewmodel.GithubViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private DashboardLayoutGitBinding binding;
    private RepoAdapter adapter;
    public GithubViewModel githubViewModel;
    private NetworkChangeReceiver networkChangeReceiver;

    public boolean isNetworkAvailable = true;
    public GitHubRepository repository = new GitHubRepository();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout_git, container, false);
        binding = DashboardLayoutGitBinding.bind(view);

        githubViewModel = new ViewModelProvider(this).get(GithubViewModel.class);
        networkChangeReceiver = new NetworkChangeReceiver(binding, isNetworkAvailable -> HomeFragment.this.isNetworkAvailable = isNetworkAvailable);


        setAdapter();
        fragmentActions();
        setObserver();
        searchAction();
        fetchSelfRepos();

        return binding.getRoot();
    }

    private void setAdapter() {
        binding.recyler.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        adapter = new RepoAdapter(githubViewModel.getAllRepos().getValue());
        binding.recyler.setAdapter(adapter);
    }

    private void setObserver() {
        githubViewModel.getAllRepos().observe(requireActivity(), this::notifyUIListUpdated);
    }

    private void notifyUIListUpdated(List<GithubModel> repositories) {
        if (repositories != null && !repositories.isEmpty()) {
            binding.recyler.setVisibility(View.VISIBLE);
            adapter.setRepos(repositories);
        }
    }

    private void fragmentActions() {
        binding.signout.setOnClickListener(v -> ApplicationHelper.getCurrentActivity().updateFragment(new SignOutFragment()));

        binding.swipeRefresh.setOnRefreshListener(() -> {
            disableTouch();
            fetchSelfRepos();
            enableTouch();
        });

    }

    private void fetchSelfRepos() {
        if (!isNetworkAvailable) {
            List<GithubModel> repos = (githubViewModel != null && githubViewModel.getAllRepos().getValue() != null) ? githubViewModel.getAllRepos().getValue() : new ArrayList<>();
            updateUserRepos(repos);
            return;
        }
        // Fetch User Repositories
        repository.fetchUserRepos(new NetworkCallback<>() {
            @Override
            public void onSuccess(List<GithubModel> repos) {
                ApplicationHelper.getCurrentActivity().runOnUiThread(() -> updateUserRepos(repos));
            }

            @Override
            public void onError(String error) {
                System.out.println("Error: " + error);
            }
        });
    }

    private void searchRepos(String query) {

        // Search Repositories
        repository.searchRepo(query, new NetworkCallback<>() {
            @Override
            public void onSuccess(List<GithubModel> repos) {
                ApplicationHelper.getCurrentActivity().runOnUiThread(() -> HomeFragment.this.notifyUIListUpdated(repos));
            }

            @Override
            public void onError(String error) {
                System.out.println("Error: " + error);
            }
        });
    }


    private void searchAction() {
        if (!isNetworkAvailable) {
            Toast.makeText(requireContext(), "Cannot Fetch Data, Network Erro!!", Toast.LENGTH_LONG).show();
        }
        binding.search.setOnSearchClickListener(v -> binding.textHeader.setVisibility(View.GONE));

        binding.search.setOnCloseListener(() -> {
            binding.textHeader.setVisibility(View.VISIBLE);
            notifyUIListUpdated(githubViewModel.getAllRepos() == null ? new ArrayList<>() : githubViewModel.getAllRepos().getValue());
            return false;
        });

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && newText.trim().length() > 3) {
                    searchRepos(newText);
                }
                return false;
            }
        });
    }

    private void disableTouch() {
        ApplicationHelper.getCurrentActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void enableTouch() {
        ApplicationHelper.getCurrentActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void updateUserRepos(List<GithubModel> selfRepos) {

        binding.progressBar.setVisibility(View.GONE);
        binding.overlay.setVisibility(View.GONE);
        binding.swipeRefresh.setRefreshing(false);
        if (selfRepos.isEmpty()) {
            return;
        }
        githubViewModel.deleteRepos();
        selfRepos.forEach(githubModel -> {
            githubViewModel.insertRepo(githubModel);
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        if (binding.search.isIconified()) {
            binding.textHeader.setVisibility(View.VISIBLE);
        } else {
            binding.textHeader.setVisibility(View.GONE);
        }
        requireContext().registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(networkChangeReceiver);
    }
}
