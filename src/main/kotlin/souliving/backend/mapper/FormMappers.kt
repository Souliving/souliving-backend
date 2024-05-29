package souliving.backend.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import souliving.backend.dto.FormDto
import souliving.backend.dto.PlainFormDto
import souliving.backend.dto.PlainShortFormDto
import souliving.backend.dto.ShortFormDto
import souliving.backend.model.Properties
import souliving.backend.model.home.HomeType
import souliving.backend.model.locations.City
import souliving.backend.model.locations.District
import souliving.backend.model.locations.Metro

var mapper: ObjectMapper = jacksonObjectMapper()
fun PlainShortFormDto.toShortForm(): ShortFormDto = ShortFormDto(
    id = this.id,
    name = this.name,
    age = this.age,
    city = City(this.cityid, this.cityname!!),
    district = District(this.districtid, this.districtname!!, this.districtcityid),
    metro = parseMetro(this.metro),
    budget = this.budget,
    description = this.description,
    dateMove = this.datemove,
    properties = Properties(
        this.propertiesid, this.smoking, this.alcohol, this.petfriendly!!, this.isclean!!, this.homeownerid
    ),
    photoId = this.photoid,
    onlineDateTime = this.onlinedatetime
)

fun parseMetro(metroJson: String): List<Metro> {
    val model: List<Map<String, String>> = mapper.readValue(metroJson)
    val metroList = mutableListOf<Metro>()
    model.forEach { metro ->
        metroList.add(Metro(metro["id"]?.toLong(), metro["name"].toString(), metro["cityId"]?.toLong()))
    }
    return metroList
}

fun PlainFormDto.toFormDto(): FormDto {
    return FormDto(
        id = this.id,
        userId = this.userid,
        description = this.description!!,
        homeType = HomeType(this.hometypeid!!, this.hometypename!!),
        rating = this.rating!!,
        reviews = listOf(this.reviews!!)
    )
}
