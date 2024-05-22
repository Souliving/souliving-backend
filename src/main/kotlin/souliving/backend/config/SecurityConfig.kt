package souliving.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.WebFluxConfigurer
import reactor.core.publisher.Mono
import souliving.backend.jwt.JwtAuthenticationManager
import souliving.backend.jwt.JwtTokenAuthenticationConverter
import souliving.backend.model.UserRole
import java.security.SecureRandom

@Configuration
class SecurityConfig : WebFluxConfigurer {

    val whiteList =
        arrayOf("/api/v1/**", "/**")

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        jwtAuthFilter: AuthenticationWebFilter
    ): SecurityWebFilterChain {
        return http
            .cors {
                it.configurationSource(addCors())
            }
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers(HttpMethod.GET, *whiteList).permitAll()
                    .pathMatchers(HttpMethod.POST, *whiteList).permitAll()
                    .pathMatchers(HttpMethod.PUT, *whiteList).permitAll()
                    .pathMatchers("api/v1/users/**").hasAuthority(UserRole.ADMIN.toString())
                    .anyExchange().authenticated()
            }
            .exceptionHandling {
                it
                    .authenticationEntryPoint { exchange, _ ->
                        return@authenticationEntryPoint Mono.fromRunnable {
                            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                        }
                    }
            }
            .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    @Bean
    fun addCors(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("*")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun jwtAuthFilter(
        authenticationManager: JwtAuthenticationManager,
        converter: JwtTokenAuthenticationConverter
    ): AuthenticationWebFilter {
        return AuthenticationWebFilter(authenticationManager).apply { setServerAuthenticationConverter(converter) }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10, SecureRandom())
    }
}
