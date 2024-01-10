package souliving.backend.model.form

import java.time.LocalDateTime


data class ShortForm(
    var id: Long? = null,
    var formId: Long? = null,
    var userId: Long? = null,
    var photoId: Long? = null,
    var propertiesId: Long? = null,
    var cityId: Long? = null,
    var districtId: Long? = null,
    var metroId: Long? = null,
    var onlineDate: LocalDateTime,
    var budget: Long,
    var dateMove: LocalDateTime
)
