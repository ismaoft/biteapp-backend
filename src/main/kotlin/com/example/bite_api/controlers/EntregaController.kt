package com.example.bite_api.controllers

import com.example.bite_api.EntregaDTO
import com.example.bite_api.services.EntregaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/entregas")
class EntregaController @Autowired constructor(
    private val entregaService: EntregaService
) {

    // Crear una nueva entrega
    @PostMapping
    fun crearEntrega(@RequestParam transaccionId: Int): ResponseEntity<EntregaDTO> {
        val nuevaEntrega = entregaService.crearEntrega(transaccionId)
        return ResponseEntity(nuevaEntrega, HttpStatus.CREATED)
    }

    // Actualizar estado de una entrega
    @PutMapping("/{id}/estado")
    fun actualizarEstadoEntrega(
        @PathVariable id: Int,
        @RequestParam estado: String
    ): ResponseEntity<EntregaDTO> {
        val entregaActualizada = entregaService.actualizarEstadoEntrega(id, estado)
        return ResponseEntity.ok(entregaActualizada)
    }

    // Obtener una entrega por ID
    @GetMapping("/{id}")
    fun obtenerEntregaPorId(@PathVariable id: Int): ResponseEntity<EntregaDTO> {
        val entrega = entregaService.obtenerEntregaPorId(id)
        return if (entrega != null) {
            ResponseEntity(entrega, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Obtener todas las entregas (opcional)
    @GetMapping
    fun obtenerTodasLasEntregas(): ResponseEntity<List<EntregaDTO>> {
        val entregas = entregaService.obtenerTodasLasEntregas()
        return ResponseEntity(entregas, HttpStatus.OK)
    }

    @GetMapping("/{id}/distancia")
    fun calcularDistanciaEntrega(@PathVariable id: Int): ResponseEntity<Double> {
        val distancia = entregaService.calcularDistanciaEntrega(id)
        return ResponseEntity.ok(distancia)
    }

}
