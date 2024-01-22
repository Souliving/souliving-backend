package souliving.backend.dto

import souliving.backend.model.UserRole
import java.time.LocalDateTime

data class UserDto(
    var id: Long? = null,
    val email: String,
    val password: String,
    val role: UserRole,
    val name: String,
    val enabled: Boolean,
    val createDate: LocalDateTime,
    val modifyDate: LocalDateTime
)
