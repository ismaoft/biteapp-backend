package com.example.bite_api.mappers

import com.example.bite_api.ProductoDTO
import com.example.bite_api.entities.*
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
interface ProductoMapper {
    @Mapping(source = "categoria.categoriaId", target = "categoriaId")
    @Mapping(source = "restaurante.restaurantId", target = "restaurantId") // Corregido aquí
    fun productoToProductoDTO(producto: Producto): ProductoDTO

    @Mapping(source = "categoriaId", target = "categoria.categoriaId")
    @Mapping(source = "restaurantId", target = "restaurante.restaurantId") // Corregido aquí
    fun productoDTOToProducto(productoDTO: ProductoDTO): Producto
}

