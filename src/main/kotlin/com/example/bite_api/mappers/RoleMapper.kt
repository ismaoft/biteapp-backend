package com.example.bite_api.mappers

import com.example.bite_api.RoleDTO
import com.example.bite_api.entities.Role
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface RoleMapper {
    companion object {
        val INSTANCE: RoleMapper = Mappers.getMapper(RoleMapper::class.java)
    }

    fun roleToRoleDTO(role: Role): RoleDTO
    fun roleDTOToRole(roleDTO: RoleDTO): Role
}
