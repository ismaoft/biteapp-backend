package com.example.bite_api.mappers

import com.example.bite_api.CategoriaDTO
import com.example.bite_api.entities.Categoria
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface CategoriaMapper {
    companion object {
        val INSTANCE: CategoriaMapper = Mappers.getMapper(CategoriaMapper::class.java)
    }

    fun categoriaToCategoriaDTO(categoria: Categoria): CategoriaDTO
    fun categoriaDTOToCategoria(categoriaDTO: CategoriaDTO): Categoria
}
