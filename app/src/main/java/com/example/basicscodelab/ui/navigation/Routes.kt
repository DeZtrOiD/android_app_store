package com.example.basicscodelab.ui.navigation

object Routes {
    const val Onboarding = "onboarding"
    const val Store = "store"
    const val Categories = "categories"
    const val Details = "details"
    const val Screenshots = "screenshots"

    const val CategoryArg = "category"
    const val AppIdArg = "appId"
    const val StartIndexArg = "startIndex"

    const val StorePattern = "$Store?$CategoryArg={$CategoryArg}"
    fun store(categoryName: String? = null): String {
        return if (categoryName.isNullOrBlank()) {
            Store
        } else {
            "$Store?$CategoryArg=$categoryName"
        }
    }

    const val DetailsPattern = "$Details/{$AppIdArg}"
    fun details(appId: Int): String = "$Details/$appId"

    const val ScreenshotsPattern = "$Screenshots/{$AppIdArg}/{$StartIndexArg}"
    fun screenshots(appId: Int, startIndex: Int): String = "$Screenshots/$appId/$startIndex"
}