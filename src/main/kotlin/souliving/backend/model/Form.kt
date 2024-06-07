package souliving.backend.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("form")
data class Form(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    var userId: Long? = null,
    var description: String,
    var homeTypeId: Long? = null,
    var socialMediaListId: List<Long>? = null,
    var rating: Double,
    var reviews: List<String>,
    var photoId: Long? = null,
    var propertiesId: Long? = null,
    var cityId: Long? = null,
    var districtId: Long? = null,
    var metroId: Long? = null,
    var budget: Long,
    var dateMove: LocalDateTime,
    var onlineDateTime: LocalDateTime
)
