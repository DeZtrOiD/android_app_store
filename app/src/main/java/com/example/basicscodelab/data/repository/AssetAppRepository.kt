package com.example.basicscodelab.data.repository

import android.content.Context
import com.example.basicscodelab.data.model.AgeRating
import com.example.basicscodelab.data.model.AppCategory
import com.example.basicscodelab.data.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

class AssetAppRepository(
    private val context: Context
) : AppRepository {

    override suspend fun getApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val json = context.assets.open("apps.json").bufferedReader().use { it.readText() }
        val array = JSONArray(json)

        buildList {
            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)

                val screenshotNames = buildList {
                    val shots = item.getJSONArray("screenshotNames")
                    for (j in 0 until shots.length()) {
                        add(shots.getString(j))
                    }
                }

                val category = AppCategory.fromJsonName(item.optString("category"))
                    ?: AppCategory.TOOLS

                add(
                    AppInfo(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        developer = item.getString("developer"),
                        category = category,
                        ageRating = AgeRating.fromLabel(item.optString("ageRating")),
                        shortDescription = item.getString("shortDescription"),
                        fullDescription = item.getString("fullDescription"),
                        iconName = item.getString("iconName"),
                        screenshotNames = screenshotNames,
                        apkUrl = item.optString("apkUrl").takeIf { it.isNotBlank() && it != "null" }
                    )
                )
            }
        }.sortedBy { it.id }
    }
}
