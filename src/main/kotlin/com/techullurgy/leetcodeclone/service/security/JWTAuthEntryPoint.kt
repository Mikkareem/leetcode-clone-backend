package com.techullurgy.leetcodeclone.service.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JWTAuthEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        when (authException) {
            is BadCredentialsException -> {
                response?.sendError(HttpServletResponse.SC_FORBIDDEN)
            }

            else -> {

            }
        }
    }
}