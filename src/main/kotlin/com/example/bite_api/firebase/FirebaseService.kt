package com.example.bite_api.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Service

@Service
class FirebaseService {

    fun enviarNotificacion(token: String, titulo: String, mensaje: String) {
        try {
            println("Preparando notificación:")
            println("Token: $token")
            println("Título: $titulo")
            println("Mensaje: $mensaje")

            val message = Message.builder()
                .setToken(token)
                .putData("title", titulo)
                .putData("message", mensaje)
                .build()

            FirebaseMessaging.getInstance().send(message)
            println("Notificación enviada con éxito al token: $token")
        } catch (e: Exception) {
            println("Error al enviar notificación: ${e.message}")
        }
    }
}

