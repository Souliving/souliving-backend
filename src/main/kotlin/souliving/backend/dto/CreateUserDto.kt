package souliving.backend.dto

data class CreateUserDto(
    val email: String,
    var password: String
)
