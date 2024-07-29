package souliving.backend.model.home

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("home_owner")
data class HomeOwner(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    @Column("metro_id")
    var metroId: Long? = null,
    @Column("city_id")
    val cityId: Long? = null,
    @Column("home_type_id")
    var homeTypeId: Long? = null,
    var description: String,
    @Column("photo_id")
    var photoId: Long? = null,
)
