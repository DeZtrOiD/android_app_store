package com.example.basicscodelab.data.model

data class AppInfo(
    val id: Int,
    val name: String,
    val developer: String,
    val category: AppCategory,
    val ageRating: AgeRating,
    val shortDescription: String,
    val fullDescription: String,
    val iconName: String,
    val screenshotNames: List<String>,
    val apkUrl: String? = null
)
