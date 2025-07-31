package com.example.bite_api.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Component
class JwtFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println("JwtFilter: interceptando la solicitud...")


        if (request.requestURI.contains("/api/v1/login")) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwtToken = authHeader.substring(7)
            if (JwtUtils.validateToken(jwtToken)) {
                val username = JwtUtils.getUsernameFromToken(jwtToken)
                if (username != null) {
                    val authorities = listOf(SimpleGrantedAuthority("USER"))
                    val authToken = JwtAuthenticationToken(username, null, authorities)
                    authToken.isAuthenticated = true
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
