package com.example.bite_api

import com.example.bite_api.entities.Usuario
import com.example.bite_api.repositories.UsuarioRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
    statements = [
        "DELETE FROM public.valoraciones",
        "DELETE FROM public.detalles_transaccion",
        "DELETE FROM public.transacciones",
        "DELETE FROM public.productos",
        "DELETE FROM public.categorias",
        "DELETE FROM public.restaurantes",
        "DELETE FROM public.preferencias_ia",
        "DELETE FROM public.role_privilege",
        "DELETE FROM public.user_role",
        "DELETE FROM public.privilege",
        "DELETE FROM public.role",
        "DELETE FROM public.usuarios"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-usuarios.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class UsuarioTests(
    @Autowired
    val usuarioRepository: UsuarioRepository
) {

    // Prueba 1: Consultar todos los usuarios
    @Test
    fun testFindAllUsuarios() {
        val usuarios: List<Usuario> = usuarioRepository.findAll()
        println("Total de usuarios encontrados: ${usuarios.size}")
        usuarios.forEach { println("Usuario: ${it.nombre}, Email: ${it.email}") }
        Assertions.assertTrue(usuarios.isNotEmpty(), "La lista de usuarios no debería estar vacía")
    }

    // Prueba 2: Buscar un usuario por ID
    @Test
    fun testFindUsuarioById() {
        val usuarioId = 1
        val usuario = usuarioRepository.findById(usuarioId)
        if (usuario.isPresent) {
            println("Usuario encontrado: ${usuario.get().nombre} con Email: ${usuario.get().email}")
            Assertions.assertEquals(usuarioId, usuario.get().userId, "El ID del usuario debería coincidir")
        } else {
            Assertions.fail("Usuario con ID $usuarioId no encontrado")
        }
    }

    // Prueba 3: Crear un nuevo usuario
    @Test
    fun testCreateUsuario() {
        val nuevoUsuario = Usuario()
        nuevoUsuario.nombre = "Nuevo Usuario"
        nuevoUsuario.email = "nuevo.usuario@example.com"
        nuevoUsuario.password = "password123"
        nuevoUsuario.direccion = "Dirección de Prueba"
        nuevoUsuario.telefono = "1234567890"
        nuevoUsuario.preferencias = "Vegetariano"
        nuevoUsuario.alergias = "Ninguna"


        val usuarioGuardado = usuarioRepository.save(nuevoUsuario)
        println("Usuario creado con éxito: ${usuarioGuardado.nombre} (ID: ${usuarioGuardado.userId})")

        Assertions.assertNotNull(usuarioGuardado.userId, "El ID del usuario no debería ser nulo después de guardarse")
        Assertions.assertEquals("nuevo.usuario@example.com", usuarioGuardado.email, "El email debería coincidir")
    }

    // Prueba para actualizar el correo electrónico de un usuario
    @Test
    fun testUpdateUsuarioEmail() {
        val usuario = usuarioRepository.findById(1).orElse(null)
        Assertions.assertNotNull(usuario)

        val nuevoEmail = "actualizado.email@test.com"
        usuario.email = nuevoEmail
        usuarioRepository.save(usuario)

        val usuarioActualizado = usuarioRepository.findById(1).orElse(null)
        Assertions.assertNotNull(usuarioActualizado)
        Assertions.assertEquals(nuevoEmail, usuarioActualizado.email)
    }

    // Prueba para eliminar un usuario específico
    @Test
    fun testDeleteUsuario() {
        val usuario = usuarioRepository.findById(1).orElse(null)
        Assertions.assertNotNull(usuario)

        usuarioRepository.delete(usuario)

        val usuarioEliminado = usuarioRepository.findById(1)
        Assertions.assertTrue(usuarioEliminado.isEmpty)
    }

}
