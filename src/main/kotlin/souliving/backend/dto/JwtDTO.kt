package souliving.backend.dto

import java.util.*

data class JwtDTO(
    val userId: Long,
    val token: String,
    val refreshToken: String,
    val issueDate: Date,
    val expirationDate: Date
)
