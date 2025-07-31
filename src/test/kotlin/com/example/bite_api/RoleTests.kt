package com.example.bite_api

import com.example.bite_api.entities.Role
import com.example.bite_api.repositories.RoleRepository
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
        "DELETE FROM public.role_privilege",
        "DELETE FROM public.user_role",
        "DELETE FROM public.role",
        "DELETE FROM public.privilege"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class RoleTests(
    @Autowired val roleRepository: RoleRepository
) {

    @BeforeEach
    fun setup() {
        if (roleRepository.findAll().isEmpty()) {
            val adminRole = Role().apply { name = "ADMIN" }
            val userRole = Role().apply { name = "USER" }
            roleRepository.saveAll(listOf(adminRole, userRole))
        }
    }

    // Prueba 1: Crear y guardar un nuevo rol
    @Test
    fun testCreateRole() {
        val newRole = Role().apply { name = "MANAGER" }
        val savedRole = roleRepository.save(newRole)
        assertNotNull(savedRole.id, "El ID del rol no debería ser nulo después de guardarse")
        assertEquals("MANAGER", savedRole.name, "El nombre del rol debería ser 'MANAGER'")
    }

    // Prueba 2: Consultar todos los roles
    @Test
    fun testFindAllRoles() {
        val roles = roleRepository.findAll()
        println("Total de roles: ${roles.size}")
        assertTrue(roles.isNotEmpty(), "La lista de roles no debería estar vacía")
    }

    // Prueba 3: Actualizar un rol existente
    @Test
    fun testUpdateRole() {
        val role = roleRepository.findAll().firstOrNull() ?: fail("Rol no encontrado")
        role.name = "SUPER_ADMIN"
        val updatedRole = roleRepository.save(role)
        assertEquals("SUPER_ADMIN", updatedRole.name, "El nombre del rol debería haberse actualizado a 'SUPER_ADMIN'")
    }

    // Prueba 4: Eliminar un rol
    @Test
    fun testDeleteRole() {
        val role = roleRepository.findAll().firstOrNull() ?: fail("Rol no encontrado")
        roleRepository.delete(role)
        val deletedRole = roleRepository.findById(role.id!!)
        assertTrue(deletedRole.isEmpty, "El rol debería haberse eliminado correctamente")
    }
}
