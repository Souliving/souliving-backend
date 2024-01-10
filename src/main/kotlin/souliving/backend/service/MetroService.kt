package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.model.locations.Metro

interface MetroService {
    fun getMetros(): Flow<Metro>

    suspend fun getAllMetroByCityId(cityId: Long): Flow<Metro>?

    suspend fun getMetroByName(name: String): Metro?
}
