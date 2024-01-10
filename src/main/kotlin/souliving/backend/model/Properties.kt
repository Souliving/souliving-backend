package souliving.backend.model

data class Properties(
    var id: Long? = null,
    var smoking: Int,
    var alcohol: Int,
    var petFriendly: Int,
    var isClean: Int,
    var homeOwnerId: Int
)
