package com.example.bite_api.strategies

import org.springframework.stereotype.Component

@Component
class RecommendationContext(private var strategy: RecommendationStrategy) {

    fun setStrategy(newStrategy: RecommendationStrategy) {
        strategy = newStrategy
    }

    fun getRecommendation(preferences: List<String>, allergies: List<String>, userId: Int): String {
        return strategy.recommendProduct(preferences, allergies, userId)
    }
}

