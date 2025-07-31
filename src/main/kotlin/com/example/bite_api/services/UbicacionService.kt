package com.example.bite_api.services

import com.example.bite_api.UbicacionDTO
import com.example.bite_api.entities.Ubicacion
import com.example.bite_api.mappers.UbicacionMapper
import com.example.bite_api.repositories.EntregaRepository
import com.example.bite_api.repositories.UbicacionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class UbicacionService @Autowired constructor(
    private val ubicacionRepository: UbicacionRepository,
    private val entregaRepository: EntregaRepository,
    private val ubicacionMapper: UbicacionMapper
) {

    // Crear una nueva ubicación para una entrega
    fun agregarUbicacion(entregaId: Int, latitud: Double, longitud: Double): UbicacionDTO {
        val entrega = entregaRepository.findById(entregaId)
            .orElseThrow { Exception("Entrega no encontrada") }

        val nuevaUbicacion = Ubicacion().apply {
            this.entrega = entrega
            this.latitud = latitud
            this.longitud = longitud
            this.timestamp = Timestamp(System.currentTimeMillis())
        }

        val ubicacionGuardada = ubicacionRepository.save(nuevaUbicacion)
        return ubicacionMapper.ubicacionToUbicacionDTO(ubicacionGuardada)
    }

    // Obtener las ubicaciones más recientes de una entrega
    fun obtenerUbicacionesRecientes(entregaId: Int): List<UbicacionDTO> {
        val ubicaciones = ubicacionRepository.findByEntregaEntregaIdOrderByTimestampDesc(entregaId)
        return ubicaciones.map { ubicacionMapper.ubicacionToUbicacionDTO(it) }
    }
}
