package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.dto.FormDto

interface FormService {

    fun getAllForms(): Flow<FormDto>

    suspend fun getFormByUserId(id: Long): FormDto?

    suspend fun getFormByShortFormId(id: Long): FormDto?

}
