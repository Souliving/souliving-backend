package souliving.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("properties")
data class Properties(
    @Id
    var id: Long? = null,
    var smoking: Boolean,
    var alcohol: Boolean,
    var petFriendly: Boolean,
    var isClean: Boolean,
    var homeOwnerId: Int
)
