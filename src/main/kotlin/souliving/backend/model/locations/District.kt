package souliving.backend.model.locations

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("district")
data class District(
    @Id
    var id: Long? = null,
    var name: String,
    var cityId: Long? = null,
)
