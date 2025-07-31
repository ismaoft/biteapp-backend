package com.example.bite_api.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
    private val principal: String,
    private val credentials: String?,
    authorities: List<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {

    override fun getCredentials(): Any? = credentials
    override fun getPrincipal(): Any = principal
}
