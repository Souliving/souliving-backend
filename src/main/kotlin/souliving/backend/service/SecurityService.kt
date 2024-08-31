package souliving.backend.service


import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import souliving.backend.dto.SuccessRefreshDto
import souliving.backend.jwt.JwtTokenDetails
import souliving.backend.jwt.JwtTokenGenerator
import souliving.backend.jwt.JwtTokenVerifier

@Service
class SecurityService(
    val userService: UserService,
    val passwordEncoder: PasswordEncoder,
    val jwtTokenGenerator: JwtTokenGenerator,
    val jwtTokenVerifier: JwtTokenVerifier
) {

    suspend fun authenticate(email: String, password: String): JwtTokenDetails {
        val user = userService.findByEmail(email)

        if (!user!!.enabled) {
            throw DisabledException("User ${user.id} is disabled")
        }

        if (!passwordEncoder.matches(password, user.password)) {
            throw BadCredentialsException("Password doesn't match")
        }

        return jwtTokenGenerator.generateToken(user)
    }

    suspend fun refresh(refreshToken: String): SuccessRefreshDto {
        val res = jwtTokenVerifier.verify(refreshToken)
        return jwtTokenGenerator.generateTokenByClaims(res.claims)
    }
}
