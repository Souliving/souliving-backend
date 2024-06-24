package souliving.backend.dto

data class PlainFormDto(
    var id: Long? = null,
    var hometypeid: Long? = null,
    var hometypename: String? = null,
    var socialMedia: Long? = null,
    var rating: Double? = null,
    var reviews: String? = null
)
