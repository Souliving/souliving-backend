package souliving.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    var id: Long? = null,
    var email: String,
    var password: String,
    var name: String,
    var surname: String,
)
