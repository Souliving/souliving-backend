package souliving.backend.service.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.home.HomeType
import souliving.backend.service.HomeTypeService

@Service
class FakeHomeTypeService : HomeTypeService {

    private var homeTypes = flowOf(
        HomeType(0, "студия"),
        HomeType(1, "однокомнатная"),
        HomeType(2, "двухкомантная")
    )

    override fun getAllHomeTypes(): Flow<HomeType>? = homeTypes

    override suspend fun getHomeTypeById(id: Long): HomeType? =
        homeTypes.filter { it.id == id }.firstOrNull()

}
