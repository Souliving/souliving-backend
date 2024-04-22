package souliving.backend.service

import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.Properties
import souliving.backend.repository.PropertiesRepository

@Service
class PropertiesService(
    private val propertiesRepository: PropertiesRepository
) {

    private var properties = flowOf(
        Properties(
            0,
            smoking = false,
            alcohol = false,
            petFriendly = true,
            isClean = true,
            homeOwnerId = 0
        ),
        Properties(
            1,
            smoking = true,
            alcohol = true,
            petFriendly = false,
            isClean = false,
            homeOwnerId = 1
        )
    )

    suspend fun getPropertiesById(id: Long): Properties? =
        propertiesRepository.findById(id)

    suspend fun createProperties(prop: Properties): Properties? {
        return propertiesRepository.save(prop)
    }
}
