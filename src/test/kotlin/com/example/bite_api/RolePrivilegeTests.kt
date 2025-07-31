package com.example.bite_api

import com.example.bite_api.entities.Privilege
import com.example.bite_api.entities.Role
import com.example.bite_api.entities.RolePrivilege
import com.example.bite_api.entities.RolePrivilegeId
import com.example.bite_api.repositories.PrivilegeRepository
import com.example.bite_api.repositories.RolePrivilegeRepository
import com.example.bite_api.repositories.RoleRepository
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
        "DELETE FROM public.preferencias_ia",
        "DELETE FROM public.valoraciones",
        "DELETE FROM public.detalles_transaccion",
        "DELETE FROM public.transacciones",
        "DELETE FROM public.productos",
        "DELETE FROM public.categorias",
        "DELETE FROM public.restaurantes",
        "DELETE FROM public.privilege",
        "DELETE FROM public.role",
        "DELETE FROM public.usuarios"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = [
        "/import-usuarios.sql",
        "/import-roles.sql",
        "/import-privileges.sql",
        "/import-role_privileges.sql"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class RolePrivilegeTests(
    @Autowired val roleRepository: RoleRepository,
    @Autowired val privilegeRepository: PrivilegeRepository,
    @Autowired val rolePrivilegeRepository: RolePrivilegeRepository
) {

    // Prueba 1: Consultar todos los privilegios por rol
    @Test
    fun testFindAllPrivilegesByRole() {
        val role = roleRepository.findById(1).orElseThrow { AssertionError("Rol no encontrado") }
        val rolePrivileges: List<RolePrivilege> = rolePrivilegeRepository.findAll().filter { it.role?.id == role.id }
        println("Total de privilegios para el rol con ID ${role.id}: ${rolePrivileges.size}")
        Assertions.assertTrue(rolePrivileges.isNotEmpty(), "El rol debería tener al menos un privilegio asociado")
    }

    // Prueba 2: Crear un nuevo privilegio para un rol
    @Test
    fun testCreateRolePrivilege() {
        val role = roleRepository.findById(1).orElseThrow { AssertionError("Rol no encontrado") }
        val privilege = privilegeRepository.findById(1).orElseThrow { AssertionError("Privilegio no encontrado") }

        val nuevoRolePrivilege = RolePrivilege().apply {
            this.role = role
            this.privilege = privilege
            this.id = RolePrivilegeId(roleId = role.id!!, privilegeId = privilege.id!!)
        }

        val rolePrivilegeGuardado = rolePrivilegeRepository.save(nuevoRolePrivilege)
        println("Relación Rol-Privilegio creada con éxito: (ID: ${rolePrivilegeGuardado.id})")
        Assertions.assertNotNull(rolePrivilegeGuardado.id, "El ID de la relación no debería ser nulo después de guardarse")
    }

    // Prueba 3: Actualizar un privilegio en un rol
    @Test
    fun testUpdateRolePrivilege() {
        val originalRolePrivilegeId = RolePrivilegeId(roleId = 1, privilegeId = 1)
        val rolePrivilege = rolePrivilegeRepository.findById(originalRolePrivilegeId).orElse(null)
        Assertions.assertNotNull(rolePrivilege, "La relación inicial de Role-Privilege no debería ser nula")

        val nuevoPrivilegio = privilegeRepository.findById(2).orElseThrow { AssertionError("Nuevo privilegio no encontrado") }
        rolePrivilege!!.privilege = nuevoPrivilegio

        rolePrivilegeRepository.save(rolePrivilege)
        val nuevoRolePrivilegeId = RolePrivilegeId(roleId = 1, privilegeId = 2)

        val rolePrivilegeActualizado = rolePrivilegeRepository.findById(nuevoRolePrivilegeId).orElse(null)
        Assertions.assertNotNull(rolePrivilegeActualizado, "La relación actualizada de Role-Privilege no debería ser nula")
        Assertions.assertEquals(nuevoPrivilegio.id, rolePrivilegeActualizado!!.privilege?.id, "El ID del privilegio después de la actualización no coincide")
    }


    // Prueba 4: Eliminar una relación de privilegio y rol
    @Test
    fun testDeleteRolePrivilege() {
        val rolePrivilegeId = RolePrivilegeId(roleId = 1, privilegeId = 1)
        val rolePrivilege = rolePrivilegeRepository.findById(rolePrivilegeId).orElse(null)
        Assertions.assertNotNull(rolePrivilege)

        rolePrivilegeRepository.delete(rolePrivilege!!)
        val rolePrivilegeEliminado = rolePrivilegeRepository.findById(rolePrivilegeId)
        Assertions.assertTrue(rolePrivilegeEliminado.isEmpty)
    }
}
