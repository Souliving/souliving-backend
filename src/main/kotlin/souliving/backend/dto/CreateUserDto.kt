package souliving.backend.dto

data class CreateUserDto(
    val email: String,
    val name: String,
    var password: String
)
