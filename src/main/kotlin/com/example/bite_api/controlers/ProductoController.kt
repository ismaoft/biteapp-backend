package com.example.bite_api.controlers

import com.example.bite_api.ProductoDTO
import com.example.bite_api.services.ProductoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/productos")
class ProductoController @Autowired constructor(
    private val productoService: ProductoService
) {

    // Obtener todos los productos
    @GetMapping
    @PreAuthorize("isAuthenticated()") // Permitir a cualquier usuario autenticado
    fun getAllProductos(): ResponseEntity<List<ProductoDTO>> {
        val productos = productoService.findAll()
        return ResponseEntity(productos, HttpStatus.OK)
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    fun getProductoById(@PathVariable("id") productoId: Int): ResponseEntity<ProductoDTO> {
        val producto = productoService.findById(productoId)
        return if (producto != null) {
            ResponseEntity(producto, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear un nuevo producto
    @PostMapping
    fun createProducto(@RequestBody productoDTO: ProductoDTO): ResponseEntity<ProductoDTO> {
        val newProducto = productoService.create(productoDTO)
        return ResponseEntity(newProducto, HttpStatus.CREATED)
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    fun updateProducto(@PathVariable("id") productoId: Int, @RequestBody productoDTO: ProductoDTO): ResponseEntity<ProductoDTO> {
        val updatedProducto = productoService.update(productoId, productoDTO)
        return if (updatedProducto != null) {
            ResponseEntity(updatedProducto, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    fun deleteProducto(@PathVariable("id") productoId: Int): ResponseEntity<Void> {
        productoService.delete(productoId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
