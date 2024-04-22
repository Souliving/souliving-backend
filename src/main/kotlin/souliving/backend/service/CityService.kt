package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import souliving.backend.model.locations.City
import souliving.backend.repository.CityRepository

@Service
class CityService(
    private val cityRepository: CityRepository
) {
    fun getCities(): Flow<City> = cityRepository.findAll()

    suspend fun getCityById(id: Long): City? =
        cityRepository.findById(id)
}
