package com.example.bite_api

import com.example.bite_api.entities.Categoria
import com.example.bite_api.repositories.CategoriaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
    statements = [
        "DELETE FROM public.productos",
        "DELETE FROM public.categorias"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-categorias.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class CategoriaTests(
    @Autowired val categoriaRepository: CategoriaRepository
) {

    // Prueba 1: Consultar todas las categorías
    @Test
    fun testFindAllCategorias() {
        val categorias: List<Categoria> = categoriaRepository.findAll()
        println("Total de categorías encontradas: ${categorias.size}")
        assertTrue(categorias.isNotEmpty(), "La lista de categorías no debería estar vacía")
    }

    // Prueba 2: Buscar una categoría por su ID
    @Test
    fun testFindCategoriaById() {
        val categoriaId = 1
        val categoria = categoriaRepository.findById(categoriaId).orElse(null)
        println("Categoría con ID $categoriaId: ${categoria?.nombre}")
        assertNotNull(categoria, "La categoría con ID $categoriaId debería existir")
    }

    // Prueba 3: Crear una nueva categoría
    @Test
    fun testCreateCategoria() {
        val nuevaCategoria = Categoria().apply {
            nombre = "Postres"
        }

        val categoriaGuardada = categoriaRepository.save(nuevaCategoria)
        println("Categoría creada con éxito: (ID: ${categoriaGuardada.categoriaId})")
        assertNotNull(categoriaGuardada.categoriaId, "El ID de la categoría no debería ser nulo después de guardarse")
    }

    // Prueba 4: Actualizar una categoría existente
    @Test
    fun testUpdateCategoriaNombre() {
        val categoria = categoriaRepository.findById(1).orElse(null)
        assertNotNull(categoria)

        categoria!!.nombre = "Comida Rápida"
        categoriaRepository.save(categoria)

        val categoriaActualizada = categoriaRepository.findById(1).orElse(null)
        assertNotNull(categoriaActualizada)
        assertEquals("Comida Rápida", categoriaActualizada!!.nombre)
    }

    // Prueba 5: Eliminar una categoría
    @Test
    fun testDeleteCategoria() {
        val categoria = categoriaRepository.findById(1).orElse(null)
        assertNotNull(categoria)

        categoriaRepository.delete(categoria!!)

        val categoriaEliminada = categoriaRepository.findById(1)
        assertTrue(categoriaEliminada.isEmpty)
    }
}
