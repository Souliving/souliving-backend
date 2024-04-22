package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import souliving.backend.model.locations.Metro
import souliving.backend.repository.MetroRepository

@Service
class MetroService(
    private val metroRepository: MetroRepository
) {
    fun getMetros(): Flow<Metro> = metroRepository.findAll()

    suspend fun getAllMetroByCityId(cityId: Long): Flow<Metro>? =
        metroRepository.findAllByCityId(cityId)

    suspend fun getMetroByName(name: String): Metro? =
        metroRepository.findMetroByName(name)

    suspend fun getMetroById(id: Long?): Metro? =
        metroRepository.findById(id!!)
}
