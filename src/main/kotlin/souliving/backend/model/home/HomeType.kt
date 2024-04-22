package souliving.backend.model.home

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("home_type")
data class HomeType(
    @Id
    var id: Long? = null,
    var name: String,
)
