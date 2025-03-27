package com.ordering.agauthenticator.github.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ordering.agauthenticator.ApplicationHelper;
import com.ordering.agauthenticator.github.ui.fragment.HomeFragment;
import com.ordering.agauthenticator.databinding.MainActivityBinding;
import com.ordering.agauthenticator.github.viewmodel.GithubViewModel;

public class DashboardActivity extends GenericActivity {

    private MainActivityBinding binding;
    public GithubViewModel githubViewModel;

    @Override
    protected void onDestroy() {
        ApplicationHelper.clearActivity();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        githubViewModel = new ViewModelProvider(this).get(GithubViewModel.class);

        ApplicationHelper.setCurrentActivity(this);
        firstFragment(new HomeFragment());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void openLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public int getFragmentContainer() {
        return binding.frameLayout.getId();
    }

}
