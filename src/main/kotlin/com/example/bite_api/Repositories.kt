package com.example.bite_api.repositories

import com.example.bite_api.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Int> {
    // Método para encontrar un usuario por su UserId
    fun findUsuarioByUserId(userId: Int): Usuario

    // Método para encontrar un usuario por su nombre de usuario (username)
    fun findByNombre(nombre: String): Usuario?
}


@Repository
interface RestauranteRepository : JpaRepository<Restaurante, Int> {
    fun findByRestaurantId(restaurantId: Int): Restaurante
    fun findByCategoria(categoria: String): List<Restaurante>
}



// Repositorio para la entidad Categoria
@Repository
interface CategoriaRepository : JpaRepository<Categoria, Int>

// Repositorio para la entidad Producto
@Repository
interface ProductoRepository : JpaRepository<Producto, Int>

// Repositorio para la entidad Transaccion
@Repository
interface TransaccionRepository : JpaRepository<Transaccion, Int> {
    fun findByUsuarioUserId(userId: Int): List<Transaccion>
    fun findByRestauranteRestaurantId(restaurantId: Int): List<Transaccion>
}



// Repositorio para la entidad DetalleTransaccion
@Repository
interface DetalleTransaccionRepository : JpaRepository<DetalleTransaccion, Int> {

    fun findByTransaccionTransaccionId(transaccionId: Int): List<DetalleTransaccion>
}


// Repositorio para la entidad Valoracion
@Repository
interface ValoracionRepository : JpaRepository<Valoracion, Int>

// Repositorio para la entidad PreferenciaIA
@Repository
interface PreferenciaIARepository : JpaRepository<PreferenciaIA, Int>

// Repositorio para la entidad Role
@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Optional<Role>
}


// Repositorio para la entidad Privilege
@Repository
interface PrivilegeRepository : JpaRepository<Privilege, Long>

// Repositorio para la entidad UserRole
@Repository
interface UserRoleRepository : JpaRepository<UserRole, UserRoleId> {
    // Método para verificar si un usuario tiene un rol específico
    fun findByUsuarioUserIdAndRoleName(userId: Int, roleName: String): Optional<UserRole>
}


// Repositorio para la entidad RolePrivilege
@Repository
interface RolePrivilegeRepository : JpaRepository<RolePrivilege, RolePrivilegeId>

@Repository
interface EntregaRepository : JpaRepository<Entrega, Int> {
    // Ejemplo de método adicional para buscar entregas en camino
    fun findByEstado(estado: String): List<Entrega>
}

@Repository
interface UbicacionRepository : JpaRepository<Ubicacion, Int> {
    fun findByEntregaEntregaIdOrderByTimestampDesc(entregaId: Int): List<Ubicacion>
}
