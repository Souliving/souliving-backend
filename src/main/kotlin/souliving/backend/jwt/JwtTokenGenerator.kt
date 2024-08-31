package souliving.backend.jwt


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import souliving.backend.dto.SuccessRefreshDto
import souliving.backend.dto.UserDto
import java.util.*

@Component
class JwtTokenGenerator(val jwtProperties: JwtProperties) {

    suspend fun generateToken(user: UserDto): JwtTokenDetails {
        val issueDate = Date()
        val expirationDate = Date(issueDate.time + jwtProperties.expirationTime.toMillis())
        val refreshExpirationDate = Date(issueDate.time + jwtProperties.refreshExpirationTime.toMillis())
        val claims = mapOf("email" to user.email, "role" to user.role)
        return generateToken(user.id!!, user.email, user.name, issueDate, expirationDate, refreshExpirationDate, claims)
    }

    suspend fun generateTokenByClaims(claims: Claims): SuccessRefreshDto {
        val issueDate = Date()
        val expirationDate = Date(issueDate.time + jwtProperties.expirationTime.toMillis())
        val refreshExpirationDate = Date(issueDate.time + jwtProperties.refreshExpirationTime.toMillis())
        val token = Jwts.builder()
            .addClaims(claims)
            .setIssuer(jwtProperties.issuer)
            .setSubject(claims.subject)
            .setIssuedAt(issueDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(jwtProperties.secret.toByteArray()))
            .compact()

        val refreshToken = Jwts.builder()
            .addClaims(claims)
            .setIssuer(jwtProperties.issuer)
            .setSubject(claims.subject)
            .setIssuedAt(issueDate)
            .setExpiration(refreshExpirationDate)
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(jwtProperties.secret.toByteArray()))
            .compact()
        return SuccessRefreshDto(token, refreshToken)
    }

    private suspend fun generateToken(
        userId: Long,
        email: String,
        name: String,
        issueDate: Date,
        expirationDate: Date,
        refreshExpiration: Date,
        claims: Map<String, Any>
    ): JwtTokenDetails {
        val token = Jwts.builder()
            .addClaims(claims)
            .setIssuer(jwtProperties.issuer)
            .setSubject(userId.toString())
            .setIssuedAt(issueDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(jwtProperties.secret.toByteArray()))
            .compact()

        val refreshToken = Jwts.builder()
            .addClaims(claims)
            .setIssuer(jwtProperties.issuer)
            .setSubject(userId.toString())
            .setIssuedAt(issueDate)
            .setExpiration(refreshExpiration)
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(jwtProperties.secret.toByteArray()))
            .compact()
        return JwtTokenDetails(userId, email, name, token, refreshToken, issueDate, expirationDate);
    }
}
