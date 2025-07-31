package com.example.bite_api

import com.example.bite_api.entities.*
import com.example.bite_api.repositories.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
    statements = [
        "DELETE FROM public.user_role",
        "DELETE FROM public.role",
        "DELETE FROM public.usuarios"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class UserRoleTests(
    @Autowired val userRoleRepository: UserRoleRepository,
    @Autowired val roleRepository: RoleRepository,
    @Autowired val usuarioRepository: UsuarioRepository
) {

    lateinit var savedUser: Usuario
    lateinit var savedRole: Role
    lateinit var savedUserRole: UserRole

    @BeforeEach
    fun setup() {
        if (usuarioRepository.findAll().isEmpty()) {
            val usuario = Usuario().apply {
                nombre = "Juan"
                email = "juan@example.com"
                password = "password"
            }
            savedUser = usuarioRepository.save(usuario)
        } else {
            savedUser = usuarioRepository.findAll().first()
        }

        if (roleRepository.findAll().isEmpty()) {
            val role = Role().apply { name = "USER" }
            savedRole = roleRepository.save(role)
        } else {
            savedRole = roleRepository.findAll().first()
        }

        if (userRoleRepository.findAll().isEmpty()) {
            val userRoleId = UserRoleId(userId = savedUser.userId, roleId = savedRole.id)
            savedUserRole = UserRole().apply {
                id = userRoleId
                usuario = savedUser
                role = savedRole
            }
            userRoleRepository.save(savedUserRole)
        } else {
            savedUserRole = userRoleRepository.findAll().first()
        }
    }

    // Prueba 1: Crear una nueva relación de UserRole
    @Test
    fun testCreateUserRole() {
        val userRoleId = UserRoleId(userId = savedUser.userId, roleId = savedRole.id)
        val newUserRole = UserRole().apply {
            id = userRoleId
            usuario = savedUser
            role = savedRole
        }

        val savedUserRole = userRoleRepository.save(newUserRole)
        assertNotNull(savedUserRole.id, "El ID de UserRole no debería ser nulo después de guardarse")
        assertEquals(savedUserRole.role!!.name, savedRole.name, "El nombre del rol debe coincidir con el rol asignado")
        assertEquals(savedUserRole.usuario!!.nombre, savedUser.nombre, "El nombre del usuario debe coincidir con el usuario asignado")
    }

    // Prueba 2: Consultar todos los roles de un usuario
    @Test
    fun testFindAllUserRoles() {
        val userRoles = userRoleRepository.findAll()
        println("Total de roles por usuario encontrados: ${userRoles.size}")
        assertTrue(userRoles.isNotEmpty(), "La lista de roles por usuario no debería estar vacía")
    }

    // Prueba 3: Eliminar un UserRole
    @Test
    fun testDeleteUserRole() {
        val userRoleId = UserRoleId(userId = savedUser.userId, roleId = savedRole.id)
        val userRole = userRoleRepository.findById(userRoleId).orElseThrow { AssertionError("UserRole no encontrado") }
        userRoleRepository.delete(userRole)

        val deletedUserRole = userRoleRepository.findById(userRoleId)
        assertTrue(deletedUserRole.isEmpty, "El UserRole debería haberse eliminado correctamente")
    }
}
