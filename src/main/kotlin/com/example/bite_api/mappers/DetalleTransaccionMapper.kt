package com.example.bite_api.mappers

import com.example.bite_api.DetalleTransaccionDTO
import com.example.bite_api.entities.DetalleTransaccion
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
interface DetalleTransaccionMapper {
    @Mapping(source = "transaccion.transaccionId", target = "transaccionId")
    @Mapping(source = "producto.productoId", target = "productoId")
    fun detalleTransaccionToDetalleTransaccionDTO(detalle: DetalleTransaccion): DetalleTransaccionDTO

    @Mapping(source = "transaccionId", target = "transaccion.transaccionId")
    @Mapping(source = "productoId", target = "producto.productoId")
    fun detalleTransaccionDTOToDetalleTransaccion(detalleDTO: DetalleTransaccionDTO): DetalleTransaccion
}

