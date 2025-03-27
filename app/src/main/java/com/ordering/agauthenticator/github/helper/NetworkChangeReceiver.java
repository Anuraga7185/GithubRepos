package com.ordering.agauthenticator.github.helper;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.ordering.agauthenticator.databinding.DashboardLayoutGitBinding;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private final DashboardLayoutGitBinding binding;
    private final NetworkCallback networkCallback;

    public NetworkChangeReceiver(DashboardLayoutGitBinding binding, NetworkCallback networkCallback) {
        this.binding = binding;
        this.networkCallback = networkCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isInternetAvailable(context)) {
            if (networkCallback != null) {
                networkCallback.onUpdate(false);
            }
            Toast.makeText(context, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            binding.noInternetLayout.setVisibility(View.VISIBLE);
            binding.swipeRefresh.setEnabled(false);
        } else {
            if (networkCallback != null) {
                networkCallback.onUpdate(false);
            }
            binding.swipeRefresh.setEnabled(true);
            binding.noInternetLayout.setVisibility(View.GONE);
            Toast.makeText(context, "Internet Connected!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            android.net.Network network = cm.getActiveNetwork();
            if (network == null) return false;

            NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        } else {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }

    public interface NetworkCallback {
        void onUpdate(boolean isNetworkAvailable);
    }
}
