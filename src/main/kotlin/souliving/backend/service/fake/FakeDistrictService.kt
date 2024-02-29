package souliving.backend.service.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.locations.District
import souliving.backend.service.DistrictService

@Service
class FakeDistrictService : DistrictService {
    private val districts =
        flowOf(
            District(0, "Приморский", 1),
            District(1, "Адмиралтейский", 1),
            District(2, "Центральный", 1),
            District(3, "Красногорск", 0),
        )

    override fun getDistricts(): Flow<District> = districts

    override suspend fun getAllDistrictByCityId(cityId: Long): Flow<District>? =
        districts.filter { it.cityId == cityId }

    override suspend fun getDistrictById(id: Long): District? =
        districts.filter { it.id == id }.firstOrNull()
}
