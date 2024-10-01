package souliving.backend.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import souliving.backend.dto.*
import souliving.backend.model.Form
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
    metro = parseMetro(this.metro),
    budget = this.budget,
    description = this.description,
    dateMove = this.datemove,
    properties = Properties(
        this.propertiesid, this.smoking, this.alcohol, this.petfriendly!!, this.isclean!!, this.homeownerid
    ),
    photoId = this.photoid,
    onlineDateTime = this.onlinedatetime,
    isFavorite = this.isfavorite ?: false,
)

fun PlainFullFormDto.toFullForm(): FullFormDto = FullFormDto(
    id = this.id,
    userId = this.userid,
    name = this.name,
    age = this.age,
    city = City(this.cityid, this.cityname!!),
    metro = parseMetro(this.metro),
    budget = this.budget,
    description = this.description,
    dateMove = this.datemove,
    properties = Properties(
        this.propertiesid, this.smoking, this.alcohol, this.petfriendly!!, this.isclean!!, this.homeownerid
    ),
    photoId = this.photoid,
    onlineDateTime = this.onlinedatetime,
    homeType = parseHomeTypes(this.hometype!!),
    rating = this.rating,
    reviews = listOf(this.reviews)
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
        homeType = parseHomeTypes(this.hometype!!),
        rating = this.rating!!,
        reviews = listOf(this.reviews!!)
    )
}

fun parseHomeTypes(homeTypesJson: String): List<HomeType> {
    val model: List<Map<String, String>> = mapper.readValue(homeTypesJson)
    val homeTypes = mutableListOf<HomeType>()
    model.forEach { homeType ->
        homeTypes.add(HomeType(homeType["id"]?.toLong(), homeType["name"]!!))
    }
    return homeTypes
}
