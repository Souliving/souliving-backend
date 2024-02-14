package souliving.backend.jwt

import java.util.*

data class JwtTokenDetails(
    val userId: Long,
    val email: String,
    val name: String,
    val token: String,
    val issueDate: Date,
    val expirationDate: Date,
)
