package com.ordering.agauthenticator;

import com.ordering.agauthenticator.github.ui.activity.GenericActivity;

public class ApplicationHelper {
    public static GenericActivity currentActivity;

    public static GenericActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(GenericActivity activity) {
        currentActivity = activity;
    }

    public static void clearActivity() {
        currentActivity = null;
    }
}
