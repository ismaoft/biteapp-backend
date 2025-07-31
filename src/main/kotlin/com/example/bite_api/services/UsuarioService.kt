package com.example.bite_api.services

import com.example.bite_api.UsuarioDTO
import com.example.bite_api.entities.*
import com.example.bite_api.mappers.UsuarioMapper
import com.example.bite_api.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService @Autowired constructor(
    val usuarioRepository: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper,
    val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository,
    private val userRoleRepository: UserRoleRepository,
    private val privilegeRepository: PrivilegeRepository,
    private val rolePrivilegeRepository: RolePrivilegeRepository
) {

    // Obtener todos los usuarios
    fun findAll(): List<UsuarioDTO> {
        val usuarios = usuarioRepository.findAll()
        return usuarios.map { usuarioMapper.usuarioToUsuarioDTO(it) }
    }

    // Buscar usuario por ID
    fun findById(userId: Int): UsuarioDTO? {
        val usuario: Optional<Usuario> = usuarioRepository.findById(userId)
        return usuario.map {
            val usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(it)
            usuarioDTO.copy(roleName = it.userRoles.firstOrNull()?.role?.name)  // Incluye el roleName
        }.orElse(null)
    }


    // Crear un nuevo usuario con la contraseña cifrada
    fun create(usuarioDTO: UsuarioDTO): UsuarioDTO {
        // Mapear DTO a entidad Usuario
        val usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO)
        usuario.password = passwordEncoder.encode(usuario.password)

        // Guardar el usuario en la base de datos
        val savedUsuario = usuarioRepository.save(usuario)

        // Asignar rol (sin eliminar funcionalidades)
        val roleName = usuarioDTO.roleName ?: "USER"
        val role = roleRepository.findByName(roleName).orElseThrow {
            Exception("Rol '$roleName' no encontrado")
        }

        val userRole = UserRole(
            id = UserRoleId(savedUsuario.userId, role.id),
            usuario = savedUsuario,
            role = role
        )
        userRoleRepository.save(userRole)

        // Asignar privilegios al rol
        val privilegeList = privilegeRepository.findAll()
        privilegeList.forEach { privilege ->
            val rolePrivilege = RolePrivilege(
                id = RolePrivilegeId(roleId = role.id, privilegeId = privilege.id),
                role = role,
                privilege = privilege
            )
            rolePrivilegeRepository.save(rolePrivilege)
        }

        // Retornar el DTO actualizado
        return usuarioMapper.usuarioToUsuarioDTO(savedUsuario).copy(roleName = roleName)
    }




    // Actualizar un usuario existente
    fun update(userId: Int, usuarioDTO: UsuarioDTO): UsuarioDTO? {
        return if (usuarioRepository.existsById(userId)) {
            val usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO)
            usuario.userId = userId

            // Guardar cambios en la base de datos
            val updatedUsuario = usuarioRepository.save(usuario)
            usuarioMapper.usuarioToUsuarioDTO(updatedUsuario)
        } else {
            null
        }
    }


    // Eliminar un usuario
    fun delete(userId: Int) {
        if (usuarioRepository.existsById(userId)) {
            usuarioRepository.deleteById(userId)
        }
    }

    fun findByNombre(nombre: String): UsuarioDTO? {
        val usuario = usuarioRepository.findByNombre(nombre)
        return usuario?.let {
            val usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(it)
            usuarioDTO.copy(roleName = it.userRoles.firstOrNull()?.role?.name)  // Incluye el roleName
        }
    }


    fun authenticate(nombre: String, password: String): UsuarioDTO? {
        val usuario = usuarioRepository.findByNombre(nombre)
        return if (usuario != null && passwordEncoder.matches(password, usuario.password)) {
            val usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario)
            usuarioDTO.copy(roleName = usuario.userRoles.firstOrNull()?.role?.name)  // Incluye el roleName
        } else {
            null
        }
    }

    fun actualizarTokenFCM(userId: Int, tokenFCM: String): Usuario {
        val usuario = usuarioRepository.findById(userId)
            .orElseThrow { RuntimeException("Usuario no encontrado") }
        println("Actualizando token FCM para usuario ${usuario.nombre}: $tokenFCM")
        usuario.tokenFCM = tokenFCM
        return usuarioRepository.save(usuario)
    }


}
