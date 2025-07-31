package com.example.bite_api.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey

object JwtUtils {
    private val SECRET_KEY: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private const val EXPIRATION_MS = 86400000 // 1 día en milisegundos

    fun generateToken(username: String, roleName: String?, userId: Int): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("roleName", roleName)  // Añade el roleName como claim
            .claim("userId", userId)      // Añade el userId como claim
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_MS))
            .signWith(SECRET_KEY)
            .compact()
    }


    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String? {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).body.subject
    }

    fun getRoleNameFromToken(token: String): String? {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).body.get("roleName", String::class.java)
    }
}

