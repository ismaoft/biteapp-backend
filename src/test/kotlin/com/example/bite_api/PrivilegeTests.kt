package com.example.bite_api

import com.example.bite_api.entities.Privilege
import com.example.bite_api.repositories.PrivilegeRepository
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
        "DELETE FROM public.privilege"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class PrivilegeTests(
    @Autowired val privilegeRepository: PrivilegeRepository
) {

    @BeforeEach
    fun setup() {
        if (privilegeRepository.findAll().isEmpty()) {
            val readPrivilege = Privilege().apply { name = "READ_PRIVILEGE" }
            val writePrivilege = Privilege().apply { name = "WRITE_PRIVILEGE" }
            privilegeRepository.saveAll(listOf(readPrivilege, writePrivilege))
        }
    }

    // Prueba 1: Crear y guardar un nuevo privilegio
    @Test
    fun testCreatePrivilege() {
        val newPrivilege = Privilege().apply { name = "EDIT_PRIVILEGE" }
        val savedPrivilege = privilegeRepository.save(newPrivilege)
        assertNotNull(savedPrivilege.id, "El ID del privilegio no debería ser nulo después de guardarse")
        assertEquals("EDIT_PRIVILEGE", savedPrivilege.name, "El nombre del privilegio debería ser 'EDIT_PRIVILEGE'")
    }

    // Prueba 2: Consultar todos los privilegios
    @Test
    fun testFindAllPrivileges() {
        val privileges = privilegeRepository.findAll()
        assertTrue(privileges.isNotEmpty(), "La lista de privilegios no debería estar vacía")
    }

    // Prueba 3: Actualizar un privilegio existente
    @Test
    fun testUpdatePrivilege() {
        val privilege = privilegeRepository.findAll().firstOrNull() ?: fail("Privilegio no encontrado")
        privilege.name = "DELETE_PRIVILEGE"
        val updatedPrivilege = privilegeRepository.save(privilege)
        assertEquals("DELETE_PRIVILEGE", updatedPrivilege.name, "El nombre del privilegio debería haberse actualizado a 'DELETE_PRIVILEGE'")
    }

    // Prueba 4: Eliminar un privilegio
    @Test
    fun testDeletePrivilege() {
        val privilege = privilegeRepository.findAll().firstOrNull() ?: fail("Privilegio no encontrado")
        privilegeRepository.delete(privilege)
        val deletedPrivilege = privilegeRepository.findById(privilege.id!!)
        assertTrue(deletedPrivilege.isEmpty, "El privilegio debería haberse eliminado correctamente")
    }
}
