package com.techullurgy.leetcodeclone.service.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service

@Service
class LogoutService : LogoutHandler {

    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val authHeader = request?.getHeader("Authorization")

        authHeader?.let {
            if (!it.startsWith("Bearer ")) return

            // val jwt = it.substring(7)
            // val storedToken = tokenRepository.findByToken(jwt)
            // if(storedToken != null) {
            // expire the token
            // revoke the token
            // SecurityContextHolder.clearContext()
            // }

            SecurityContextHolder.clearContext()
        }
    }
}