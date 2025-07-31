package com.example.bite_api.controllers

import com.example.bite_api.TransaccionDTO
import com.example.bite_api.services.TransaccionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transacciones")
class TransaccionController @Autowired constructor(
    private val transaccionService: TransaccionService
) {

    // Obtener todas las transacciones
    @GetMapping
    fun getAllTransacciones(): ResponseEntity<List<TransaccionDTO>> {
        val transacciones = transaccionService.findAll()
        return ResponseEntity(transacciones, HttpStatus.OK)
    }

    // Obtener una transacción por ID
    @GetMapping("/{id}")
    fun getTransaccionById(@PathVariable("id") transaccionId: Int): ResponseEntity<TransaccionDTO> {
        val transaccion = transaccionService.findById(transaccionId)
        return if (transaccion != null) {
            ResponseEntity(transaccion, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear una nueva transacción
    @PostMapping
    fun createTransaccion(@RequestBody transaccionDTO: TransaccionDTO): ResponseEntity<TransaccionDTO> {
        val newTransaccion = transaccionService.create(transaccionDTO)
        return ResponseEntity(newTransaccion, HttpStatus.CREATED)
    }

    // Actualizar una transacción existente
    @PutMapping("/{id}")
    fun updateTransaccion(@PathVariable("id") transaccionId: Int, @RequestBody transaccionDTO: TransaccionDTO): ResponseEntity<TransaccionDTO> {
        val updatedTransaccion = transaccionService.update(transaccionId, transaccionDTO)
        return if (updatedTransaccion != null) {
            ResponseEntity(updatedTransaccion, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar una transacción
    @DeleteMapping("/{id}")
    fun deleteTransaccion(@PathVariable("id") transaccionId: Int): ResponseEntity<Void> {
        transaccionService.delete(transaccionId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }


    @PostMapping("/carrito")
    fun obtenerOCrearCarrito(@RequestParam("userId") userId: Int): ResponseEntity<TransaccionDTO> {
        val carrito = transaccionService.obtenerOCrearCarrito(userId)
        return ResponseEntity(carrito, HttpStatus.OK)
    }

    @PutMapping("/carrito/confirmar/{id}")
    fun confirmarCarrito(@PathVariable("id") transaccionId: Int): ResponseEntity<TransaccionDTO> {
        val carritoConfirmado = transaccionService.confirmarCarrito(transaccionId)
        return ResponseEntity(carritoConfirmado, HttpStatus.OK)
    }

    @GetMapping("/carrito")
    fun obtenerCarrito(@RequestParam("userId") userId: Int): ResponseEntity<TransaccionDTO> {
        val carrito = transaccionService.obtenerCarritoActivo(userId)
        return ResponseEntity(carrito, HttpStatus.OK)
    }



}
