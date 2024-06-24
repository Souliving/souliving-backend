package souliving.backend.dto

import souliving.backend.model.NoArg

@NoArg
data class FilterDto(
    val cityId: List<Long>,
    val metroIds: List<Long>,
    val smoking: Boolean?,
    val alcohol: Boolean?,
    val petFriendly: Boolean?,
    val isClean: Boolean?
)

data class AgeFilterDto(
    val startAge: Long,
    val endAge: Long,
)

data class PriceFilterDto(
    val startPrice: Long,
    val endPrice: Long,
)
