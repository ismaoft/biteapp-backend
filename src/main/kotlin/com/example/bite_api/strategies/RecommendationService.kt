package com.example.bite_api.strategies

import com.example.bite_api.services.UsuarioService
import org.springframework.stereotype.Service

@Service
class RecommendationService(
    private val recommendationContext: RecommendationContext,
    private val usuarioService: UsuarioService
) {

    fun getRecommendation(userId: Int): String {
        val usuario = usuarioService.findById(userId)
            ?: throw RuntimeException("Usuario no encontrado")

        val preferencias = usuario.preferencias?.split(",") ?: emptyList()
        val alergias = usuario.alergias?.split(",") ?: emptyList()

        return recommendationContext.getRecommendation(preferencias, alergias, userId)
    }
}

