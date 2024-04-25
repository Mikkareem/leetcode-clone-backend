package com.techullurgy.leetcodeclone.controllers.rest

import com.techullurgy.leetcodeclone.service.security.JWTService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("http://localhost:5173/")
class AuthenticationController(
    private val authManager: AuthenticationManager,
    private val jwtService: JWTService
) {
    @PostMapping("auth/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {

        println("${loginRequest.username} ${loginRequest.password}")
        val authentication: Authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )

        val token = jwtService.generateToken(authentication)

        return ResponseEntity.ok(LoginResponse(token = token))
    }

    @GetMapping("/code/dd")
    fun getRes(auth: Authentication): ResponseEntity<String> {
        println("Accessed User: ${auth.name}")
        return ResponseEntity.ok("Good morning, Welcome to Leetcode Clone Project of Irsath Kareem")
    }
}

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String?,
    val isSuccess: Boolean = true,
    val error: String? = null
)