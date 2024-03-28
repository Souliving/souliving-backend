package souliving.backend.model

data class Properties(
    var id: Long? = null,
    var smoking: Boolean,
    var alcohol: Boolean,
    var petFriendly: Boolean,
    var isClean: Boolean,
    var homeOwnerId: Int
)
