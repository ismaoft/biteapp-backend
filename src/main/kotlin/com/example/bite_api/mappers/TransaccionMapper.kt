package com.example.bite_api.mappers

import com.example.bite_api.TransaccionDTO
import com.example.bite_api.entities.*
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", uses = [UsuarioMapper::class, RestauranteMapper::class])
interface TransaccionMapper {
    @Mapping(source = "usuario.userId", target = "userId")
    @Mapping(source = "restaurante.restaurantId", target = "restaurantId")
    fun transaccionToTransaccionDTO(transaccion: Transaccion): TransaccionDTO

    @Mapping(source = "userId", target = "usuario.userId")
    @Mapping(source = "restaurantId", target = "restaurante.restaurantId")
    fun transaccionDTOToTransaccion(transaccionDTO: TransaccionDTO): Transaccion
}
