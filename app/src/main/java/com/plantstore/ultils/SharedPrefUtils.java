package com.plantstore.ultils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.plantstore.models.api.UserResponse;

public class SharedPrefUtils {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_RESPONSE = "UserResponse";

    // Save the user response locally
    public static void saveUserResponseLocally(Context context, UserResponse userResponse) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Convert the UserResponse object to JSON using Gson
        String userResponseJson = new Gson().toJson(userResponse);

        // Save the JSON string in SharedPreferences
        editor.putString(KEY_USER_RESPONSE, userResponseJson);
        editor.apply();
    }

    // Retrieve the saved user response
    public static UserResponse getSavedUserResponse(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String userResponseJson = prefs.getString(KEY_USER_RESPONSE, null);

        // Convert the JSON string back to UserResponse object using Gson
        if (userResponseJson != null) {
            return new Gson().fromJson(userResponseJson, UserResponse.class);
        }

        return null;
    }
    // Remove the saved user response
    public static void removeSavedUserResponse(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.remove(KEY_USER_RESPONSE);
        editor.apply();
    }
}
