package com.example.basicscodelab.data.repository

import com.example.basicscodelab.data.model.AppInfo

interface AppRepository {
    suspend fun getApps(): List<AppInfo>
}