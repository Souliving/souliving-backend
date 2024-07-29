package souliving.backend.mapper

import souliving.backend.dto.CreateHomeOwnerDto
import souliving.backend.dto.CreatePropertiesDto
import souliving.backend.model.Properties
import souliving.backend.model.home.HomeOwner

fun CreatePropertiesDto.toProperties(): Properties {

    return Properties(
        smoking = this.smoking,
        alcohol = this.alcohol,
        petFriendly = this.petFriendly,
        isClean = this.isClean,
        homeOwnerId = this.homeOwnerId ?: 0,
    )
}

fun CreateHomeOwnerDto.toHomeOwner(): HomeOwner =
    HomeOwner(
        metroId = this.metroId,
        cityId = this.cityId,
        homeTypeId = this.homeTypeId,
        description = this.description,
        photoId = this.photoId
    )
