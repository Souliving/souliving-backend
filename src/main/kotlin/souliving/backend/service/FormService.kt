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

    fun getAllForms(): Flow<FormDto> = formRepository.findAll().map { it.toDto() }

    suspend fun getFormByUserId(id: Long): FormDto? =
        formRepository.findByUserId(id)?.toDto()

    suspend fun getShortForms(): Flow<ShortFormDto> =
        formRepository.findAll().map { it.toShortDto() }

//    override suspend fun getFormByShortFormId(id: Long): FormDto? =
//        formRepository.getFormByShortFormId(id)?.toDto()

    suspend fun getShortFormsByProperties(properties: Properties): Flow<ShortFormDto> {
        return formRepository.findAll().map { it.toShortDto() }
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

    suspend fun Form.toShortDto(): ShortFormDto = fetchDataForShortFormDto(this)

    private suspend fun fetchDataForShortFormDto(form: Form): ShortFormDto = withContext(Dispatchers.IO) {
        val city = async { cityService.getCityById(form.cityId!!) }
        val properties = async { propertiesService.getPropertiesById(form.propertiesId!!) }
        val metro = async { metroService.getMetroById(form.metroId) }
        val district = async { districtService.getDistrictById(form.districtId!!) }
        val user = async { userService.findById(form.userId!!) }.await()!!
        ShortFormDto(
            form.id,
            user.name!!,
            user.age,
            city.await(),
            district.await(),
            metro.await(),
            form.budget,
            form.description,
            form.dateMove,
            properties.await(),
            form.photoId,
            form.onlineDateTime
        )
    }

}
