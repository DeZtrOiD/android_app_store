package com.example.basicscodelab.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.data.model.AppCategory
import com.example.basicscodelab.data.model.AppInfo
import com.example.basicscodelab.ui.components.AppCard
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    apps: List<AppInfo>,
    isLoading: Boolean,
    errorMessage: String?,
    categoryName: String?,
    onOpenApp: (Int) -> Unit,
    onOpenCategories: () -> Unit,
    onRetry: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    val selectedCategory = AppCategory.fromJsonName(categoryName)
    val visibleApps = apps.filter { selectedCategory == null || it.category == selectedCategory }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VKStore") },
                navigationIcon = {
                    if (onBack != null) {
                        TextButton(onClick = onBack) {
                            Text("Назад")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onOpenCategories) {
                    Text("К списку категорий")
                }

                if (selectedCategory != null) {
                    Text(
                        text = selectedCategory.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(24.dp))
                }

                errorMessage != null -> {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                        TextButton(onClick = onRetry) {
                            Text("Повторить")
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(visibleApps) { app ->
                            AppCard(
                                app = app,
                                onClick = { onOpenApp(app.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
