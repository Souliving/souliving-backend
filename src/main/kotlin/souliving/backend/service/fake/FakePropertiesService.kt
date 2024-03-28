package souliving.backend.service.fake

import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import souliving.backend.model.Properties
import souliving.backend.service.PropertiesService

@Service
class FakePropertiesService : PropertiesService {

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

    override suspend fun getPropertiesById(id: Long): Properties? =
        properties.filter { it.id == id }.firstOrNull()

    override suspend fun createProperties(prop: Properties): Properties? {
        val mutProp = properties.toList().toMutableList()
        mutProp.add(prop)
        properties = mutProp.asFlow()
        return prop
    }
}
