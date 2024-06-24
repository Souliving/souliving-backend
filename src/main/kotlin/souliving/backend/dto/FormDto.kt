package souliving.backend.dto

import souliving.backend.model.home.HomeType

data class FormDto(
    var id: Long? = null,
    var homeType: HomeType? = null,
    var socialMediaIds: List<Long>? = null,
    var rating: Double,
    var reviews: List<String>
)
