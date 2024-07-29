package souliving.backend.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.lang.Nullable

@Table("properties")
data class Properties(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    var smoking: Boolean,
    var alcohol: Boolean,
    var petFriendly: Boolean,
    var isClean: Boolean,
    var homeOwnerId: Long? = null
)
