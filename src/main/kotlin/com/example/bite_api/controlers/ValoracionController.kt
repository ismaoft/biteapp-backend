package com.example.bite_api.controllers

import com.example.bite_api.ValoracionDTO
import com.example.bite_api.services.ValoracionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/valoraciones")
class ValoracionController @Autowired constructor(
    private val valoracionService: ValoracionService
) {

    // Obtener todas las valoraciones
    @GetMapping
    fun getAllValoraciones(): ResponseEntity<List<ValoracionDTO>> {
        val valoraciones = valoracionService.findAll()
        return ResponseEntity(valoraciones, HttpStatus.OK)
    }

    // Obtener una valoración por ID
    @GetMapping("/{id}")
    fun getValoracionById(@PathVariable("id") valoracionId: Int): ResponseEntity<ValoracionDTO> {
        val valoracion = valoracionService.findById(valoracionId)
        return if (valoracion != null) {
            ResponseEntity(valoracion, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear una nueva valoración
    @PostMapping
    fun createValoracion(@RequestBody valoracionDTO: ValoracionDTO): ResponseEntity<ValoracionDTO> {
        val newValoracion = valoracionService.create(valoracionDTO)
        return ResponseEntity(newValoracion, HttpStatus.CREATED)
    }

    // Actualizar una valoración existente
    @PutMapping("/{id}")
    fun updateValoracion(@PathVariable("id") valoracionId: Int, @RequestBody valoracionDTO: ValoracionDTO): ResponseEntity<ValoracionDTO> {
        val updatedValoracion = valoracionService.update(valoracionId, valoracionDTO)
        return if (updatedValoracion != null) {
            ResponseEntity(updatedValoracion, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar una valoración
    @DeleteMapping("/{id}")
    fun deleteValoracion(@PathVariable("id") valoracionId: Int): ResponseEntity<Void> {
        valoracionService.delete(valoracionId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
