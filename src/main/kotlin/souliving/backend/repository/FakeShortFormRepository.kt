package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Repository
import souliving.backend.model.form.ShortForm
import java.time.LocalDateTime

@Repository
class FakeShortFormRepository {
    private var shortForms = flowOf(
        ShortForm(
            0, 0, 0, 0, 0, 0, 0, 0,
            LocalDateTime.now(), 1000, LocalDateTime.now()
        ),
        ShortForm(
            1, 1, 1, 1, 1, 1, 1, 0,
            LocalDateTime.now(), 50000, LocalDateTime.now()
        )
    )

    fun getAllShortForm(): Flow<ShortForm>? = shortForms

    suspend fun getShortFormByFormId(id: Long): ShortForm? =
        shortForms.filter { it.formId == id }.firstOrNull()

    suspend fun getShortFormByUserId(id: Long?): ShortForm? =
        shortForms.filter { it.userId == id }.firstOrNull()

}
