package souliving.backend.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import souliving.backend.dto.FormDto
import souliving.backend.dto.ShortFormDto
import souliving.backend.mapper.toFormDto
import souliving.backend.mapper.toShortForm
import souliving.backend.model.Form
import souliving.backend.model.Properties
import souliving.backend.repository.FormRepository


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

}
