package souliving.backend.model.locations

data class District(
    var id: Long? = null,
    var name: String,
    var cityId: Long? = null,
)
