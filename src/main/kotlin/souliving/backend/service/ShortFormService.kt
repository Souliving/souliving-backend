package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import souliving.backend.model.form.ShortForm

interface ShortFormService {

    fun getAllShortForm(): Flow<ShortForm>?

    suspend fun getShortFormByFormId(id: Long): ShortForm?

    suspend fun getShortFormByUserId(id: Long?): ShortForm?

}
