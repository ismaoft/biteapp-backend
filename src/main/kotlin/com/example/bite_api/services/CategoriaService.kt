package com.example.bite_api.services

import com.example.bite_api.CategoriaDTO
import com.example.bite_api.entities.Categoria
import com.example.bite_api.mappers.CategoriaMapper
import com.example.bite_api.repositories.CategoriaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoriaService @Autowired constructor(
    private val categoriaRepository: CategoriaRepository,
    private val categoriaMapper: CategoriaMapper
) {

    fun findAll(): List<CategoriaDTO> {
        val categorias = categoriaRepository.findAll()
        return categorias.map { categoriaMapper.categoriaToCategoriaDTO(it) }
    }

    fun findById(categoriaId: Int): CategoriaDTO? {
        val categoria: Optional<Categoria> = categoriaRepository.findById(categoriaId)
        return if (categoria.isPresent) categoriaMapper.categoriaToCategoriaDTO(categoria.get()) else null
    }

    fun create(categoriaDTO: CategoriaDTO): CategoriaDTO {
        val categoria = categoriaMapper.categoriaDTOToCategoria(categoriaDTO)
        val savedCategoria = categoriaRepository.save(categoria)
        return categoriaMapper.categoriaToCategoriaDTO(savedCategoria)
    }

    fun update(categoriaId: Int, categoriaDTO: CategoriaDTO): CategoriaDTO? {
        if (categoriaRepository.existsById(categoriaId)) {
            val categoria = categoriaMapper.categoriaDTOToCategoria(categoriaDTO)
            categoria.categoriaId = categoriaId
            val updatedCategoria = categoriaRepository.save(categoria)
            return categoriaMapper.categoriaToCategoriaDTO(updatedCategoria)
        }
        return null
    }

    fun delete(categoriaId: Int) {
        if (categoriaRepository.existsById(categoriaId)) {
            categoriaRepository.deleteById(categoriaId)
        }
    }
}
