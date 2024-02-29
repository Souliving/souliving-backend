package souliving.backend.dto

import souliving.backend.model.home.HomeType

data class FormDto(
    var id: Long? = null,
    var userId: Long? = null,
    var shortFormId: Long? = null,
    var description: String,
    var homeType: HomeType? = null,
    var socialMediaIds: List<Long>? = null,
    var rating: Double,
    var reviews: List<String>
)