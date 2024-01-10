package souliving.backend.service.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.locations.City
import souliving.backend.service.CityService

@Service
class FakeCityService : CityService {
    private val city: Flow<City> = flowOf(
        City(0, "Moscow"),
        City(1, "Saint-Petersburg")
    )

    override fun getCities(): Flow<City> = city

    override suspend fun getCitiesById(id: Long): City? =
        city.filter { it.id == id }.firstOrNull()
}
