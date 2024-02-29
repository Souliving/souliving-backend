package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.model.locations.District

interface DistrictService {
    fun getDistricts(): Flow<District>

    suspend fun getAllDistrictByCityId(cityId: Long): Flow<District>?

    suspend fun getDistrictById(id: Long): District?
}
