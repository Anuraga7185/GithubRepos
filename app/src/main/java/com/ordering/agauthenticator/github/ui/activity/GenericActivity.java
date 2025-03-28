package com.ordering.agauthenticator.github.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class GenericActivity extends AppCompatActivity {
    public Fragment currentFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(getFragmentContainer(), fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public void firstFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().add(getFragmentContainer(), fragment).commit();
    }

    public int getFragmentContainer() {
        return 0;
    }

    public void openLoginActivity() {

    }

}
