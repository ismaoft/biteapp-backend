package com.example.bite_api.controllers

import com.example.bite_api.UbicacionDTO
import com.example.bite_api.services.UbicacionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/ubicaciones")
class UbicacionController @Autowired constructor(
    private val ubicacionService: UbicacionService
) {

    // Agregar una ubicación para una entrega
    @PostMapping
    fun agregarUbicacion(@RequestBody ubicacionRequest: UbicacionRequest): ResponseEntity<UbicacionDTO> {
        val nuevaUbicacion = ubicacionService.agregarUbicacion(
            ubicacionRequest.entregaId,
            ubicacionRequest.latitud,
            ubicacionRequest.longitud
        )
        return ResponseEntity(nuevaUbicacion, HttpStatus.CREATED)
    }

    // Obtener las ubicaciones recientes de una entrega
    @GetMapping("/{entregaId}/recientes")
    fun obtenerUbicacionesRecientes(@PathVariable entregaId: Int): ResponseEntity<List<UbicacionDTO>> {
        val ubicaciones = ubicacionService.obtenerUbicacionesRecientes(entregaId)
        return ResponseEntity(ubicaciones, HttpStatus.OK)
    }
}
data class UbicacionRequest(
    val entregaId: Int,
    val latitud: Double,
    val longitud: Double
)