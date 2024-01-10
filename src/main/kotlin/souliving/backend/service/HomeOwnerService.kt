package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.model.home.HomeOwner

interface HomeOwnerService {

    fun getAllHomeOwners(): Flow<HomeOwner>?

    suspend fun getHomeOwnerById(id: Long): HomeOwner?

    suspend fun getHomeOwnersByHomeId(homeTypeId: Long): Flow<HomeOwner>?
}