package com.example.bite_api

import com.example.bite_api.entities.DetalleTransaccion
import com.example.bite_api.entities.Producto
import com.example.bite_api.entities.Transaccion
import com.example.bite_api.repositories.DetalleTransaccionRepository
import com.example.bite_api.repositories.ProductoRepository
import com.example.bite_api.repositories.TransaccionRepository
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
        "DELETE FROM public.detalles_transaccion",
        "DELETE FROM public.productos",
        "DELETE FROM public.transacciones"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-detalles-transaccion.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class DetalleTransaccionTests(
    @Autowired val detalleTransaccionRepository: DetalleTransaccionRepository,
    @Autowired val transaccionRepository: TransaccionRepository,
    @Autowired val productoRepository: ProductoRepository
) {

    // Prueba 1: Consultar todos los detalles de transacciones
    @Test
    fun testFindAllDetallesTransaccion() {
        val detalles: List<DetalleTransaccion> = detalleTransaccionRepository.findAll()
        println("Total de detalles de transacción encontrados: ${detalles.size}")
        assertTrue(detalles.isNotEmpty(), "La lista de detalles de transacción no debería estar vacía")
    }

    // Prueba 2: Buscar un detalle de transacción por ID
    @Test
    fun testFindDetalleTransaccionById() {
        val detalleId = 1
        val detalle = detalleTransaccionRepository.findById(detalleId).orElse(null)
        println("Detalle de transacción con ID $detalleId: ${detalle?.subtotal}")
        assertNotNull(detalle, "El detalle de transacción con ID $detalleId debería existir")
    }

    // Prueba 3: Crear un nuevo detalle de transacción
    @Test
    fun testCreateDetalleTransaccion() {
        val transaccion = transaccionRepository.findById(1).orElseThrow { AssertionError("Transacción no encontrada") }
        val producto = productoRepository.findById(1).orElseThrow { AssertionError("Producto no encontrado") }

        val nuevoDetalle = DetalleTransaccion().apply {
            cantidad = 3
            precioUnitario = producto.precio
            subtotal = cantidad * precioUnitario
            this.transaccion = transaccion
            this.producto = producto
        }

        val detalleGuardado = detalleTransaccionRepository.save(nuevoDetalle)
        println("Detalle de transacción creado con éxito: (ID: ${detalleGuardado.detalleId})")
        assertNotNull(detalleGuardado.detalleId, "El ID del detalle de transacción no debería ser nulo después de guardarse")
    }

    // Prueba 4: Actualizar un detalle de transacción existente
    @Test
    fun testUpdateDetalleTransaccionCantidad() {
        val detalle = detalleTransaccionRepository.findById(1).orElse(null)
        assertNotNull(detalle)

        detalle!!.cantidad = 5
        detalle.subtotal = detalle.cantidad * detalle.precioUnitario
        detalleTransaccionRepository.save(detalle)

        val detalleActualizado = detalleTransaccionRepository.findById(1).orElse(null)
        assertNotNull(detalleActualizado)
        assertEquals(5, detalleActualizado!!.cantidad)
    }

    // Prueba 5: Eliminar un detalle de transacción
    @Test
    fun testDeleteDetalleTransaccion() {
        val detalle = detalleTransaccionRepository.findById(1).orElse(null)
        assertNotNull(detalle)

        detalleTransaccionRepository.delete(detalle!!)

        val detalleEliminado = detalleTransaccionRepository.findById(1)
        assertTrue(detalleEliminado.isEmpty)
    }
}
