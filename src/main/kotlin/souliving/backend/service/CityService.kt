package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.model.locations.City

interface CityService {
    fun getCities(): Flow<City>

    suspend fun getCitiesById(id: Long): City?
}
