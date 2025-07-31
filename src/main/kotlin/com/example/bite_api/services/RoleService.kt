package com.example.bite_api.services

import com.example.bite_api.RoleDTO
import com.example.bite_api.entities.Role
import com.example.bite_api.mappers.RoleMapper
import com.example.bite_api.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleService @Autowired constructor(
    private val roleRepository: RoleRepository,
    private val roleMapper: RoleMapper
) {

    fun findAll(): List<RoleDTO> {
        val roles = roleRepository.findAll()
        return roles.map { roleMapper.roleToRoleDTO(it) }
    }

    fun findById(roleId: Long): RoleDTO? {
        val role: Optional<Role> = roleRepository.findById(roleId)
        return if (role.isPresent) roleMapper.roleToRoleDTO(role.get()) else null
    }

    fun create(roleDTO: RoleDTO): RoleDTO {
        val role = roleMapper.roleDTOToRole(roleDTO)
        val savedRole = roleRepository.save(role)
        return roleMapper.roleToRoleDTO(savedRole)
    }

    fun update(roleId: Long, roleDTO: RoleDTO): RoleDTO? {
        if (roleRepository.existsById(roleId)) {
            val role = roleMapper.roleDTOToRole(roleDTO)
            role.id = roleId
            val updatedRole = roleRepository.save(role)
            return roleMapper.roleToRoleDTO(updatedRole)
        }
        return null
    }

    fun delete(roleId: Long) {
        if (roleRepository.existsById(roleId)) {
            roleRepository.deleteById(roleId)
        }
    }
}
