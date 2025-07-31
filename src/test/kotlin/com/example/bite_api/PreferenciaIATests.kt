package com.example.bite_api

import com.example.bite_api.entities.PreferenciaIA
import com.example.bite_api.entities.Usuario
import com.example.bite_api.repositories.PreferenciaIARepository
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
        "DELETE FROM public.role_privilege",
        "DELETE FROM public.user_role",
        "DELETE FROM public.valoraciones",
        "DELETE FROM public.preferencias_ia",
        "DELETE FROM public.detalles_transaccion",
        "DELETE FROM public.transacciones",
        "DELETE FROM public.productos",
        "DELETE FROM public.categorias",
        "DELETE FROM public.usuarios",
        "DELETE FROM public.restaurantes",
        "DELETE FROM public.privilege",
        "DELETE FROM public.role"
    ]
,
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = [
        "/import-usuarios.sql",
        "/import-preferenciasIA.sql"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class PreferenciaIATests(
    @Autowired val preferenciaIARepository: PreferenciaIARepository,
    @Autowired val usuarioRepository: UsuarioRepository
) {

    // Prueba 1: Consultar todas las preferencias
    @Test
    fun testFindAllPreferenciasIA() {
        val preferencias: List<PreferenciaIA> = preferenciaIARepository.findAll()
        println("Total de preferencias encontradas: ${preferencias.size}")
        Assertions.assertTrue(preferencias.isNotEmpty(), "La lista de preferencias no debería estar vacía")
    }

    // Prueba 2: Buscar preferencias por ID de usuario
    @Test
    fun testFindPreferenciaIAByUsuario() {
        val usuarioId = 1
        val preferencias = preferenciaIARepository.findAll().filter { it.usuario?.userId == usuarioId }
        println("Preferencias para el usuario con ID $usuarioId: ${preferencias.size}")
        Assertions.assertTrue(preferencias.isNotEmpty(), "El usuario debería tener al menos una preferencia configurada")
    }

    // Prueba 3: Crear una nueva preferencia
    @Test
    fun testCreatePreferenciaIA() {
        val usuario = usuarioRepository.findById(1).orElseThrow { AssertionError("Usuario no encontrado") }

        val nuevaPreferenciaIA = PreferenciaIA().apply {
            historialBusqueda = "Pizzas, Hamburguesas"
            gustosIdentificados = "Comida Rápida, Italiana"
            alergiasIdentificadas = "Gluten"
            this.usuario = usuario
        }

        val preferenciaGuardada = preferenciaIARepository.save(nuevaPreferenciaIA)
        println("Preferencia creada con éxito: (ID: ${preferenciaGuardada.preferenciaId})")
        Assertions.assertNotNull(preferenciaGuardada.preferenciaId, "El ID de la preferencia no debería ser nulo después de guardarse")
    }

    // Prueba 4: Actualizar una preferencia
    @Test
    fun testUpdatePreferenciaIA() {
        val preferencia = preferenciaIARepository.findById(1).orElse(null)
        Assertions.assertNotNull(preferencia)

        preferencia!!.gustosIdentificados = "Comida Rápida, Mexicana"
        preferenciaIARepository.save(preferencia)

        val preferenciaActualizada = preferenciaIARepository.findById(1).orElse(null)
        Assertions.assertNotNull(preferenciaActualizada)
        Assertions.assertEquals("Comida Rápida, Mexicana", preferenciaActualizada!!.gustosIdentificados)
    }

    // Prueba 5: Eliminar una preferencia
    @Test
    fun testDeletePreferenciaIA() {
        val preferencia = preferenciaIARepository.findById(1).orElse(null)
        Assertions.assertNotNull(preferencia)

        preferenciaIARepository.delete(preferencia!!)

        val preferenciaEliminada = preferenciaIARepository.findById(1)
        Assertions.assertTrue(preferenciaEliminada.isEmpty)
    }
}
