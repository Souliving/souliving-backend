package souliving.backend.jwt

import java.util.*

data class JwtTokenDetails(
    val userId: Long,
    val token: String,
    val issueDate: Date,
    val expirationDate: Date,
)
