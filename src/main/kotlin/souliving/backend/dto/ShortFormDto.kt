package souliving.backend.dto

import souliving.backend.model.Properties
import souliving.backend.model.locations.City
import souliving.backend.model.locations.District
import souliving.backend.model.locations.Metro
import java.time.LocalDateTime

data class ShortFormDto(
    var id: Long? = null,
    var formId: Long? = null,
    var userId: Long? = null,
    var photoId: Long? = null,
    var properties: Properties? = null,
    var city: City? = null,
    var district: District? = null,
    var metro: Metro? = null,
    var onlineDate: LocalDateTime,
    var budget: Long,
    var dateMove: LocalDateTime
)
