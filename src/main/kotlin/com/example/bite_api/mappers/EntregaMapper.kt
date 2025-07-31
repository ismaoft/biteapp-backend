package com.example.bite_api.mappers

import com.example.bite_api.EntregaDTO
import com.example.bite_api.entities.Entrega
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface EntregaMapper {
    @Mapping(source = "transaccion.transaccionId", target = "transaccionId")
    @Mapping(source = "repartidor.userId", target = "repartidorId")
    fun entregaToEntregaDTO(entrega: Entrega): EntregaDTO

    @Mapping(source = "transaccionId", target = "transaccion.transaccionId")
    @Mapping(source = "repartidorId", target = "repartidor.userId")
    fun entregaDTOToEntrega(entregaDTO: EntregaDTO): Entrega
}

