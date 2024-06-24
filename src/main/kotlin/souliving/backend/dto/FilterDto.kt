package souliving.backend.dto

import souliving.backend.model.NoArg

@NoArg
data class FilterDto(
    val price: PriceFilterDto,
    val age: AgeFilterDto,
    val cityId: List<Long>,
    val metroIds: List<Long>,
    val smoking: Boolean?,
    val alcohol: Boolean?,
    val petFriendly: Boolean?,
    val isClean: Boolean?
)

data class AgeFilterDto(
    val startAge: Int? = null,
    val endAge: Int? = null,
)

data class PriceFilterDto(
    val startPrice: Long? = null,
    val endPrice: Long? = null
)
