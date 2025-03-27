package com.ordering.agauthenticator.github.network;

public interface NetworkCallback<T> {
    void onSuccess(T response);

    void onError(String error);
}
