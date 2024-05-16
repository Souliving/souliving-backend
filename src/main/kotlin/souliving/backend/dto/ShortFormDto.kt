package souliving.backend.dto

import souliving.backend.model.Properties
import souliving.backend.model.locations.City
import souliving.backend.model.locations.District
import souliving.backend.model.locations.Metro
import java.time.LocalDateTime

data class ShortFormDto(
    var id: Long? = null,
    var name: String,
    val age: Int,
    var city: City? = null,
    var district: District? = null,
    var metro: List<Metro>,
    var budget: Long,
    var description: String,
    var dateMove: LocalDateTime?,
    var properties: Properties? = null,
    var photoId: Long? = null,
    var onlineDateTime: LocalDateTime?,
)
