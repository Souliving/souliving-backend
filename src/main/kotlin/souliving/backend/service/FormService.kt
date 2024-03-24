package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.dto.FormDto
import souliving.backend.dto.ShortFormDto

interface FormService {

    fun getAllForms(): Flow<FormDto>

    suspend fun getFormByUserId(id: Long): FormDto?

    suspend fun getShortForms(): Flow<ShortFormDto>
}
