package com.example.bite_api.services

import com.example.bite_api.ProductoDTO
import com.example.bite_api.entities.Producto
import com.example.bite_api.mappers.ProductoMapper
import com.example.bite_api.repositories.CategoriaRepository
import com.example.bite_api.repositories.ProductoRepository
import com.example.bite_api.repositories.RestauranteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductoService @Autowired constructor(
    private val productoRepository: ProductoRepository,
    private val productoMapper: ProductoMapper,
    private val categoriaRepository: CategoriaRepository,
    private val restauranteRepository: RestauranteRepository
) {

    fun findAll(): List<ProductoDTO> {
        val productos = productoRepository.findAll()
        return productos.map { productoMapper.productoToProductoDTO(it) }
    }

    fun findById(productoId: Int): ProductoDTO? {
        val producto: Optional<Producto> = productoRepository.findById(productoId)
        return if (producto.isPresent) productoMapper.productoToProductoDTO(producto.get()) else null
    }

    fun create(productoDTO: ProductoDTO): ProductoDTO {
        val producto = productoMapper.productoDTOToProducto(productoDTO)

        producto.categoria = categoriaRepository.findById(productoDTO.categoriaId ?: 0).orElse(null)
        producto.restaurante = restauranteRepository.findById(productoDTO.restaurantId ?: 0).orElse(null)

        val savedProducto = productoRepository.save(producto)
        return productoMapper.productoToProductoDTO(savedProducto)
    }

    fun update(productoId: Int, productoDTO: ProductoDTO): ProductoDTO? {
        if (productoRepository.existsById(productoId)) {
            val producto = productoMapper.productoDTOToProducto(productoDTO)

            producto.productoId = productoId
            producto.categoria = categoriaRepository.findById(productoDTO.categoriaId ?: 0).orElse(null)
            producto.restaurante = restauranteRepository.findById(productoDTO.restaurantId ?: 0).orElse(null)

            val updatedProducto = productoRepository.save(producto)
            return productoMapper.productoToProductoDTO(updatedProducto)
        }
        return null
    }

    fun delete(productoId: Int) {
        if (productoRepository.existsById(productoId)) {
            productoRepository.deleteById(productoId)
        }
    }
}
