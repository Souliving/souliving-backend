package souliving.backend.service.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.home.HomeOwner
import souliving.backend.service.HomeOwnerService

@Service
class FakeHomeOwnerService : HomeOwnerService {

    private var homeOwners = flowOf(
        HomeOwner(0, 0, "Уютная студия", 0),
        HomeOwner(1, 1, "Аккуратная однокомнатная квартира", 1),
        HomeOwner(2, 2, "Просторная двушка", 2)
    )

    override fun getAllHomeOwners(): Flow<HomeOwner>? =
        homeOwners

    override suspend fun getHomeOwnerById(id: Long): HomeOwner? =
        homeOwners.filter { it.id == id }.firstOrNull()

    override suspend fun getHomeOwnersByHomeId(homeTypeId: Long): Flow<HomeOwner>? =
        homeOwners.filter { it.homeTypeId == homeTypeId }

}
