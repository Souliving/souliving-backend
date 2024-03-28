package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Repository
import souliving.backend.model.Form
import java.time.LocalDateTime

@Repository
class FakeFormRepository {

    var forms = flowOf(
        Form(
            0,
            1,
            "Хочу крутую квартиру",
            0,
            listOf(0),
            4.5,
            listOf("Он крутой"),
            1,
            0,
            0,
            0,
            0,
            50000,
            LocalDateTime.now(),
            LocalDateTime.now()
        ),
        Form(
            1,
            2,
            "Хочу новую квартиру",
            1,
            listOf(1),
            5.0,
            listOf("Онa крутая"),
            2,
            1,
            1,
            1,
            1,
            30000,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
    )

    fun getAllForms(): Flow<Form> {
        return forms
    }

    suspend fun getFormByUserId(id: Long): Form? =
        forms.filter { it.userId == id }.firstOrNull()

//    suspend fun getFormByShortFormId(shortId: Long): Form? =
//        forms.filter { it.shortFormId == shortId }.firstOrNull()
}
