package souliving.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id
    var id: Long? = null,
    val email: String,
    val password: String,
    val role: UserRole,
    val name: String? = null,
    val enabled: Boolean,
    val createDate: LocalDateTime,
    val modifyDate: LocalDateTime
)

enum class UserRole {
    USER, ADMIN
}


