package com.example.bite_api.services

import com.example.bite_api.DetalleTransaccionDTO
import com.example.bite_api.entities.DetalleTransaccion
import com.example.bite_api.mappers.DetalleTransaccionMapper
import com.example.bite_api.repositories.DetalleTransaccionRepository
import com.example.bite_api.repositories.TransaccionRepository
import com.example.bite_api.repositories.ProductoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DetalleTransaccionService @Autowired constructor(
    private val detalleTransaccionRepository: DetalleTransaccionRepository,
    private val detalleTransaccionMapper: DetalleTransaccionMapper,
    private val transaccionRepository: TransaccionRepository,
    private val productoRepository: ProductoRepository

) {

    fun findAll(): List<DetalleTransaccionDTO> {
        val detalles = detalleTransaccionRepository.findAll()
        return detalles.map { detalleTransaccionMapper.detalleTransaccionToDetalleTransaccionDTO(it) }
    }

    fun findById(detalleId: Int): DetalleTransaccionDTO? {
        val detalle: Optional<DetalleTransaccion> = detalleTransaccionRepository.findById(detalleId)
        return if (detalle.isPresent) detalleTransaccionMapper.detalleTransaccionToDetalleTransaccionDTO(detalle.get()) else null
    }

    fun findDetallesByTransaccionId(transaccionId: Int): List<DetalleTransaccionDTO> {
        val detalles = detalleTransaccionRepository.findByTransaccionTransaccionId(transaccionId)
        return detalles.map { detalleTransaccionMapper.detalleTransaccionToDetalleTransaccionDTO(it) }
    }

    fun create(detalleTransaccionDTO: DetalleTransaccionDTO): DetalleTransaccionDTO {
        // Buscar la transacción relacionada
        val transaccion = transaccionRepository.findById(detalleTransaccionDTO.transaccionId!!)
            .orElseThrow { RuntimeException("Transacción no encontrada") }

        // Buscar el producto relacionado
        val producto = productoRepository.findById(detalleTransaccionDTO.productoId!!)
            .orElseThrow { RuntimeException("Producto no encontrado") }

        // Crear la entidad `DetalleTransaccion`
        val detalle = DetalleTransaccion().apply {
            this.transaccion = transaccion
            this.producto = producto
            this.cantidad = detalleTransaccionDTO.cantidad
            this.precioUnitario = detalleTransaccionDTO.precioUnitario
            this.subtotal = detalleTransaccionDTO.cantidad * detalleTransaccionDTO.precioUnitario
        }

        // Guardar el detalle
        val savedDetalle = detalleTransaccionRepository.save(detalle)
        return detalleTransaccionMapper.detalleTransaccionToDetalleTransaccionDTO(savedDetalle)
    }



    fun update(detalleId: Int, detalleTransaccionDTO: DetalleTransaccionDTO): DetalleTransaccionDTO? {
        if (detalleTransaccionRepository.existsById(detalleId)) {
            val detalle = detalleTransaccionMapper.detalleTransaccionDTOToDetalleTransaccion(detalleTransaccionDTO)
            detalle.detalleId = detalleId
            val updatedDetalle = detalleTransaccionRepository.save(detalle)
            return detalleTransaccionMapper.detalleTransaccionToDetalleTransaccionDTO(updatedDetalle)
        }
        return null
    }

    fun delete(detalleId: Int) {
        if (detalleTransaccionRepository.existsById(detalleId)) {
            detalleTransaccionRepository.deleteById(detalleId)
        }
    }

    fun agregarOActualizarDetalle(detalleDTO: DetalleTransaccionDTO): DetalleTransaccionDTO {
        val transaccion = transaccionRepository.findById(detalleDTO.transaccionId!!)
            .orElseThrow { RuntimeException("Transacción no encontrada") }

        // Verificar si el detalle existe
        val detalleExistente = detalleTransaccionRepository.findAll()
            .find { it.transaccion?.transaccionId == detalleDTO.transaccionId && it.producto?.productoId == detalleDTO.productoId }

        if (detalleExistente != null) {
            // Actualizar detalle existente
            detalleExistente.cantidad += detalleDTO.cantidad
            detalleExistente.subtotal = detalleExistente.cantidad * detalleExistente.precioUnitario
            val detalleGuardado = detalleTransaccionRepository.save(detalleExistente)
            return detalleTransaccionMapper.detalleTransaccionToDetalleTransaccionDTO(detalleGuardado)
        }

        // Crear un nuevo detalle
        val nuevoDetalle = DetalleTransaccion().apply {
            this.transaccion = transaccion
            this.cantidad = detalleDTO.cantidad
            this.precioUnitario = detalleDTO.precioUnitario
            this.subtotal = this.cantidad * this.precioUnitario
        }

        val detalleGuardado = detalleTransaccionRepository.save(nuevoDetalle)
        return detalleTransaccionMapper.detalleTransaccionToDetalleTransaccionDTO(detalleGuardado)
    }



}
