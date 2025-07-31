package com.example.bite_api.controllers

import com.example.bite_api.DetalleTransaccionDTO
import com.example.bite_api.services.DetalleTransaccionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/detalles-transaccion")
class DetalleTransaccionController @Autowired constructor(
    private val detalleTransaccionService: DetalleTransaccionService
) {

    // Obtener todos los detalles de transacción
    @GetMapping
    fun getAllDetalles(): ResponseEntity<List<DetalleTransaccionDTO>> {
        val detalles = detalleTransaccionService.findAll()
        return ResponseEntity(detalles, HttpStatus.OK)
    }

    // Obtener un detalle de transacción por ID
    @GetMapping("/{id}")
    fun getDetalleById(@PathVariable("id") detalleId: Int): ResponseEntity<DetalleTransaccionDTO> {
        val detalle = detalleTransaccionService.findById(detalleId)
        return if (detalle != null) {
            ResponseEntity(detalle, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear un nuevo detalle de transacción
    @PostMapping
    fun createDetalle(@RequestBody detalleTransaccionDTO: DetalleTransaccionDTO): ResponseEntity<DetalleTransaccionDTO> {
        val newDetalle = detalleTransaccionService.create(detalleTransaccionDTO)
        return ResponseEntity(newDetalle, HttpStatus.CREATED)
    }

    // Actualizar un detalle de transacción existente
    @PutMapping("/{id}")
    fun updateDetalle(@PathVariable("id") detalleId: Int, @RequestBody detalleTransaccionDTO: DetalleTransaccionDTO): ResponseEntity<DetalleTransaccionDTO> {
        val updatedDetalle = detalleTransaccionService.update(detalleId, detalleTransaccionDTO)
        return if (updatedDetalle != null) {
            ResponseEntity(updatedDetalle, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar un detalle de transacción
    @DeleteMapping("/{id}")
    fun deleteDetalle(@PathVariable("id") detalleId: Int): ResponseEntity<Void> {
        detalleTransaccionService.delete(detalleId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }


    @PostMapping("/carrito/detalles")
    fun agregarOActualizarDetalle(@RequestBody detalleDTO: DetalleTransaccionDTO): ResponseEntity<DetalleTransaccionDTO> {
        val detalleActualizado = detalleTransaccionService.agregarOActualizarDetalle(detalleDTO)
        return ResponseEntity(detalleActualizado, HttpStatus.OK)
    }


    @GetMapping("/transaccion/{transaccionId}")
    fun getDetallesByTransaccionId(@PathVariable transaccionId: Int): ResponseEntity<List<DetalleTransaccionDTO>> {
        val detalles = detalleTransaccionService.findDetallesByTransaccionId(transaccionId)
        return ResponseEntity.ok(detalles)
    }

}
