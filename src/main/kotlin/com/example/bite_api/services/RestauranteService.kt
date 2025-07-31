package com.example.bite_api.services

import com.example.bite_api.RestauranteDTO
import com.example.bite_api.entities.Restaurante
import com.example.bite_api.mappers.RestauranteMapper
import com.example.bite_api.repositories.RestauranteRepository
import com.example.bite_api.repositories.UserRoleRepository
import com.example.bite_api.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RestauranteService @Autowired constructor(
    private val restauranteRepository: RestauranteRepository,
    private val restauranteMapper: RestauranteMapper,
    private val usuarioRepository: UsuarioRepository,
    private val userRoleRepository: UserRoleRepository
) {

    // Obtener todos los restaurantes
    fun findAll(): List<RestauranteDTO> {
        val restaurantes = restauranteRepository.findAll()
        return restaurantes.map { restauranteMapper.restauranteToRestauranteDTO(it) }
    }

    // Buscar restaurante por ID
    fun findById(restaurantId: Int): RestauranteDTO? {
        val restaurante: Optional<Restaurante> = restauranteRepository.findById(restaurantId)
        return if (restaurante.isPresent) restauranteMapper.restauranteToRestauranteDTO(restaurante.get()) else null
    }

    fun create(restauranteDTO: RestauranteDTO): RestauranteDTO {
        // Convertir DTO a entidad Restaurante
        val restaurante = restauranteMapper.restauranteDTOToRestaurante(restauranteDTO)

        // Verificar el administrador
        val adminId = restauranteDTO.adminId
        val administrador = usuarioRepository.findById(adminId!!)
            .orElseThrow { RuntimeException("Administrador no encontrado con el ID: $adminId") }

        val esAdmin = userRoleRepository.findByUsuarioUserIdAndRoleName(adminId, "ADMIN").isPresent
        if (!esAdmin) throw RuntimeException("El usuario con ID: $adminId no tiene permisos de administrador")

        // Asignar administrador y guardar el restaurante
        restaurante.administrador = administrador
        val savedRestaurante = restauranteRepository.save(restaurante)

        return restauranteMapper.restauranteToRestauranteDTO(savedRestaurante)
    }



    // Actualizar un restaurante existente
    fun update(restaurantId: Int, restauranteDTO: RestauranteDTO): RestauranteDTO? {
        if (restauranteRepository.existsById(restaurantId)) {
            val restaurante = restauranteMapper.restauranteDTOToRestaurante(restauranteDTO)
            restaurante.restaurantId = restaurantId

            // Guardar los cambios
            val updatedRestaurante = restauranteRepository.save(restaurante)
            return restauranteMapper.restauranteToRestauranteDTO(updatedRestaurante)
        }
        return null
    }


    // Eliminar un restaurante
    fun delete(restaurantId: Int) {
        if (restauranteRepository.existsById(restaurantId)) {
            restauranteRepository.deleteById(restaurantId)
        }
    }

    // Método para obtener restaurantes por categoría
    fun findByCategoria(categoria: String): List<RestauranteDTO> {
        val restaurantes = restauranteRepository.findByCategoria(categoria)
        return restaurantes.map { restauranteMapper.restauranteToRestauranteDTO(it) }
    }
}
