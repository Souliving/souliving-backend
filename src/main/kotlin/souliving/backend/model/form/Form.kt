package souliving.backend.model.form

data class Form(
    var id: Long? = null,
    var userId: Long? = null,
    var shortFormId: Long? = null,
    var description: String,
    var homeTypeId: Long? = null,
    var socialMediaId: Long? = null,
    var rating: Double,
    var reviews: List<String>
)
