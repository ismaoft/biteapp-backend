package com.example.bite_api.controllers

import com.example.bite_api.RestauranteDTO
import com.example.bite_api.services.RestauranteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/restaurantes")
class RestauranteController @Autowired constructor(
    private val restauranteService: RestauranteService
) {

    // Obtener todos los restaurantes
    @GetMapping
    fun getAllRestaurantes(): ResponseEntity<List<RestauranteDTO>> {
        val restaurantes = restauranteService.findAll()
        return ResponseEntity(restaurantes, HttpStatus.OK)
    }

    // Obtener un restaurante por ID
    @GetMapping("/{id}")
    fun getRestauranteById(@PathVariable("id") restaurantId: Int): ResponseEntity<RestauranteDTO> {
        val restaurante = restauranteService.findById(restaurantId)
        return if (restaurante != null) {
            ResponseEntity(restaurante, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear un nuevo restaurante
    @PostMapping
    fun createRestaurante(@RequestBody restauranteDTO: RestauranteDTO): ResponseEntity<RestauranteDTO> {
        val newRestaurante = restauranteService.create(restauranteDTO)
        return ResponseEntity(newRestaurante, HttpStatus.CREATED)
    }

    // Actualizar un restaurante existente
    @PutMapping("/{id}")
    fun updateRestaurante(@PathVariable("id") restaurantId: Int, @RequestBody restauranteDTO: RestauranteDTO): ResponseEntity<RestauranteDTO> {
        val updatedRestaurante = restauranteService.update(restaurantId, restauranteDTO)
        return if (updatedRestaurante != null) {
            ResponseEntity(updatedRestaurante, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar un restaurante
    @DeleteMapping("/{id}")
    fun deleteRestaurante(@PathVariable("id") restaurantId: Int): ResponseEntity<Void> {
        restauranteService.delete(restaurantId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    // Obtener restaurantes por categoría
    @GetMapping("/categoria/{categoria}")
    fun getRestaurantesByCategoria(@PathVariable("categoria") categoria: String): ResponseEntity<List<RestauranteDTO>> {
        val restaurantes = restauranteService.findByCategoria(categoria)
        return ResponseEntity(restaurantes, HttpStatus.OK)
    }
}
