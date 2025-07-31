package com.example.bite_api

import com.example.bite_api.entities.Producto
import com.example.bite_api.repositories.ProductoRepository
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
        "DELETE FROM public.detalles_transaccion",
        "DELETE FROM public.transacciones",
        "DELETE FROM public.valoraciones",
        "DELETE FROM public.productos"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-productos.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class ProductoTests(
    @Autowired
    val productoRepository: ProductoRepository,
) {

    @Test
    fun testFindAllProductos() {
        val productos: List<Producto> = productoRepository.findAll()
        Assertions.assertTrue(productos.isNotEmpty())
    }

    @Test
    fun testFindProductoById() {
        val producto: Producto = productoRepository.findById(1).orElse(null)
        Assertions.assertNotNull(producto)
        Assertions.assertEquals(1, producto.productoId)
    }

    @Test
    fun testCreateProducto() {
        val nuevoProducto = Producto().apply {
            nombre = "Pizza Margarita"
            descripcion = "Pizza con base de tomate, mozzarella y albahaca"
            precio = 15.99
            disponibilidad = true
        }
        val productoCreado = productoRepository.save(nuevoProducto)
        Assertions.assertNotNull(productoCreado.productoId)
    }
}
