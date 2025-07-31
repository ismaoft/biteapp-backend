package com.example.bite_api.services

import com.example.bite_api.RolePrivilegeDTO
import com.example.bite_api.entities.RolePrivilege
import com.example.bite_api.entities.RolePrivilegeId // Importa la nueva clase de ID compuesto
import com.example.bite_api.mappers.RolePrivilegeMapper
import com.example.bite_api.repositories.RolePrivilegeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RolePrivilegeService @Autowired constructor(
    private val rolePrivilegeRepository: RolePrivilegeRepository,
    private val rolePrivilegeMapper: RolePrivilegeMapper
) {

    fun findAll(): List<RolePrivilegeDTO> {
        val rolePrivileges = rolePrivilegeRepository.findAll()
        return rolePrivileges.map { rolePrivilegeMapper.rolePrivilegeToRolePrivilegeDTO(it) }
    }

    // Cambiar el parámetro de Long a RolePrivilegeId
    fun findById(rolePrivilegeId: RolePrivilegeId): RolePrivilegeDTO? {
        val rolePrivilege: Optional<RolePrivilege> = rolePrivilegeRepository.findById(rolePrivilegeId)
        return if (rolePrivilege.isPresent) rolePrivilegeMapper.rolePrivilegeToRolePrivilegeDTO(rolePrivilege.get()) else null
    }

    fun create(rolePrivilegeDTO: RolePrivilegeDTO): RolePrivilegeDTO {
        val rolePrivilege = rolePrivilegeMapper.rolePrivilegeDTOToRolePrivilege(rolePrivilegeDTO)
        val savedRolePrivilege = rolePrivilegeRepository.save(rolePrivilege)
        return rolePrivilegeMapper.rolePrivilegeToRolePrivilegeDTO(savedRolePrivilege)
    }

    // Cambiar el parámetro de Long a RolePrivilegeId
    fun update(rolePrivilegeId: RolePrivilegeId, rolePrivilegeDTO: RolePrivilegeDTO): RolePrivilegeDTO? {
        if (rolePrivilegeRepository.existsById(rolePrivilegeId)) {
            val rolePrivilege = rolePrivilegeMapper.rolePrivilegeDTOToRolePrivilege(rolePrivilegeDTO)
            rolePrivilege.id = rolePrivilegeId
            val updatedRolePrivilege = rolePrivilegeRepository.save(rolePrivilege)
            return rolePrivilegeMapper.rolePrivilegeToRolePrivilegeDTO(updatedRolePrivilege)
        }
        return null
    }

    // Cambiar el parámetro de Long a RolePrivilegeId
    fun delete(rolePrivilegeId: RolePrivilegeId) {
        if (rolePrivilegeRepository.existsById(rolePrivilegeId)) {
            rolePrivilegeRepository.deleteById(rolePrivilegeId)
        }
    }
}
