
package com.example.basicscodelab.data.model

enum class AppCategory(val displayName: String) {
    FINANCES("Финансы"),
    TOOLS("Инструменты"),
    GAMES("Игры"),
    GOVERNMENT("Государственные"),
    TRANSPORT("Транспорт");

    companion object {
        fun fromJsonName(value: String?): AppCategory? {
            if (value.isNullOrBlank()) return null
            return values().firstOrNull { it.name.equals(value, ignoreCase = true) }
        }
    }
}