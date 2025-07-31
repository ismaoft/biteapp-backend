package com.example.bite_api.controllers

import com.example.bite_api.PreferenciaIADTO
import com.example.bite_api.services.PreferenciaIAService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/preferencias-ia")
class PreferenciaIAController @Autowired constructor(
    private val preferenciaIAService: PreferenciaIAService
) {

    // Obtener todas las preferencias IA
    @GetMapping
    fun getAllPreferencias(): ResponseEntity<List<PreferenciaIADTO>> {
        val preferencias = preferenciaIAService.findAll()
        return ResponseEntity(preferencias, HttpStatus.OK)
    }

    // Obtener una preferencia IA por ID
    @GetMapping("/{id}")
    fun getPreferenciaById(@PathVariable("id") preferenciaId: Int): ResponseEntity<PreferenciaIADTO> {
        val preferencia = preferenciaIAService.findById(preferenciaId)
        return if (preferencia != null) {
            ResponseEntity(preferencia, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear una nueva preferencia IA
    @PostMapping
    fun createPreferencia(@RequestBody preferenciaIADTO: PreferenciaIADTO): ResponseEntity<PreferenciaIADTO> {
        val newPreferencia = preferenciaIAService.create(preferenciaIADTO)
        return ResponseEntity(newPreferencia, HttpStatus.CREATED)
    }

    // Actualizar una preferencia IA existente
    @PutMapping("/{id}")
    fun updatePreferencia(@PathVariable("id") preferenciaId: Int, @RequestBody preferenciaIADTO: PreferenciaIADTO): ResponseEntity<PreferenciaIADTO> {
        val updatedPreferencia = preferenciaIAService.update(preferenciaId, preferenciaIADTO)
        return if (updatedPreferencia != null) {
            ResponseEntity(updatedPreferencia, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar una preferencia IA
    @DeleteMapping("/{id}")
    fun deletePreferencia(@PathVariable("id") preferenciaId: Int): ResponseEntity<Void> {
        preferenciaIAService.delete(preferenciaId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
