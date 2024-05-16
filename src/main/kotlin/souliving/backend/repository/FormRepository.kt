package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import souliving.backend.dto.PlainFormDto
import souliving.backend.dto.PlainShortFormDto
import souliving.backend.model.Form

@Repository
interface FormRepository : CoroutineCrudRepository<Form, Long> {

    @Query("select * from get_forms_by_user_id(:userId)")
    suspend fun findByUserId(userId: Long): PlainFormDto?

//    @Procedure(name = "GET_SHORT_FORMS")
//    fun findShort(): Flow<PlainShortFormDto>

    @Query(
        "select * from get_short_forms()"
    )
    fun getShortForms(): Flow<PlainShortFormDto>

    @Query("select * from get_forms()")
    fun getForms(): Flow<PlainFormDto>
}
