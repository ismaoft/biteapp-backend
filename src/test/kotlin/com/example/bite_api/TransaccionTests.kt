package com.example.bite_api

import com.example.bite_api.entities.Transaccion
import com.example.bite_api.repositories.RestauranteRepository
import com.example.bite_api.repositories.TransaccionRepository
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
    scripts = [
        "/import-usuarios.sql",
        "/import-restaurantes.sql",
        "/import-transacciones.sql"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class TransaccionTests(
    @Autowired val transaccionRepository: TransaccionRepository
) {

    // Prueba 1: Consultar todas las transacciones
    @Test
    fun testFindAllTransacciones() {
        val transacciones: List<Transaccion> = transaccionRepository.findAll()
        println("Total de transacciones encontradas: ${transacciones.size}")
        Assertions.assertTrue(transacciones.isNotEmpty(), "La lista de transacciones no debería estar vacía")
    }

    // Prueba 2: Buscar una transacción por ID de usuario
    @Test
    fun testFindTransaccionByUsuario() {
        val usuarioId = 1
        val transacciones = transaccionRepository.findByUsuarioUserId(usuarioId)
        println("Transacciones para el usuario con ID $usuarioId: ${transacciones.size}")
        Assertions.assertTrue(transacciones.isNotEmpty(), "El usuario debería tener al menos una transacción")
    }

    // Prueba 3: Crear una nueva transacción
    @Test
    fun testCreateTransaccion(@Autowired usuarioRepository: UsuarioRepository, @Autowired restauranteRepository: RestauranteRepository) {
        val usuario = usuarioRepository.findById(1).orElseThrow { AssertionError("Usuario no encontrado") }
        val restaurante = restauranteRepository.findById(1).orElseThrow { AssertionError("Restaurante no encontrado") }

        val nuevaTransaccion = Transaccion().apply {
            fechaTransaccion = java.sql.Timestamp.valueOf("2024-10-01 12:00:00")
            estado = "Pendiente"
            total = 80.0
            this.usuario = usuario
            this.restaurante = restaurante
        }

        val transaccionGuardada = transaccionRepository.save(nuevaTransaccion)
        println("Transacción creada con éxito: (ID: ${transaccionGuardada.transaccionId})")
        Assertions.assertNotNull(transaccionGuardada.transaccionId, "El ID de la transacción no debería ser nulo después de guardarse")
    }



    // Prueba 4: Actualizar el estado de una transacción
    @Test
    fun testUpdateTransaccionEstado() {
        val transaccion = transaccionRepository.findById(1).orElse(null)
        Assertions.assertNotNull(transaccion)

        // Actualizamos el estado
        transaccion!!.estado = "Completado"
        transaccionRepository.save(transaccion)

        // Recuperamos nuevamente la transacción y verificamos el cambio
        val transaccionActualizada = transaccionRepository.findById(1).orElse(null)
        Assertions.assertNotNull(transaccionActualizada)
        Assertions.assertEquals("Completado", transaccionActualizada!!.estado)
    }

    // Prueba 5: Eliminar una transacción
    @Test
    fun testDeleteTransaccion() {
        // Recuperamos la transacción con ID 1 y la eliminamos
        val transaccion = transaccionRepository.findById(1).orElse(null)
        Assertions.assertNotNull(transaccion)

        transaccionRepository.delete(transaccion!!)

        val transaccionEliminada = transaccionRepository.findById(1)
        Assertions.assertTrue(transaccionEliminada.isEmpty)
    }
}
