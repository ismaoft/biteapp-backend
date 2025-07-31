package com.example.bite_api.controllers

import com.example.bite_api.CategoriaDTO
import com.example.bite_api.services.CategoriaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/categorias")
class CategoriaController @Autowired constructor(
    private val categoriaService: CategoriaService
) {

    // Obtener todas las categorías
    @GetMapping
    fun getAllCategorias(): ResponseEntity<List<CategoriaDTO>> {
        val categorias = categoriaService.findAll()
        return ResponseEntity(categorias, HttpStatus.OK)
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    fun getCategoriaById(@PathVariable("id") categoriaId: Int): ResponseEntity<CategoriaDTO> {
        val categoria = categoriaService.findById(categoriaId)
        return if (categoria != null) {
            ResponseEntity(categoria, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear una nueva categoría
    @PostMapping
    fun createCategoria(@RequestBody categoriaDTO: CategoriaDTO): ResponseEntity<CategoriaDTO> {
        val newCategoria = categoriaService.create(categoriaDTO)
        return ResponseEntity(newCategoria, HttpStatus.CREATED)
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    fun updateCategoria(@PathVariable("id") categoriaId: Int, @RequestBody categoriaDTO: CategoriaDTO): ResponseEntity<CategoriaDTO> {
        val updatedCategoria = categoriaService.update(categoriaId, categoriaDTO)
        return if (updatedCategoria != null) {
            ResponseEntity(updatedCategoria, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    fun deleteCategoria(@PathVariable("id") categoriaId: Int): ResponseEntity<Void> {
        categoriaService.delete(categoriaId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
