package com.example.bite_api.mappers

import com.example.bite_api.RestauranteDTO
import com.example.bite_api.entities.*
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
@Component
interface RestauranteMapper {
    companion object {
        val INSTANCE: RestauranteMapper = Mappers.getMapper(RestauranteMapper::class.java)
    }

    @Mapping(source = "administrador.userId", target = "adminId")
    fun restauranteToRestauranteDTO(restaurante: Restaurante): RestauranteDTO

    @Mapping(source = "adminId", target = "administrador.userId")
    fun restauranteDTOToRestaurante(restauranteDTO: RestauranteDTO): Restaurante
}
