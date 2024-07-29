package souliving.backend.dto

import java.time.LocalDateTime

data class CreateFormDto(
    var userId: Long? = null,
    var description: String,
    var homeTypesIds: List<Long>? = null,
    var rating: Double,
    var reviews: List<String>,
    var photoId: Long? = null,
    var properties: CreatePropertiesDto? = null,
    var cityId: Long? = null,
    var metroIds: List<Long>? = null,
    var budget: Long,
    var dateMove: LocalDateTime,
    var onlineDateTime: LocalDateTime
)
