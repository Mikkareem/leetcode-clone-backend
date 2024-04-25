package com.techullurgy.leetcodeclone.service.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JWTAuthenticationFilter(
    private val jwtService: JWTService,
    private val customerUserDetailsService: CustomerUserDetailsService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Ignore whitelisted urls from Authentication
        if (request.servletPath.contains("/auth/")) {
            filterChain.doFilter(request, response)
            return
        }
        println(request.servletPath)
        val token = getJWTFromRequest(request)
        println("JWT Filter called with token $token")

        token?.let {
            val username = jwtService.getUsernameFromToken(it)

            if (jwtService.isTokenExpired(it)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                return
            }

            val userDetails: UserDetails? = customerUserDetailsService.loadUserByUsername(username)
            val userCredential = BCryptPasswordEncoder().encode(username).take(10)
            val usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(username, userCredential, userDetails?.authorities)

            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        } ?: run {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            return
        }
        filterChain.doFilter(request, response)
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader("Authorization")
        return bearerToken?.let {
            if (bearerToken.startsWith("Bearer "))
                bearerToken.substring(7)
            else null
        }
    }
}