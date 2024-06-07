package souliving.backend.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    val email: String,
    val password: String,
    val role: UserRole,
    val name: String? = null,
    val age: Int,
    val gender: Gender? = null,
    val enabled: Boolean,
    val createDate: LocalDateTime,
    val modifyDate: LocalDateTime
)

enum class UserRole {
    USER, ADMIN
}

enum class Gender {
    MALE, FEMALE
}

