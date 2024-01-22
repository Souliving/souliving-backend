package souliving.backend.jwt


import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import souliving.backend.service.UserService

@Component
class JwtAuthenticationManager(val userService: UserService) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> = mono {
        val principal = authentication.principal as CustomPrincipal
        val user = userService.findByEmail(principal.email)
        if (!user?.enabled!!) {
            throw DisabledException("User ${user.id} is disabled")
        }
        return@mono authentication
    }
}
