package com.example.bite_api

import com.example.bite_api.entities.Producto
import com.example.bite_api.entities.Usuario
import com.example.bite_api.entities.Valoracion
import com.example.bite_api.repositories.ProductoRepository
import com.example.bite_api.repositories.UsuarioRepository
import com.example.bite_api.repositories.ValoracionRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import java.sql.Timestamp

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
    statements = [
        "DELETE FROM public.detalles_transaccion",
        "DELETE FROM public.valoraciones",
        "DELETE FROM public.transacciones",
        "DELETE FROM public.productos",
        "DELETE FROM public.usuarios"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-valoraciones.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class ValoracionTests(
    @Autowired val valoracionRepository: ValoracionRepository,
    @Autowired val usuarioRepository: UsuarioRepository,
    @Autowired val productoRepository: ProductoRepository
) {

    // Prueba 1: Consultar todas las valoraciones
    @Test
    fun testFindAllValoraciones() {
        val valoraciones: List<Valoracion> = valoracionRepository.findAll()
        println("Total de valoraciones encontradas: ${valoraciones.size}")
        assertTrue(valoraciones.isNotEmpty(), "La lista de valoraciones no debería estar vacía")
    }

    // Prueba 2: Buscar una valoración por ID
    @Test
    fun testFindValoracionById() {
        val valoracionId = 1
        val valoracion = valoracionRepository.findById(valoracionId).orElse(null)
        println("Valoración con ID $valoracionId: ${valoracion?.calificacion}")
        assertNotNull(valoracion, "La valoración con ID $valoracionId debería existir")
    }

    // Prueba 3: Crear una nueva valoración
    @Test
    fun testCreateValoracion() {
        val usuario = usuarioRepository.findById(1).orElseThrow { AssertionError("Usuario no encontrado") }
        val producto = productoRepository.findById(1).orElseThrow { AssertionError("Producto no encontrado") }

        val nuevaValoracion = Valoracion().apply {
            calificacion = 5
            comentario = "Excelente calidad y servicio"
            fechaResena = Timestamp.valueOf("2024-10-02 12:00:00")
            this.usuario = usuario
            this.producto = producto
        }

        val valoracionGuardada = valoracionRepository.save(nuevaValoracion)
        println("Valoración creada con éxito: (ID: ${valoracionGuardada.valoracionId})")
        assertNotNull(valoracionGuardada.valoracionId, "El ID de la valoración no debería ser nulo después de guardarse")
    }

    // Prueba 4: Actualizar una valoración existente
    @Test
    fun testUpdateValoracionComentario() {
        val valoracion = valoracionRepository.findById(1).orElse(null)
        assertNotNull(valoracion)

        valoracion!!.comentario = "Servicio mejorado, excelente atención"
        valoracionRepository.save(valoracion)

        val valoracionActualizada = valoracionRepository.findById(1).orElse(null)
        assertNotNull(valoracionActualizada)
        assertEquals("Servicio mejorado, excelente atención", valoracionActualizada!!.comentario)
    }

    // Prueba 5: Eliminar una valoración
    @Test
    fun testDeleteValoracion() {
        // Recuperamos la valoración con ID 1 y la eliminamos
        val valoracion = valoracionRepository.findById(1).orElse(null)
        assertNotNull(valoracion)

        valoracionRepository.delete(valoracion!!)

        val valoracionEliminada = valoracionRepository.findById(1)
        assertTrue(valoracionEliminada.isEmpty)
    }
}
