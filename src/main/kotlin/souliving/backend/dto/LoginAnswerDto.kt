package souliving.backend.dto

data class LoginAnswerDto(
    val jwt: JwtDTO,
    val email: String,
    val name: String
)
