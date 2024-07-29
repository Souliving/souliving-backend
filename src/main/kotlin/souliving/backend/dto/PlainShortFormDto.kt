package souliving.backend.dto

import java.time.LocalDateTime

data class PlainShortFormDto(
    var id: Long? = null,
    var name: String,
    val age: Int,
    var cityid: Long? = null,
    var cityname: String? = null,
    var metro: String,
    var budget: Long,
    var description: String,
    var datemove: LocalDateTime?,
    var propertiesid: Long? = null,
    var smoking: Boolean,
    var alcohol: Boolean,
    var petfriendly: Boolean?,
    var isclean: Boolean?,
    var homeownerid: Long? = null,
    var photoid: Long? = null,
    var onlinedatetime: LocalDateTime?,
)
