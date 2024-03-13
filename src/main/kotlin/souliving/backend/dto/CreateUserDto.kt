package souliving.backend.dto

import souliving.backend.model.Gender
import java.time.LocalDate

data class CreateUserDto(
    val email: String,
    var password: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val phone: String
)
