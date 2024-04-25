package com.techullurgy.leetcodeclone.service.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Service
class JWTService {

    @Value("\${app.jwt.expirationTime}")
    private lateinit var expirationTime: String

    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        return generateToken(username)
    }

    fun getUsernameFromToken(token: String): String {
        val claims = getClaims(token)

        return claims?.subject ?: ""
    }

    private fun getClaims(token: String): Claims? {
        val claims: Claims

        try {
            claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            return null
        } catch (e: MalformedJwtException) {
            return null
        }

        return claims
    }

    fun isTokenExpired(token: String): Boolean {
        val claims = getClaims(token)
        return claims?.expiration?.before(Date()) ?: true
    }

    private fun generateToken(username: String): String {
        val currentDate = Date(Instant.now().toEpochMilli())

        val expirationIn = expirationTime.toLong()

//        val expirationDate = Date(currentDate.time + (3*60*60*1000))
        val expirationDate = Date(currentDate.time + expirationIn)

        return Jwts.builder()
            .subject(username)
            .issuer("self")
            .issuedAt(currentDate)
            .expiration(expirationDate)
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact()
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    companion object {
        private const val SECRET_KEY = "SECRETKEYFORLEETCODECLONESECRETKEYFORLEETCODECLONE"
    }
}