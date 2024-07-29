package souliving.backend.service

import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.dto.CreatePropertiesDto
import souliving.backend.mapper.toProperties
import souliving.backend.model.Properties
import souliving.backend.repository.PropertiesRepository

@Service
class PropertiesService(
    private val propertiesRepository: PropertiesRepository
) {

    suspend fun getPropertiesById(id: Long): Properties? =
        propertiesRepository.findById(id)

    suspend fun createProperties(propDto: CreatePropertiesDto): Long? {
        return propertiesRepository.save(propDto.toProperties()).id
    }
}
