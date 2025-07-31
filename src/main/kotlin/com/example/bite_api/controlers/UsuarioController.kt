package com.example.bite_api.controllers

import com.example.bite_api.UsuarioDTO
import com.example.bite_api.entities.Usuario
import com.example.bite_api.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/usuarios")
class UsuarioController @Autowired constructor(
    private val usuarioService: UsuarioService
) {

    // Obtener todos los usuarios
    @GetMapping
    fun getAllUsuarios(): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = usuarioService.findAll()
        return ResponseEntity(usuarios, HttpStatus.OK)
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    fun getUsuarioById(@PathVariable("id") userId: Int): ResponseEntity<UsuarioDTO> {
        val usuario = usuarioService.findById(userId)
        return if (usuario != null) {
            ResponseEntity(usuario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Crear un nuevo usuario
    @PostMapping
    fun createUsuario(@RequestBody usuarioDTO: UsuarioDTO): ResponseEntity<UsuarioDTO> {
        val newUsuario = usuarioService.create(usuarioDTO)
        return ResponseEntity(newUsuario, HttpStatus.CREATED)
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    fun updateUsuario(@PathVariable("id") userId: Int, @RequestBody usuarioDTO: UsuarioDTO): ResponseEntity<UsuarioDTO> {
        val updatedUsuario = usuarioService.update(userId, usuarioDTO)
        return if (updatedUsuario != null) {
            ResponseEntity(updatedUsuario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    fun deleteUsuario(@PathVariable("id") userId: Int): ResponseEntity<Void> {
        usuarioService.delete(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }


    @PutMapping("/{id}/token")
    fun actualizarTokenFCM(@PathVariable id: Int, @RequestBody tokenRequest: TokenRequest): ResponseEntity<Usuario> {
        val usuarioActualizado = usuarioService.actualizarTokenFCM(id, tokenRequest.tokenFCM)
        return ResponseEntity.ok(usuarioActualizado)
    }

    data class TokenRequest(val tokenFCM: String)


}
