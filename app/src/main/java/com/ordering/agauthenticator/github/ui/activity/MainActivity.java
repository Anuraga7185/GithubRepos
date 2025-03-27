package com.ordering.agauthenticator.github.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.ordering.agauthenticator.github.util.AuthPreferences;
import com.ordering.agauthenticator.databinding.LoginLayoutBinding;

public class MainActivity extends GenericActivity {

    private static final String TAG = "LoginActivity";
    private GoogleSignInClient googleSignInClient;
    private AuthPreferences authPreferences;
    private LoginLayoutBinding binding;
    private static final String GOOGLE_WEB_CLIENT_ID = "788983845495-89gnbs87m7kb8hutte1kp829upqum5jf.apps.googleusercontent.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authPreferences = new AuthPreferences(this);
        if (authPreferences.getGoogleToken() != null) {
            Log.d(TAG, "Already LoggedIN");
            moveToDashboard();
        } else {
            setupGoogleSignIn();
        }
        binding.googleSignIn.setOnClickListener(v -> signIn());
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_WEB_CLIENT_ID)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                String token = account.getIdToken();
                Log.d(TAG, "Google ID Token: " + token);
                authPreferences.saveGoogleToken(account.getIdToken());
                moveToDashboard();
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void moveToDashboard() {
        Intent intent = new Intent(this, DashboardActiivity.class);
        startActivity(intent);
        finish();
    }
}