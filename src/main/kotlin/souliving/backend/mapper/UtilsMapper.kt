package souliving.backend.mapper

import souliving.backend.dto.CreatePropertiesDto
import souliving.backend.model.Properties

fun CreatePropertiesDto.toProperties(): Properties {

   return Properties(
        smoking = this.smoking,
        alcohol = this.alcohol,
        petFriendly = this.petFriendly,
        isClean = this.isClean,
        homeOwnerId = this.homeOwnerId ?: 0,
    )
}
