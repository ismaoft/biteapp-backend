package com.example.bite_api.services

import com.example.bite_api.EntregaDTO
import com.example.bite_api.entities.Entrega
import com.example.bite_api.firebase.FirebaseService
import com.example.bite_api.mappers.EntregaMapper
import com.example.bite_api.repositories.EntregaRepository
import com.example.bite_api.repositories.TransaccionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class EntregaService @Autowired constructor(
    private val entregaRepository: EntregaRepository,
    private val firebaseService: FirebaseService,
    private val transaccionRepository: TransaccionRepository,
    private val entregaMapper: EntregaMapper
) {

    // Crear una nueva entrega
    fun crearEntrega(transaccionId: Int): EntregaDTO {
        val transaccion = transaccionRepository.findById(transaccionId)
            .orElseThrow { RuntimeException("Transacción no encontrada") }

        val repartidor = transaccion.restaurante?.administrador
            ?: throw RuntimeException("No se encontró un administrador para el restaurante")

        val nuevaEntrega = Entrega().apply {
            this.transaccion = transaccion
            this.repartidor = repartidor
            this.estado = Entrega.PENDIENTE
            this.fechaCreacion = Timestamp(System.currentTimeMillis())
            this.fechaEntregaEstimada = Timestamp(System.currentTimeMillis() + 3600000) // 1 hora
        }

        val entregaGuardada = entregaRepository.save(nuevaEntrega)
        return entregaMapper.entregaToEntregaDTO(entregaGuardada)
    }


    // Actualizar estado de una entrega
// Actualizar estado de una entrega
    fun actualizarEstadoEntrega(entregaId: Int, nuevoEstado: String): EntregaDTO {
        val entrega = entregaRepository.findById(entregaId)
            .orElseThrow { RuntimeException("Entrega no encontrada") }

        entrega.estado = nuevoEstado
        val entregaActualizada = entregaRepository.save(entrega)

        // Enviar notificaciones según el estado
        when (nuevoEstado) {
            Entrega.CAMINO -> {
                val clienteToken = entregaActualizada.transaccion?.usuario?.tokenFCM
                if (clienteToken.isNullOrEmpty()) {
                    println("Token FCM del cliente es nulo o vacío.")
                } else {
                    println("Enviando notificación al cliente con token: $clienteToken")
                    firebaseService.enviarNotificacion(
                        clienteToken,
                        "¡Tu pedido está en camino!",
                        "Sigue tu pedido en tiempo real."
                    )
                }
            }
            Entrega.ENTREGADO -> {
                val clienteToken = entregaActualizada.transaccion?.usuario?.tokenFCM
                if (clienteToken.isNullOrEmpty()) {
                    println("Token FCM del cliente es nulo o vacío.")
                } else {
                    println("Enviando notificación al cliente con token: $clienteToken")
                    firebaseService.enviarNotificacion(
                        clienteToken,
                        "Pedido entregado",
                        "Gracias por tu compra."
                    )
                }
            }
        }

        return entregaMapper.entregaToEntregaDTO(entregaActualizada)
    }



    // Obtener una entrega por ID
    fun obtenerEntregaPorId(entregaId: Int): EntregaDTO? {
        val entrega = entregaRepository.findById(entregaId).orElse(null)
        return entrega?.let { entregaMapper.entregaToEntregaDTO(it) }
    }

    // Obtener todas las entregas
    fun obtenerTodasLasEntregas(): List<EntregaDTO> {
        val entregas = entregaRepository.findAll()
        return entregas.map { entregaMapper.entregaToEntregaDTO(it) }
    }

    fun calcularDistanciaEntrega(entregaId: Int): Double {
        val entrega = entregaRepository.findById(entregaId)
            .orElseThrow { RuntimeException("Entrega no encontrada") }

        val transaccion = entrega.transaccion
            ?: throw RuntimeException("Transacción no encontrada para la entrega")

        val cliente = transaccion.usuario
            ?: throw RuntimeException("Usuario no encontrado para la transacción")

        val restaurante = transaccion.restaurante
            ?: throw RuntimeException("Restaurante no encontrado para la transacción")

        // Verificar si las coordenadas están definidas
        if (cliente.latitud == null || cliente.longitud == null || restaurante.latitud == null || restaurante.longitud == null) {
            throw RuntimeException("Faltan coordenadas para calcular la distancia")
        }

        // Calcular la distancia usando la fórmula de Haversine
        val earthRadiusKm = 6371.0
        val dLat = Math.toRadians(restaurante.latitud!! - cliente.latitud!!)
        val dLon = Math.toRadians(restaurante.longitud!! - cliente.longitud!!)
        val lat1 = Math.toRadians(cliente.latitud!!)
        val lat2 = Math.toRadians(restaurante.latitud!!)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadiusKm * c
    }

}
