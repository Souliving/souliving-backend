package souliving.backend.service.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.form.Form
import souliving.backend.service.FormService

@Service
class FakeFormService : FormService {

    var forms = flowOf(
        Form(
            0, 0, 0, "Это я Жора",
            0, 0, 4.5, listOf("Хороший человек")
        ),
        Form(
            1, 1, 1, "Это Маша",
            1, 1, 5.0, listOf("Прекрасный человек")
        )
    )

    override fun getAllForms(): Flow<Form>? = forms

    override suspend fun getFormByUserId(id: Long): Form? =
        forms.filter { it.userId == id }.firstOrNull()

    override suspend fun getFormByShortFormId(id: Long): Form? =
        forms.filter { it.shortFormId == id }.firstOrNull()
}