package com.example.bite_api

import com.example.bite_api.entities.RolePrivilegeId
import java.sql.Timestamp

// DTO para Usuario
data class UsuarioDTO(
    val userId: Int?,
    val nombre: String,
    val email: String,
    val direccion: String,
    val telefono: String,
    val preferencias: String?,
    val alergias: String?,
    val fechaCreacion: Timestamp?,
    val ultimoLogin: Timestamp?,
    val password: String,
    val roleName: String?,
    val latitud: Double?,
    val longitud: Double?
)

// DTO para Restaurante
data class RestauranteDTO(
    val restaurantId: Int?,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val categoria: String,
    val horarioAtencion: String,
    val descripcion: String?,
    val imagenUrl: String?,
    val adminId: Int?,
    val latitud: Double?,
    val longitud: Double?
)

// DTO para Categoria
data class CategoriaDTO(
    val categoriaId: Int?,
    val nombre: String,
    val imagenUrl: String?
)

// DTO para Producto
data class ProductoDTO(
    val productoId: Int?,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val ingredientes: String?,
    val disponibilidad: Boolean,
    val categoriaId: Int?,
    val restaurantId: Int?,
    val imagenUrl: String?
)

// DTO para Transaccion
data class TransaccionDTO(
    val transaccionId: Int?,
    val fechaTransaccion: Timestamp?,
    val estado: String,
    val total: Double,
    val userId: Int?,
    val restaurantId: Int?
)

// DTO para DetalleTransaccion
data class DetalleTransaccionDTO(
    val detalleId: Int?,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double,
    val transaccionId: Int?,
    val productoId: Int?
)

// DTO para Valoracion
data class ValoracionDTO(
    val valoracionId: Int?,
    val calificacion: Int,
    val comentario: String?,
    val fechaReseña: Timestamp?,
    val userId: Int?,
    val productoId: Int?
)

// DTO para PreferenciaIA
data class PreferenciaIADTO(
    val preferenciaId: Int?,
    val historialBusqueda: String?,
    val gustosIdentificados: String?,
    val alergiasIdentificadas: String?,
    val userId: Int?
)

// DTO para Role
data class RoleDTO(
    val id: Long?,
    val name: String
)

// DTO para Privilege
data class PrivilegeDTO(
    val id: Long?,
    val name: String
)

// DTO para UserRole
data class UserRoleDTO(
    var userId: Int? = null,
    var roleId: Long? = null,
    var userName: String? = null,
    var roleName: String? = null
)


// DTO para RolePrivilege
data class RolePrivilegeDTO(
    var id: RolePrivilegeId? = null,
    var roleName: String? = null,
    var privilegeName: String? = null
)

data class EntregaDTO(
    val entregaId: Int?,
    val transaccionId: Int?,
    val repartidorId: Int?,
    val estado: String,
    val fechaCreacion: Timestamp,
    val fechaEntregaEstimada: Timestamp?
)

data class UbicacionDTO(
    val ubicacionId: Int?,
    val entregaId: Int?,
    val latitud: Double,
    val longitud: Double,
    val timestamp: Timestamp
)

