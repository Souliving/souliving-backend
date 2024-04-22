package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import souliving.backend.model.locations.District

@Repository
interface DistrictRepository : CoroutineCrudRepository<District, Long> {
    suspend fun findAllDistrictsByCityId(cityId: Long): Flow<District>
}
