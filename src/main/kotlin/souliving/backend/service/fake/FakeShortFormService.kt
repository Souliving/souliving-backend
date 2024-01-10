package souliving.backend.service.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.form.ShortForm
import souliving.backend.service.ShortFormService
import java.time.LocalDateTime

@Service
class FakeShortFormService : ShortFormService {

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

    override fun getAllShortForm(): Flow<ShortForm>? = shortForms

    override suspend fun getShortFormByFormId(id: Long): ShortForm? =
        shortForms.filter { it.formId == id }.firstOrNull()

    override suspend fun getShortFormByUserId(id: Long?): ShortForm? =
        shortForms.filter { it.userId == id }.firstOrNull()

}
