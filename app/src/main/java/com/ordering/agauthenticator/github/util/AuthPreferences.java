package com.ordering.agauthenticator.github.util;

import android.content.Context;
import android.content.SharedPreferences;

// Utility class to manage SharedPreferences
public class AuthPreferences {

    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_GOOGLE_TOKEN = "google_token";
    private static final String KEY_GITHUB_TOKEN = "github_token";

    private SharedPreferences sharedPreferences;

    public AuthPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveGoogleToken(String token) {
        sharedPreferences.edit().putString(KEY_GOOGLE_TOKEN, token).apply();
    }

    public String getGoogleToken() {
        return sharedPreferences.getString(KEY_GOOGLE_TOKEN, null);
    }

    public void saveGitHubToken(String token) {
        sharedPreferences.edit().putString(KEY_GITHUB_TOKEN, token).apply();
    }

    public String getGitHubToken() {
        return sharedPreferences.getString(KEY_GITHUB_TOKEN, null);
    }

    public void clearTokens() {
        sharedPreferences.edit().clear().apply();
    }
}
