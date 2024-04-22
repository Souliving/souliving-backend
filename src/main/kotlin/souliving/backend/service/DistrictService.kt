package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import souliving.backend.model.locations.District
import souliving.backend.repository.DistrictRepository

@Service
class DistrictService(
    private val districtRepository: DistrictRepository,
) {
    fun getDistricts(): Flow<District> = districtRepository.findAll()

    suspend fun getAllDistrictByCityId(cityId: Long): Flow<District>? =
        districtRepository.findAllDistrictsByCityId(cityId)

    suspend fun getDistrictById(id: Long): District? =
        districtRepository.findById(id)
}
