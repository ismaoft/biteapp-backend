package com.example.bite_api.strategies

interface RecommendationStrategy {
    fun recommendProduct(preferences: List<String>, allergies: List<String>, userId: Int): String
}

