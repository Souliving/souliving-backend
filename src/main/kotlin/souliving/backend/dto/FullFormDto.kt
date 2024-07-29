package souliving.backend.dto

import souliving.backend.model.Properties
import souliving.backend.model.home.HomeType
import souliving.backend.model.locations.City
import souliving.backend.model.locations.Metro
import java.time.LocalDateTime

data class FullFormDto(
    var id: Long? = null,
    var userId: Long? = null,
    var name: String,
    val age: Int,
    var city: City? = null,
    var metro: List<Metro>,
    var budget: Long,
    var description: String,
    var dateMove: LocalDateTime?,
    var properties: Properties? = null,
    var photoId: Long? = null,
    var onlineDateTime: LocalDateTime?,
    var homeType: List<HomeType>? = null,
    var socialMediaIds: List<Long>? = null,
    var rating: Double,
    var reviews: List<String>
)
