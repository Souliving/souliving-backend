package souliving.backend.service.fake

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
import souliving.backend.repository.FakeFormRepository
import souliving.backend.service.FormService
import souliving.backend.service.UserService

@Service
class FakeFormService(
    @Autowired
    private val formRepository: FakeFormRepository,
    @Autowired
    private val homeTypeService: FakeHomeTypeService,
    @Autowired
    private val cityService: FakeCityService,
    @Autowired
    private val propertiesService: FakePropertiesService,
    @Autowired
    private val metroService: FakeMetroService,
    @Autowired
    private val districtService: FakeDistrictService,
    @Autowired
    private val userService: UserService,
) : FormService {

    override fun getAllForms(): Flow<FormDto> = formRepository.getAllForms().map { it.toDto() }

    override suspend fun getFormByUserId(id: Long): FormDto? =
        formRepository.getFormByUserId(id)?.toDto()

    override suspend fun getShortForms(): Flow<ShortFormDto> =
        formRepository.getAllForms().map { it.toShortDto() }

//    override suspend fun getFormByShortFormId(id: Long): FormDto? =
//        formRepository.getFormByShortFormId(id)?.toDto()

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
        val city = async { cityService.getCitiesById(form.cityId!!) }
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



