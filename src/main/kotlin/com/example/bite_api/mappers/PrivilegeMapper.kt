package com.example.bite_api.mappers

import com.example.bite_api.PrivilegeDTO
import com.example.bite_api.entities.Privilege
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface PrivilegeMapper {
    companion object {
        val INSTANCE: PrivilegeMapper = Mappers.getMapper(PrivilegeMapper::class.java)
    }

    fun privilegeToPrivilegeDTO(privilege: Privilege): PrivilegeDTO
    fun privilegeDTOToPrivilege(privilegeDTO: PrivilegeDTO): Privilege
}
