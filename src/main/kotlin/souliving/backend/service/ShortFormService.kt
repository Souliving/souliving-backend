package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.dto.ShortFormDto

interface ShortFormService {

    fun getAllShortForm(): Flow<ShortFormDto>?

    suspend fun getShortFormByFormId(id: Long): ShortFormDto?

    suspend fun getShortFormByUserId(id: Long?): ShortFormDto?

}
