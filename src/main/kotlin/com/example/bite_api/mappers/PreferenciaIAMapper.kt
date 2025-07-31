package com.example.bite_api.mappers

import com.example.bite_api.PreferenciaIADTO
import com.example.bite_api.entities.PreferenciaIA
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface PreferenciaIAMapper {
    companion object {
        val INSTANCE: PreferenciaIAMapper = Mappers.getMapper(PreferenciaIAMapper::class.java)
    }

    fun preferenciaIAToPreferenciaIADTO(preferenciaIA: PreferenciaIA): PreferenciaIADTO
    fun preferenciaIADTOToPreferenciaIA(preferenciaIADTO: PreferenciaIADTO): PreferenciaIA
}
