package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Repository
import souliving.backend.model.form.Form

@Repository
class FakeFormRepository {

    var forms = flowOf(
        Form(
            0, 0, 0, "Это я Жора",
            0, listOf(0), 4.5, listOf("Хороший человек")
        ),
        Form(
            1, 1, 1, "Это Маша",
            1, listOf(1), 5.0, listOf("Прекрасный человек")
        )
    )

    fun getAllForms(): Flow<Form> {
        return forms
    }

    suspend fun getFormByUserId(id: Long): Form? =
        forms.filter { it.userId == id }.firstOrNull()

    suspend fun getFormByShortFormId(shortId: Long): Form? =
        forms.filter { it.shortFormId == shortId }.firstOrNull()
}
