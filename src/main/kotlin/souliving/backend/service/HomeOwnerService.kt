package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.home.HomeOwner
import souliving.backend.repository.HomeOwnerRepository

@Service
class HomeOwnerService(
    private val homeOwnerRepository: HomeOwnerRepository
) {

    private var homeOwners = flowOf(
        HomeOwner(0, 0, "Уютная студия", 0),
        HomeOwner(1, 1, "Аккуратная однокомнатная квартира", 1),
        HomeOwner(2, 2, "Просторная двушка", 2)
    )

    fun getAllHomeOwners(): Flow<HomeOwner>? = homeOwnerRepository.findAll()

    suspend fun getHomeOwnerById(id: Long): HomeOwner? =
        homeOwnerRepository.findById(id)

    suspend fun getHomeOwnersByHomeId(homeTypeId: Long): Flow<HomeOwner>? =
        homeOwnerRepository.findHomeOwnersByHomeTypeId(homeTypeId)

}
