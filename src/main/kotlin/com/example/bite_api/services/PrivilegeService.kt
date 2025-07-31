package com.example.bite_api.services

import com.example.bite_api.PrivilegeDTO
import com.example.bite_api.entities.Privilege
import com.example.bite_api.mappers.PrivilegeMapper
import com.example.bite_api.repositories.PrivilegeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PrivilegeService @Autowired constructor(
    private val privilegeRepository: PrivilegeRepository,
    private val privilegeMapper: PrivilegeMapper
) {

    fun findAll(): List<PrivilegeDTO> {
        val privileges = privilegeRepository.findAll()
        return privileges.map { privilegeMapper.privilegeToPrivilegeDTO(it) }
    }

    fun findById(privilegeId: Long): PrivilegeDTO? {
        val privilege: Optional<Privilege> = privilegeRepository.findById(privilegeId)
        return if (privilege.isPresent) privilegeMapper.privilegeToPrivilegeDTO(privilege.get()) else null
    }

    fun create(privilegeDTO: PrivilegeDTO): PrivilegeDTO {
        val privilege = privilegeMapper.privilegeDTOToPrivilege(privilegeDTO)
        val savedPrivilege = privilegeRepository.save(privilege)
        return privilegeMapper.privilegeToPrivilegeDTO(savedPrivilege)
    }

    fun update(privilegeId: Long, privilegeDTO: PrivilegeDTO): PrivilegeDTO? {
        if (privilegeRepository.existsById(privilegeId)) {
            val privilege = privilegeMapper.privilegeDTOToPrivilege(privilegeDTO)
            privilege.id = privilegeId
            val updatedPrivilege = privilegeRepository.save(privilege)
            return privilegeMapper.privilegeToPrivilegeDTO(updatedPrivilege)
        }
        return null
    }

    fun delete(privilegeId: Long) {
        if (privilegeRepository.existsById(privilegeId)) {
            privilegeRepository.deleteById(privilegeId)
        }
    }
}
