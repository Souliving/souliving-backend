package souliving.backend.model.locations

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("metro")
data class Metro(
    @Id
    var id: Long? = null,
    var name: String,
    var cityId: Long? = null,
)
