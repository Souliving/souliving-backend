package souliving.backend.dto

data class CreateHomeOwnerDto(
    val metroId: Long? = null,
    val cityId: Long? = null,
    val homeTypeId: Long? = null,
    val description: String,
    val photoId: Long? = null,
)
