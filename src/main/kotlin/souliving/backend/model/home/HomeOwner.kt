package souliving.backend.model.home

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("home_owner")
data class HomeOwner(
    @Id
    var id: Long? = null,
    var homeTypeId: Long? = null,
    var description: String,
    var photoId: Long? = null,
)
