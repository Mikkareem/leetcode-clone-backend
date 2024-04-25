package com.techullurgy.leetcodeclone.service.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authEntryPoint: JWTAuthEntryPoint,
    private val jwtAuthFilter: JWTAuthenticationFilter,
    private val logoutHandler: LogoutHandler
) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        // replace with customerUserDetailsService
        return InMemoryUserDetailsManager(
            User.withUsername("irsath")
                .password("irsath@123").authorities("ROLE_ADMIN")
                .passwordEncoder {
                    passwordEncoder().encode(it)
                }
                .build(),
            User.withUsername("kiruba")
                .password("kiruba@123").authorities("ROLE_USER")
                .passwordEncoder {
                    passwordEncoder().encode(it)
                }
                .build(),
            User
                .withUsername("ganesh")
                .password("ganesh@123").authorities("ROLE_USER")
                .passwordEncoder {
                    passwordEncoder().encode(it)
                }
                .build(),
            User.withUsername("rahul")
                .password("rahul@123").authorities("ROLE_USER")
                .passwordEncoder {
                    passwordEncoder().encode(it)
                }
                .build(),
        )
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val daoAuthProvider = DaoAuthenticationProvider()
        daoAuthProvider.setUserDetailsService(userDetailsService())
        daoAuthProvider.setPasswordEncoder(passwordEncoder())
        return daoAuthProvider
    }

    @Bean
    fun jwtFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .exceptionHandling { exc ->
                exc.authenticationEntryPoint(authEntryPoint)
            }
            .authorizeHttpRequests { auth ->
                // Request Matchers Order matters.
                auth
                    .requestMatchers("/", "/auth/**").permitAll()
//                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/code/").hasRole("ADMIN")
                    .requestMatchers("/message/rest").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .requestMatchers("/api/**").hasRole("USER")
                    .anyRequest().authenticated()
            }
//            .httpBasic(Customizer.withDefaults())
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .logout {
                it.logoutUrl("/logout")
                it.addLogoutHandler(logoutHandler)
                it.logoutSuccessHandler { req, res, auth ->
                    SecurityContextHolder.clearContext()
                }
            }
            .build()
    }

    @Bean
    fun authenticationManager(authConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}