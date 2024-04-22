package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.home.HomeType
import souliving.backend.repository.HomeTypeRepository

@Service
class HomeTypeService(
    private val homeTypeRepository: HomeTypeRepository
) {

    private var homeTypes = flowOf(
        HomeType(0, "студия"),
        HomeType(1, "однокомнатная"),
        HomeType(2, "двухкомантная")
    )

    fun getAllHomeTypes(): Flow<HomeType>? = homeTypeRepository.findAll()

    suspend fun getHomeTypeById(id: Long): HomeType? = homeTypeRepository.findById(id)
}
