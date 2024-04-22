package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import souliving.backend.model.home.HomeOwner

@Repository
interface HomeOwnerRepository : CoroutineCrudRepository<HomeOwner, Long> {
    suspend fun findHomeOwnersByHomeTypeId(homeTypeId: Long): Flow<HomeOwner>?
}
