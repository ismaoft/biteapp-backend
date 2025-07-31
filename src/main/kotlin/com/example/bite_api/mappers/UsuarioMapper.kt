package com.example.bite_api.mappers

import com.example.bite_api.UsuarioDTO
import com.example.bite_api.entities.Usuario
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface UsuarioMapper {
    companion object {
        val INSTANCE: UsuarioMapper = Mappers.getMapper(UsuarioMapper::class.java)
    }

    fun usuarioToUsuarioDTO(usuario: Usuario): UsuarioDTO
    fun usuarioDTOToUsuario(usuarioDTO: UsuarioDTO): Usuario
}

