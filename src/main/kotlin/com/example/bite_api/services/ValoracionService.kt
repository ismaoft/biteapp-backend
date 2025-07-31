package com.example.bite_api.services

import com.example.bite_api.ValoracionDTO
import com.example.bite_api.entities.Valoracion
import com.example.bite_api.mappers.ValoracionMapper
import com.example.bite_api.repositories.ValoracionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ValoracionService @Autowired constructor(
    private val valoracionRepository: ValoracionRepository,
    private val valoracionMapper: ValoracionMapper
) {

    fun findAll(): List<ValoracionDTO> {
        val valoraciones = valoracionRepository.findAll()
        return valoraciones.map { valoracionMapper.valoracionToValoracionDTO(it) }
    }

    fun findById(valoracionId: Int): ValoracionDTO? {
        val valoracion: Optional<Valoracion> = valoracionRepository.findById(valoracionId)
        return if (valoracion.isPresent) valoracionMapper.valoracionToValoracionDTO(valoracion.get()) else null
    }

    fun create(valoracionDTO: ValoracionDTO): ValoracionDTO {
        val valoracion = valoracionMapper.valoracionDTOToValoracion(valoracionDTO)
        val savedValoracion = valoracionRepository.save(valoracion)
        return valoracionMapper.valoracionToValoracionDTO(savedValoracion)
    }

    fun update(valoracionId: Int, valoracionDTO: ValoracionDTO): ValoracionDTO? {
        if (valoracionRepository.existsById(valoracionId)) {
            val valoracion = valoracionMapper.valoracionDTOToValoracion(valoracionDTO)
            valoracion.valoracionId = valoracionId
            val updatedValoracion = valoracionRepository.save(valoracion)
            return valoracionMapper.valoracionToValoracionDTO(updatedValoracion)
        }
        return null
    }

    fun delete(valoracionId: Int) {
        if (valoracionRepository.existsById(valoracionId)) {
            valoracionRepository.deleteById(valoracionId)
        }
    }
}
