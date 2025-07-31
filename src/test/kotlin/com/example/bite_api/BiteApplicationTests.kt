package com.example.bite_api

import com.example.bite_api.entities.Restaurante
import com.example.bite_api.entities.Usuario
import com.example.bite_api.repositories.RestauranteRepository
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
	scripts = ["/import-usuarios.sql", "/import-restaurantes.sql"],
	executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class BiteApplicationTests(
	@Autowired
	val usuarioRepository: UsuarioRepository,
	@Autowired
	val restauranteRepository: RestauranteRepository
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
		// Recuperamos el usuario con ID 1 (que se carga desde el script)
		val usuario = usuarioRepository.findById(1).orElse(null)
		Assertions.assertNotNull(usuario)

		// Actualizamos el correo electrónico
		val nuevoEmail = "actualizado.email@test.com"
		usuario.email = nuevoEmail
		usuarioRepository.save(usuario)

		// Recuperamos nuevamente el usuario y verificamos el cambio
		val usuarioActualizado = usuarioRepository.findById(1).orElse(null)
		Assertions.assertNotNull(usuarioActualizado)
		Assertions.assertEquals(nuevoEmail, usuarioActualizado.email)
	}

	// Prueba para eliminar un usuario específico
	@Test
	fun testDeleteUsuario() {
		// Recuperamos el usuario con ID 1 y lo eliminamos
		val usuario = usuarioRepository.findById(1).orElse(null)
		Assertions.assertNotNull(usuario)

		usuarioRepository.delete(usuario)

		// Verificamos que el usuario ya no exista en la base de datos
		val usuarioEliminado = usuarioRepository.findById(1)
		Assertions.assertTrue(usuarioEliminado.isEmpty)
	}


	// Pruebas de Restaurante (nuevas)
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

		// Actualizar la dirección del restaurante
		val nuevaDireccion = "Calle Nueva, Número 123"
		restaurante.direccion = nuevaDireccion
		restauranteRepository.save(restaurante)

		// Recuperar nuevamente el restaurante y verificar el cambio
		val restauranteActualizado = restauranteRepository.findById(1).orElse(null)
		Assertions.assertNotNull(restauranteActualizado)
		Assertions.assertEquals(nuevaDireccion, restauranteActualizado.direccion)
	}



}
