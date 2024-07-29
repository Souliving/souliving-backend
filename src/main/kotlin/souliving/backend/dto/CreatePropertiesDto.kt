package souliving.backend.dto

data class CreatePropertiesDto(
    val smoking: Boolean,
    val alcohol: Boolean,
    val petFriendly: Boolean,
    val isClean: Boolean,
    val homeOwnerId: Long? = null
)
