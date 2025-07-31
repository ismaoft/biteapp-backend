package com.example.bite_api.entities

import jakarta.persistence.* // Importar las anotaciones de JPA para Kotlin
import java.io.Serializable
import java.sql.Timestamp

@Entity
@Table(name = "Usuarios")
class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Int? = null

    var nombre: String = ""
    var email: String = ""
    var password: String = ""
    var direccion: String = ""
    var telefono: String = ""
    var preferencias: String? = null
    var alergias: String? = null

    var latitud: Double? = null // Coordenadas del restaurante
    var longitud: Double? = null

    @Column(name = "fecha_creacion")
    var fechaCreacion: Timestamp? = null

    @Column(name = "ultimo_login")
    var ultimoLogin: Timestamp? = null

    var tokenFCM: String? = null

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var transacciones: Set<Transaccion> = emptySet()

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var valoraciones: Set<Valoracion> = emptySet()

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var preferenciasIA: Set<PreferenciaIA> = emptySet()

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var userRoles: Set<UserRole> = emptySet()

    @OneToMany(mappedBy = "administrador", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var restaurantesAdministrados: Set<Restaurante> = emptySet()

    constructor()
}


@Entity
@Table(name = "Restaurantes")
class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var restaurantId: Int? = null

    var nombre: String = ""
    var direccion: String = ""
    var telefono: String = ""
    var categoria: String = ""
    var horarioAtencion: String = ""
    var descripcion: String? = null
    var imagenUrl: String? = null

    var latitud: Double? = null // Coordenadas del restaurante
    var longitud: Double? = null

    @ManyToOne
    @JoinColumn(name = "admin_id")  // Define la clave foránea en la tabla Restaurantes
    var administrador: Usuario? = null  // Campo para referenciar al administrador (Usuario)

    @OneToMany(mappedBy = "restaurante", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var productos: Set<Producto> = emptySet()

    @OneToMany(mappedBy = "restaurante", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var transacciones: Set<Transaccion> = emptySet()

    constructor()
}

@Entity
@Table(name = "Entregas")
class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var entregaId: Int? = null

    @ManyToOne
    @JoinColumn(name = "transaccion_id", nullable = false)
    var transaccion: Transaccion? = null

    @ManyToOne
    @JoinColumn(name = "repartidor_id", nullable = false)
    var repartidor: Usuario? = null  // En este caso, es el dueño del restaurante.

    var estado: String = Estado.PENDIENTE

    @Column(name = "fecha_creacion")
    var fechaCreacion: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name = "fecha_entrega_estimada")
    var fechaEntregaEstimada: Timestamp? = null

    companion object Estado {
        const val PENDIENTE = "pendiente"
        const val PREPARACION = "en preparación"
        const val CAMINO = "en camino"
        const val ENTREGADO = "entregado"
    }
}


@Entity
@Table(name = "Ubicaciones")
class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ubicacionId: Int? = null

    @ManyToOne
    @JoinColumn(name = "entrega_id")
    var entrega: Entrega? = null

    var latitud: Double = 0.0
    var longitud: Double = 0.0

    @Column(name = "timestamp")
    var timestamp: Timestamp = Timestamp(System.currentTimeMillis())
}




@Entity
@Table(name = "Categorias")
class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoriaId: Int? = null
    var nombre: String = ""
    var imagenUrl: String? = null

    constructor()
}

@Entity
@Table(name = "Productos")
class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var productoId: Int? = null

    var nombre: String = ""
    var descripcion: String? = null
    var precio: Double = 0.0
    var ingredientes: String? = null
    var disponibilidad: Boolean = false
    var imagenUrl: String? = null

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    var categoria: Categoria? = null

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    var restaurante: Restaurante? = null

    @OneToMany(mappedBy = "producto", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var detalles: Set<DetalleTransaccion> = emptySet()

    @OneToMany(mappedBy = "producto", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var valoraciones: Set<Valoracion> = emptySet()

    constructor()
}



@Entity
@Table(name = "Transacciones")
class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var transaccionId: Int? = null

    var fechaTransaccion: Timestamp? = null
    var estado: String = ""
    var total: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "user_id")
    var usuario: Usuario? = null

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    var restaurante: Restaurante? = null

    constructor()
}



@Entity
@Table(name = "Detalles_Transaccion")
class DetalleTransaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var detalleId: Int? = null

    var cantidad: Int = 0
    var precioUnitario: Double = 0.0
    var subtotal: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "transaccion_id")
    var transaccion: Transaccion? = null

    @ManyToOne
    @JoinColumn(name = "producto_id")
    var producto: Producto? = null

    constructor()
}


@Entity
@Table(name = "Valoraciones")
class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var valoracionId: Int? = null

    var calificacion: Int = 0
    var comentario: String? = null
    var fechaResena: Timestamp? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var usuario: Usuario? = null

    @ManyToOne
    @JoinColumn(name = "producto_id")
    var producto: Producto? = null

    constructor()
}


@Entity
@Table(name = "Preferencias_IA")
class PreferenciaIA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var preferenciaId: Int? = null

    var historialBusqueda: String? = null
    var gustosIdentificados: String? = null
    var alergiasIdentificadas: String? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var usuario: Usuario? = null

    constructor()
}


@Entity
@Table(name = "role")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = ""

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var userRoles: Set<UserRole> = emptySet()

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var rolePrivileges: Set<RolePrivilege> = emptySet()

    constructor()
}



@Entity
@Table(name = "privilege")
class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = ""

    @OneToMany(mappedBy = "privilege", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var rolePrivileges: Set<RolePrivilege> = emptySet()

    constructor()
}

@Embeddable
class UserRoleId(
    var userId: Int? = null,
    var roleId: Long? = null
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as UserRoleId
        return userId == other.userId && roleId == other.roleId
    }

    override fun hashCode(): Int {
        return userId.hashCode() + roleId.hashCode()
    }
}



@Entity
@Table(name = "user_role")
class UserRole(
    @EmbeddedId
    var id: UserRoleId? = null,

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    var usuario: Usuario? = null,

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    var role: Role? = null
) {
    constructor() : this(null)
}


@Embeddable
class RolePrivilegeId(
    var roleId: Long? = null,
    var privilegeId: Long? = null
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as RolePrivilegeId
        return roleId == other.roleId && privilegeId == other.privilegeId
    }

    override fun hashCode(): Int {
        return roleId.hashCode() + privilegeId.hashCode()
    }
}

@Entity
@Table(name = "role_privilege")
class RolePrivilege(
    @EmbeddedId
    var id: RolePrivilegeId? = null,

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    var role: Role? = null,

    @ManyToOne
    @MapsId("privilegeId")
    @JoinColumn(name = "privilege_id")
    var privilege: Privilege? = null
) {
    constructor() : this(null)
}

