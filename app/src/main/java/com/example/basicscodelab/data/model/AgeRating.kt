package com.example.basicscodelab.data.model

enum class AgeRating(val label: String) {
    ZERO("0+"),
    SIX("6+"),
    EIGHT("8+"),
    TWELVE("12+"),
    SIXTEEN("16+"),
    EIGHTEEN("18+");

    companion object {
        fun fromLabel(value: String?): AgeRating {
            if (value.isNullOrBlank()) return ZERO
            return values().firstOrNull { it.label == value } ?: ZERO
        }
    }
}
