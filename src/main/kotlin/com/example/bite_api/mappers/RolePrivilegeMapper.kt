package com.example.bite_api.mappers

import com.example.bite_api.RolePrivilegeDTO
import com.example.bite_api.entities.RolePrivilege
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface RolePrivilegeMapper {
    companion object {
        val INSTANCE: RolePrivilegeMapper = Mappers.getMapper(RolePrivilegeMapper::class.java)
    }

    fun rolePrivilegeToRolePrivilegeDTO(rolePrivilege: RolePrivilege): RolePrivilegeDTO
    fun rolePrivilegeDTOToRolePrivilege(rolePrivilegeDTO: RolePrivilegeDTO): RolePrivilege
}
