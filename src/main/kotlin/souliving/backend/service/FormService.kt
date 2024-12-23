package souliving.backend.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import souliving.backend.dto.*
import souliving.backend.mapper.toFormDto
import souliving.backend.mapper.toFullForm
import souliving.backend.mapper.toShortForm
import souliving.backend.model.Form
import souliving.backend.model.Properties
import souliving.backend.repository.FormRepository
import java.time.LocalDateTime


@Service
class FormService(
    private val formRepository: FormRepository,
    @Autowired
    private val databaseClient: DatabaseClient,
    @Autowired
    private val propertiesService: PropertiesService
) {

    fun getAllForms(): Flow<FormDto> = formRepository.getForms().map { it.toFormDto() }

    suspend fun getFormByUserId(id: Long): FormDto? =
        formRepository.findPlainDtoByUserId(id)?.toFormDto()

    suspend fun getFormByFormId(id: Long): FormDto? =
        formRepository.findPlainDtoByFormId(id)?.toFormDto()

    suspend fun getShortForms(): Flow<ShortFormDto> =
        formRepository.getShortForms().map { it.toShortForm() }

    suspend fun getShortFormsForUserId(userId: Long): List<ShortFormDto> {
        val result =
            databaseClient.sql("select * from get_short_forms_for_user_id(:userId)").bind("userId", userId).fetch()
                .flow().toList()
        val forms = result.map { it.parseToShortDto().toShortForm() }
        return forms
    }
//    override suspend fun getFormByShortFormId(id: Long): FormDto? =
//        formRepository.getFormByShortFormId(id)?.toDto()

    suspend fun getShortFormsByProperties(properties: Properties): Flow<ShortFormDto> {
        return formRepository.getShortForms().map { it.toShortForm() }
    }

    suspend fun getShortFormsWithFilter(userId: Long, filter: FilterDto): List<ShortFormDto> {
        val result = buildFilterRequest(userId, filter)
            .fetch().flow().toList()

        val filteredForms = result.map {
            it.parseToShortDto()
        }
        return filteredForms.map { it.toShortForm() }
    }

    suspend fun getFullFormById(id: Long): FullFormDto? =
        formRepository.findPlainFullDtoById(id)?.toFullForm()

    @Transactional
    suspend fun updateMetrosInForm(mfDto: MetroFormDto): Boolean {
        databaseClient.sql("call DELETE_METRO(:formId)").bind("formId", mfDto.formId!!).fetch().awaitRowsUpdated()

        mfDto.metroIds!!.forEach { metroId ->
            databaseClient.sql("INSERT INTO form_metro_ids(form_id, metro_id) VALUES (:formId, :metroId)")
                .bind("formId", mfDto.formId!!).bind("metroId", metroId).fetch().awaitRowsUpdated()
        }
        return true
    }

    @Transactional
    suspend fun updateHomeTypesInForm(homeTypes: HomeTypeFormDto) {
        homeTypes.homeTypeIds.forEach { homeTypeId ->
            databaseClient.sql("INSERT INTO home_type_form(form_id, home_type_id) VALUES (:formId, :homeTypeId)")
                .bind("formId", homeTypes.formId).bind("homeTypeId", homeTypeId).fetch().awaitRowsUpdated()
        }
    }

    @Transactional
    suspend fun addFavoriteForm(userId: Long, favFormId: Long): Long {
        databaseClient.sql("INSERT INTO favorite_forms(user_id, fav_form_id) VALUES (:user_id, :favFormId)")
            .bind("user_id", userId).bind("favFormId", favFormId)
            .fetch().awaitRowsUpdated()
        return userId
    }

    @Transactional
    suspend fun deleteFavoriteForm(userId: Long, favFormId: Long): Boolean {
        return databaseClient.sql("DELETE FROM favorite_forms WHERE user_id = :user_id and fav_form_id = :favFormId")
            .bind("user_id", userId).bind("favFormId", favFormId).fetch().awaitRowsUpdated() != 0L
    }

    suspend fun getAllFavoriteFormByUserId(userId: Long): List<ShortFormDto> {
        val result =
            databaseClient.sql("select * from get_fav_forms_by_user_id(:userId)").bind("userId", userId).fetch().flow()
                .toList()
        val favForms = result.map {
            it.parseToShortDto()
        }
        return favForms.map { it.toShortForm() }

    }

    @Transactional
    suspend fun createFormForUserById(createForm: CreateFormDto): ShortFormDto {
        val newForm = formRepository.save(createForm.toForm())

        updateMetrosInForm(MetroFormDto(newForm.id, createForm.metroIds))
        updateHomeTypesInForm(HomeTypeFormDto(newForm.id!!, createForm.homeTypesIds!!))

        return formRepository.getShortFormsByFormId(newForm.id!!).toShortForm()
    }

    suspend fun Map<String, Any>.parseToShortDto(): PlainShortFormDto {
        return PlainShortFormDto(
            id = this["id"] as Long,
            name = this["name"] as String,
            age = (this["age"] as Long).toInt(),
            cityid = this["cityid"] as Long,
            cityname = this["cityname"] as String,
            metro = this["metro"] as String,
            budget = this["budget"] as Long,
            description = this["description"] as String,
            datemove = this["datemove"] as LocalDateTime,
            propertiesid = this["propertiesid"] as Long,
            smoking = this["smoking"] as Boolean,
            alcohol = this["alcohol"] as Boolean,
            petfriendly = this["petfriendly"] as Boolean,
            isclean = this["isclean"] as Boolean,
            homeownerid = (this["homeownerid"] as Long),
            photoid = this["photoid"] as Long,
            onlinedatetime = this["onlinedatetime"] as LocalDateTime,
            isfavorite = this["isfavorite"] as Boolean,
        )
    }


    private suspend fun CreateFormDto.toForm(): Form {
        return Form(
            userId = this.userId,
            description = this.description,
            rating = this.rating,
            reviews = this.reviews,
            photoId = this.photoId,
            propertiesId = propertiesService.createProperties(this.properties!!),
            cityId = this.cityId,
            budget = this.budget,
            dateMove = this.dateMove,
            onlineDateTime = this.onlineDateTime
        )
    }

    private fun buildFilterRequest(userId: Long, filter: FilterDto): DatabaseClient.GenericExecuteSpec {
        val sqlString = "select * from" +
            " get_short_forms_with_filter" +
            "(${userId.buildSqlParameter()}," +
            "${filter.price.startPrice?.buildSqlParameter()},${filter.price.endPrice?.buildSqlParameter()}," +
            "${filter.age.startAge?.buildSqlParameter()}, ${filter.age.endAge?.buildSqlParameter()}," +
            "${filter.cityId.buildSqlParameter()}, ${filter.metroIds.buildSqlParameter()}," +
            " ${filter.smoking.buildSqlParameter()}, ${filter.alcohol.buildSqlParameter()}," +
            " ${filter.petFriendly.buildSqlParameter()}, ${filter.isClean.buildSqlParameter()})"

        val sql = databaseClient.sql(
            sqlString
        )
        return sql
    }

    private fun Number.buildSqlParameter(): String {
        if (this == null) {
            return "null"
        }
        return when (this) {
            is Long -> "$this::bigint"
            is Int -> "$this::int"
            else -> "$this::int"
        }
    }

    private fun List<Number>.buildSqlParameter(): String {
        if (this.isEmpty()) {
            return "null"
        }
        var result = "array["
        this.forEach {
            result += when (it) {
                is Int -> "$it::int,"
                is Long -> "$it::bigint,"
                else -> "$it::int,"
            }
        }
        result = result.substring(0, result.length - 1)
        result = result.plus("]")
        return result
    }

    private fun Boolean?.buildSqlParameter(): String {
        if (this == null)
            return "null"
        return this.toString()
    }
}
