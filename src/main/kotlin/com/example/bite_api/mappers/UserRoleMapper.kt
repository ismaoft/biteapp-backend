package com.example.bite_api.mappers

import com.example.bite_api.UserRoleDTO
import com.example.bite_api.entities.UserRole
import org.mapstruct.*
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring")
interface UserRoleMapper {

    companion object {
        val INSTANCE: UserRoleMapper = Mappers.getMapper(UserRoleMapper::class.java)
    }

    // Mapea de UserRole a UserRoleDTO
    @Mappings(
        Mapping(source = "id.userId", target = "userId"),
        Mapping(source = "id.roleId", target = "roleId")
    )
    fun userRoleToUserRoleDTO(userRole: UserRole): UserRoleDTO

    // Mapea de UserRoleDTO a UserRole, incluyendo el manejo de la clave compuesta
    @Mappings(
        Mapping(target = "id", expression = "java(new com.example.bite_api.entities.UserRoleId(userRoleDTO.getUserId(), userRoleDTO.getRoleId()))")
    )
    fun userRoleDTOToUserRole(userRoleDTO: UserRoleDTO): UserRole
}
