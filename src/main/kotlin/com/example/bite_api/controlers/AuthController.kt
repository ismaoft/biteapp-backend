package com.example.bite_api.controller

import com.example.bite_api.UsuarioDTO
import com.example.bite_api.security.JwtUtils
import com.example.bite_api.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class LoginRequest(val nombre: String, val password: String,val tokenFCM: String)

@RestController
class AuthController @Autowired constructor(
    private val usuarioService: UsuarioService
) {

    @PostMapping("/api/v1/login")
    fun login(@RequestBody request: LoginRequest): Map<String, String> {
        val usuario = usuarioService.authenticate(request.nombre, request.password)
        return if (usuario != null) {
            val token = JwtUtils.generateToken(usuario.nombre, usuario.roleName, usuario.userId!!)
            usuarioService.actualizarTokenFCM(usuario.userId!!, request.tokenFCM)
            println("Token FCM recibido y actualizado: ${request.tokenFCM}")
            mapOf("token" to token)
        } else {
            throw RuntimeException("Credenciales inválidas")
        }
    }



    @GetMapping("/api/v1/user/profile")
    fun getProfile(authentication: Authentication): ResponseEntity<UsuarioDTO> {
        val usuario = usuarioService.findByNombre(authentication.name)
            ?: throw RuntimeException("Usuario no encontrado")
        return ResponseEntity.ok(usuario)
    }
}
