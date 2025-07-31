package com.example.bite_api.services

import com.example.bite_api.TransaccionDTO
import com.example.bite_api.entities.Transaccion
import com.example.bite_api.mappers.TransaccionMapper
import com.example.bite_api.repositories.TransaccionRepository
import com.example.bite_api.repositories.UsuarioRepository
import com.example.bite_api.repositories.RestauranteRepository
import com.example.bite_api.firebase.FirebaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

@Service
class TransaccionService @Autowired constructor(
    private val transaccionRepository: TransaccionRepository,
    private val transaccionMapper: TransaccionMapper,
    private val usuarioRepository: UsuarioRepository,
    private val firebaseService: FirebaseService,
    private val restauranteRepository: RestauranteRepository,

) {

    fun findAll(): List<TransaccionDTO> {
        val transacciones = transaccionRepository.findAll()
        return transacciones.map { transaccionMapper.transaccionToTransaccionDTO(it) }
    }

    fun findById(transaccionId: Int): TransaccionDTO? {
        val transaccion: Optional<Transaccion> = transaccionRepository.findById(transaccionId)
        return if (transaccion.isPresent) transaccionMapper.transaccionToTransaccionDTO(transaccion.get()) else null
    }

    fun create(transaccionDTO: TransaccionDTO): TransaccionDTO {
        // Obtener el usuario relacionado
        val usuario = usuarioRepository.findUsuarioByUserId(transaccionDTO.userId!!)
            ?: throw RuntimeException("Usuario no encontrado")

        // Obtener el restaurante relacionado
        val restaurante = restauranteRepository.findById(transaccionDTO.restaurantId!!)
            .orElseThrow { RuntimeException("Restaurante no encontrado") }

        // Crear la entidad Transaccion
        val transaccion = Transaccion().apply {
            this.usuario = usuario
            this.restaurante = restaurante
            this.fechaTransaccion = transaccionDTO.fechaTransaccion
            this.estado = transaccionDTO.estado
            this.total = transaccionDTO.total
        }

        // Guardar la transacción
        val savedTransaccion = transaccionRepository.save(transaccion)
        return transaccionMapper.transaccionToTransaccionDTO(savedTransaccion)
    }


    fun update(transaccionId: Int, transaccionDTO: TransaccionDTO): TransaccionDTO? {
        if (transaccionRepository.existsById(transaccionId)) {
            val transaccion = transaccionMapper.transaccionDTOToTransaccion(transaccionDTO)
            transaccion.transaccionId = transaccionId
            val updatedTransaccion = transaccionRepository.save(transaccion)
            return transaccionMapper.transaccionToTransaccionDTO(updatedTransaccion)
        }
        return null
    }

    fun delete(transaccionId: Int) {
        if (transaccionRepository.existsById(transaccionId)) {
            transaccionRepository.deleteById(transaccionId)
        }
    }

    fun obtenerOCrearCarrito(userId: Int): TransaccionDTO {
        // Obtener las transacciones pendientes del usuario
        val transaccionesPendientes = transaccionRepository.findByUsuarioUserId(userId)
            .filter { it.estado == "pendiente" }

        // Si ya existe un carrito pendiente, devolverlo
        if (transaccionesPendientes.isNotEmpty()) {
            return transaccionMapper.transaccionToTransaccionDTO(transaccionesPendientes.first())
        }

        // Crear un nuevo carrito si no existe uno pendiente
        val usuario = usuarioRepository.findUsuarioByUserId(userId)
        val nuevaTransaccion = Transaccion().apply {
            this.usuario = usuario
            this.estado = "pendiente"
            this.fechaTransaccion = Timestamp(System.currentTimeMillis())
            this.total = 0.0 // Total inicial.
        }

        val transaccionGuardada = transaccionRepository.save(nuevaTransaccion)
        return transaccionMapper.transaccionToTransaccionDTO(transaccionGuardada)
    }


    fun confirmarCarrito(transaccionId: Int): TransaccionDTO {
        val transaccion = transaccionRepository.findById(transaccionId)
            .orElseThrow { RuntimeException("Transacción no encontrada") }

        // Validar estado de la transacción
        if (transaccion.estado != "pendiente") {
            throw IllegalStateException("La transacción ya fue confirmada o no está en estado 'pendiente'")
        }

        // Cambiar el estado a 'confirmada'
        transaccion.estado = "confirmada"
        val transaccionActualizada = transaccionRepository.save(transaccion)

        // Notificar al restaurante
        val restauranteToken = transaccionActualizada.restaurante?.administrador?.tokenFCM
        restauranteToken?.let {
            try {
                firebaseService.enviarNotificacion(it, "Nuevo Pedido", "Tienes un nuevo pedido para preparar.")
            } catch (e: Exception) {
                println("Error enviando notificación: ${e.message}")
            }
        }

        return transaccionMapper.transaccionToTransaccionDTO(transaccionActualizada)
    }


    fun obtenerCarritoActivo(userId: Int): TransaccionDTO? {
        // Buscar transacciones pendientes del usuario
        val transaccionesPendientes = transaccionRepository.findByUsuarioUserId(userId)
            .filter { it.estado == "pendiente" }

        // Si existe un carrito pendiente, devolverlo
        if (transaccionesPendientes.isNotEmpty()) {
            return transaccionMapper.transaccionToTransaccionDTO(transaccionesPendientes.first())
        }
        return null
    }




}
