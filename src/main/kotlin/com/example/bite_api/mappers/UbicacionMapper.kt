package com.example.bite_api.mappers

import com.example.bite_api.UbicacionDTO
import com.example.bite_api.entities.Ubicacion
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UbicacionMapper {

    @Mapping(source = "entrega.entregaId", target = "entregaId") // Mapear el ID de la entrega
    fun ubicacionToUbicacionDTO(ubicacion: Ubicacion): UbicacionDTO

    @Mapping(source = "entregaId", target = "entrega.entregaId") // Mapear de vuelta al crear la entidad
    fun ubicacionDTOToUbicacion(ubicacionDTO: UbicacionDTO): Ubicacion
}
