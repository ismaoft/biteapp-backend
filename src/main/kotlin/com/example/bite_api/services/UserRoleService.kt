package com.example.bite_api.services

import com.example.bite_api.UserRoleDTO
import com.example.bite_api.entities.UserRole
import com.example.bite_api.entities.UserRoleId
import com.example.bite_api.mappers.UserRoleMapper
import com.example.bite_api.repositories.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserRoleService @Autowired constructor(
    private val userRoleRepository: UserRoleRepository,
    private val userRoleMapper: UserRoleMapper
) {

    fun findAll(): List<UserRoleDTO> {
        val userRoles = userRoleRepository.findAll()
        return userRoles.map { userRoleMapper.userRoleToUserRoleDTO(it) }
    }

    fun findById(userId: Int, roleId: Long): UserRoleDTO? {
        val userRoleId = UserRoleId(userId, roleId)
        val userRole: Optional<UserRole> = userRoleRepository.findById(userRoleId)
        return if (userRole.isPresent) userRoleMapper.userRoleToUserRoleDTO(userRole.get()) else null
    }

    fun create(userRoleDTO: UserRoleDTO): UserRoleDTO {
        val userRole = userRoleMapper.userRoleDTOToUserRole(userRoleDTO)
        val savedUserRole = userRoleRepository.save(userRole)
        return userRoleMapper.userRoleToUserRoleDTO(savedUserRole)
    }

    fun update(userId: Int, roleId: Long, userRoleDTO: UserRoleDTO): UserRoleDTO? {
        val userRoleId = UserRoleId(userId, roleId)
        if (userRoleRepository.existsById(userRoleId)) {
            val userRole = userRoleMapper.userRoleDTOToUserRole(userRoleDTO)
            userRole.id = userRoleId
            val updatedUserRole = userRoleRepository.save(userRole)
            return userRoleMapper.userRoleToUserRoleDTO(updatedUserRole)
        }
        return null
    }
    fun delete(userId: Int, roleId: Long) {
        // Crear la clave compuesta `UserRoleId` con los tipos correctos
        val userRoleId = UserRoleId(userId, roleId)
        if (userRoleRepository.existsById(userRoleId)) {
            userRoleRepository.deleteById(userRoleId)
        }
    }

}
