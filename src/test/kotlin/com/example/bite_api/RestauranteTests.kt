package com.example.bite_api

import com.example.bite_api.entities.Restaurante
import com.example.bite_api.repositories.RestauranteRepository
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
    scripts = ["/import-restaurantes.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class RestauranteTests(
    @Autowired
    val restauranteRepository: RestauranteRepository
) {


    @Test
    fun testRestauranteFindAll() {
        val restaurantes: List<Restaurante> = restauranteRepository.findAll()
        Assertions.assertTrue(restaurantes.isNotEmpty())
    }

    @Test
    fun testRestauranteFindById() {
        val restaurante: Restaurante = restauranteRepository.findById(1).orElse(null)
        Assertions.assertNotNull(restaurante)
        Assertions.assertEquals("Restaurante A", restaurante.nombre)
    }

    @Test
    fun testCreateRestaurante() {
        val nuevoRestaurante = Restaurante()
        nuevoRestaurante.nombre = "Restaurante C"
        nuevoRestaurante.direccion = "Direccion C"
        nuevoRestaurante.telefono = "3333333333"
        nuevoRestaurante.categoria = "China"
        nuevoRestaurante.horarioAtencion = "10:00 AM - 8:00 PM"
        nuevoRestaurante.descripcion = "Restaurante de comida china"
        val savedRestaurante = restauranteRepository.save(nuevoRestaurante)
        Assertions.assertNotNull(savedRestaurante.restaurantId)
        Assertions.assertEquals("Restaurante C", savedRestaurante.nombre)
    }

    // Prueba para buscar restaurantes por categoría
    @Test
    fun testFindRestauranteByCategory() {
        // Buscar restaurantes por la categoría "Italiana"
        val categoriaBuscada = "Italiana"
        val restaurantes = restauranteRepository.findAll().filter { it.categoria == categoriaBuscada }

        // Verificamos que hay al menos un restaurante con esa categoría
        Assertions.assertTrue(restaurantes.isNotEmpty())
        Assertions.assertEquals(categoriaBuscada, restaurantes.first().categoria)
    }

    // Prueba para actualizar la dirección de un restaurante
    @Test
    fun testUpdateRestauranteDireccion() {
        // Recuperar el restaurante con ID 1
        val restaurante = restauranteRepository.findById(1).orElse(null)
        Assertions.assertNotNull(restaurante)

        val nuevaDireccion = "Calle Nueva, Número 123"
        restaurante.direccion = nuevaDireccion
        restauranteRepository.save(restaurante)

        val restauranteActualizado = restauranteRepository.findById(1).orElse(null)
        Assertions.assertNotNull(restauranteActualizado)
        Assertions.assertEquals(nuevaDireccion, restauranteActualizado.direccion)
    }



}
