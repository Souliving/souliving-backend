package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.model.home.HomeType

interface HomeTypeService {
    fun getAllHomeTypes(): Flow<HomeType>?

    suspend fun getHomeTypeById(id: Long): HomeType?
}