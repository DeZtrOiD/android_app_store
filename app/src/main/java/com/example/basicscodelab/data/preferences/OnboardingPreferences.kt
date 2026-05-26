package com.example.basicscodelab.data.preferences

import android.content.Context

object OnboardingPreferences {
    private const val PREFS_NAME = "vkstore_prefs"
    private const val KEY_ONBOARDING_DONE = "onboarding_done"

    fun isOnboardingCompleted(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ONBOARDING_DONE, false)
    }

    fun setOnboardingCompleted(context: Context, completed: Boolean = true) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_ONBOARDING_DONE, completed)
            .apply()
    }
}
