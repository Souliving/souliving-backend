package souliving.backend.dto

data class SuccessRefreshDto(
    val token: String,
    val refreshToken: String,
)
