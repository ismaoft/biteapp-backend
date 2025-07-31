package com.example.bite_api.mappers

import com.example.bite_api.ValoracionDTO
import com.example.bite_api.entities.Valoracion
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface ValoracionMapper {
    companion object {
        val INSTANCE: ValoracionMapper = Mappers.getMapper(ValoracionMapper::class.java)
    }

    fun valoracionToValoracionDTO(valoracion: Valoracion): ValoracionDTO
    fun valoracionDTOToValoracion(valoracionDTO: ValoracionDTO): Valoracion
}
