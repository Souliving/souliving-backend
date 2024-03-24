package souliving.backend.dto

import souliving.backend.model.Gender

data class CreateUserDto(
    val email: String,
    var password: String,
    val name: String,
    val age: Int,
    val gender: Gender,
    val phone: String
)
