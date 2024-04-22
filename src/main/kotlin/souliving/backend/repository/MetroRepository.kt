package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import souliving.backend.model.locations.Metro

interface MetroRepository : CoroutineCrudRepository<Metro, Long> {
    suspend fun findAllByCityId(cityId: Long): Flow<Metro>?
    suspend fun findMetroByName(name: String): Metro
}
