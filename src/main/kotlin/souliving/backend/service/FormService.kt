package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.model.form.Form

interface FormService {

    fun getAllForms(): Flow<Form>?

    suspend fun getFormByUserId(id: Long): Form?

    suspend fun getFormByShortFormId(id: Long): Form?

}