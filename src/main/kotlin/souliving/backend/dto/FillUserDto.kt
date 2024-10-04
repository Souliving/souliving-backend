package souliving.backend.dto

import souliving.backend.model.Gender

data class FillUserDto(
    val name: String,
    val email: String,
    val age: Int,
    val gender: Gender
)
