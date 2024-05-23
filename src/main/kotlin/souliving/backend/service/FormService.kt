package souliving.backend.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import souliving.backend.dto.FormDto
import souliving.backend.dto.MetroFormDto
import souliving.backend.dto.PlainShortFormDto
import souliving.backend.dto.ShortFormDto
import souliving.backend.mapper.toFormDto
import souliving.backend.mapper.toShortForm
import souliving.backend.model.Form
import souliving.backend.model.Properties
import souliving.backend.repository.FormRepository
import java.time.LocalDateTime


@Service
class FormService(
    private val formRepository: FormRepository,
    @Autowired
    private val homeTypeService: HomeTypeService,
    @Autowired
    private val cityService: CityService,
    @Autowired
    private val propertiesService: PropertiesService,
    @Autowired
    private val metroService: MetroService,
    @Autowired
    private val districtService: DistrictService,
    @Autowired
    private val userService: UserService,
    @Autowired
    private val databaseClient: DatabaseClient,
) {

    fun getAllForms(): Flow<FormDto> = formRepository.getForms().map { it.toFormDto() }

    suspend fun getFormByUserId(id: Long): FormDto? =
        formRepository.findByUserId(id)?.toFormDto()

    suspend fun getShortForms(): Flow<ShortFormDto> =
        formRepository.getShortForms().map { it.toShortForm() }

//    override suspend fun getFormByShortFormId(id: Long): FormDto? =
//        formRepository.getFormByShortFormId(id)?.toDto()

    suspend fun getShortFormsByProperties(properties: Properties): Flow<ShortFormDto> {
        return formRepository.getShortForms().map { it.toShortForm() }
    }

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
    suspend fun addFavoriteForm(mainFormId: Long, favFormId: Long): Long {
        databaseClient.sql("INSERT INTO favorite_forms(main_form_id, fav_form_id) VALUES (:mainFormId, :favFormId)")
            .bind("mainFormId", mainFormId).bind("favFormId", favFormId)
            .fetch().awaitRowsUpdated()
        return mainFormId
    }

    @Transactional
    suspend fun deleteFavoriteForm(mainFormId: Long, favFormId: Long): Boolean {
        return databaseClient.sql("DELETE FROM favorite_forms WHERE main_form_id = :mainFormId and fav_form_id = :favFormId")
            .bind("mainFormId", mainFormId).bind("favFormId", favFormId).fetch().awaitRowsUpdated() != 0L
    }

    suspend fun getAllFavoriteFormByFormId(formId: Long): List<ShortFormDto> {
        val result =
            databaseClient.sql("select * from get_fav_forms_by_form_id(:formId)").bind("formId", formId).fetch().flow()
                .toList()
        val favForms = result.map {
            it.parseToShortDto()
        }
        return favForms.map { it.toShortForm() }

    }


    suspend fun Form.toDto(): FormDto = fetchDataForFormDto(this)

    private suspend fun fetchDataForFormDto(form: Form): FormDto = withContext(Dispatchers.IO) {

        val homeType = async { homeTypeService.getHomeTypeById(form.homeTypeId!!) }

        FormDto(
            form.id,
            form.userId,
            form.description,
            homeType.await(),
            form.socialMediaListId,
            form.rating,
            form.reviews
        )
    }

    suspend fun Map<String, Any>.parseToShortDto(): PlainShortFormDto {
        return PlainShortFormDto(
            id = this["id"] as Long,
            name = this["name"] as String,
            age = (this["age"] as Long).toInt(),
            cityid = this["cityid"] as Long,
            cityname = this["cityname"] as String,
            districtid = this["districtid"] as Long,
            districtname = this["districtname"] as String,
            districtcityid = this["districtcityid"] as Long,
            metro = this["metro"] as String,
            budget = this["budget"] as Long,
            description = this["description"] as String,
            datemove = this["datemove"] as LocalDateTime,
            propertiesid = this["propertiesid"] as Long,
            smoking = this["smoking"] as Boolean,
            alcohol = this["alcohol"] as Boolean,
            petfriendly = this["petfriendly"] as Boolean,
            isclean = this["isclean"] as Boolean,
            homeownerid = (this["homeownerid"] as Long).toInt(),
            photoid = this["photoid"] as Long,
            onlinedatetime = this["onlinedatetime"] as LocalDateTime
        )
    }

}
