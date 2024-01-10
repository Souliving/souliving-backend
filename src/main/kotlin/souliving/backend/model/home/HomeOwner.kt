package souliving.backend.model.home

data class HomeOwner(
    var id: Long? = null,
    var homeTypeId: Long? = null,
    var description: String,
    var photoId: Long? = null,
)
