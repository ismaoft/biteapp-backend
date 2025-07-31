package com.example.bite_api.strategies

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/recommendation")
class RecommendationController(private val recommendationService: RecommendationService) {

    @GetMapping("/{userId}")
    fun getRecommendation(@PathVariable userId: Int): ResponseEntity<String> {
        val recommendation = recommendationService.getRecommendation(userId)
        return ResponseEntity.ok(recommendation)
    }
}
