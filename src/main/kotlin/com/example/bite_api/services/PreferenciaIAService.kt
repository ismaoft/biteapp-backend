package com.example.bite_api.services

import com.example.bite_api.PreferenciaIADTO
import com.example.bite_api.entities.PreferenciaIA
import com.example.bite_api.mappers.PreferenciaIAMapper
import com.example.bite_api.repositories.PreferenciaIARepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PreferenciaIAService @Autowired constructor(
    private val preferenciaIARepository: PreferenciaIARepository,
    private val preferenciaIAMapper: PreferenciaIAMapper
) {

    fun findAll(): List<PreferenciaIADTO> {
        val preferencias = preferenciaIARepository.findAll()
        return preferencias.map { preferenciaIAMapper.preferenciaIAToPreferenciaIADTO(it) }
    }

    fun findById(preferenciaId: Int): PreferenciaIADTO? {
        val preferencia: Optional<PreferenciaIA> = preferenciaIARepository.findById(preferenciaId)
        return if (preferencia.isPresent) preferenciaIAMapper.preferenciaIAToPreferenciaIADTO(preferencia.get()) else null
    }

    fun create(preferenciaIADTO: PreferenciaIADTO): PreferenciaIADTO {
        val preferencia = preferenciaIAMapper.preferenciaIADTOToPreferenciaIA(preferenciaIADTO)
        val savedPreferencia = preferenciaIARepository.save(preferencia)
        return preferenciaIAMapper.preferenciaIAToPreferenciaIADTO(savedPreferencia)
    }

    fun update(preferenciaId: Int, preferenciaIADTO: PreferenciaIADTO): PreferenciaIADTO? {
        if (preferenciaIARepository.existsById(preferenciaId)) {
            val preferencia = preferenciaIAMapper.preferenciaIADTOToPreferenciaIA(preferenciaIADTO)
            preferencia.preferenciaId = preferenciaId
            val updatedPreferencia = preferenciaIARepository.save(preferencia)
            return preferenciaIAMapper.preferenciaIAToPreferenciaIADTO(updatedPreferencia)
        }
        return null
    }

    fun delete(preferenciaId: Int) {
        if (preferenciaIARepository.existsById(preferenciaId)) {
            preferenciaIARepository.deleteById(preferenciaId)
        }
    }
}
