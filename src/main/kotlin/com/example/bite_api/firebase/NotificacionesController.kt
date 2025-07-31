package com.example.bite_api.firebase

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notificaciones")
class NotificacionesController(
    private val firebaseService: FirebaseService
) {

    @PostMapping("/enviar")
    fun enviarNotificacion(
        @RequestBody request: NotificacionRequest
    ): ResponseEntity<String> {
        return try {
            firebaseService.enviarNotificacion(request.token, request.titulo, request.mensaje)
            ResponseEntity.ok("Notificación enviada exitosamente.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error al enviar notificación: ${e.message}")
        }
    }
}

data class NotificacionRequest(
    val token: String,
    val titulo: String,
    val mensaje: String
)
