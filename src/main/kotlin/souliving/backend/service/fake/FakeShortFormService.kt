package souliving.backend.service.fake

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import souliving.backend.dto.ShortFormDto
import souliving.backend.model.form.ShortForm
import souliving.backend.repository.FakeShortFormRepository
import souliving.backend.service.ShortFormService

@Service
class FakeShortFormService(
    @Autowired
    private val shortFormRepository: FakeShortFormRepository,
    @Autowired
    private val propertiesService: FakePropertiesService,
    @Autowired
    private val cityService: FakeCityService,
    @Autowired
    private val districtService: FakeDistrictService,
    @Autowired
    private val metroService: FakeMetroService
) : ShortFormService {

    override fun getAllShortForm(): Flow<ShortFormDto> = shortFormRepository.getAllShortForm()!!.map { it.toDto() }

    override suspend fun getShortFormByFormId(id: Long): ShortFormDto? =
        shortFormRepository.getShortFormByFormId(id)!!.toDto()

    override suspend fun getShortFormByUserId(id: Long?): ShortFormDto? =
        shortFormRepository.getShortFormByUserId(id)!!.toDto()


    suspend fun ShortForm.toDto(): ShortFormDto = fetchDataForFormDto(this)

    private suspend fun fetchDataForFormDto(shortForm: ShortForm): ShortFormDto = withContext(Dispatchers.IO) {

        val properties = async { propertiesService.getPropertiesById(shortForm.propertiesId!!) }

        val city = async { cityService.getCitiesById(shortForm.cityId!!) }

        val district = async { districtService.getDistrictById(shortForm.districtId!!) }

        val metro = async { metroService.getMetroById(shortForm.metroId) }

        ShortFormDto(
            shortForm.id,
            shortForm.formId,
            shortForm.userId,
            shortForm.photoId,
            properties.await(),
            city.await(),
            district.await(),
            metro.await(),
            shortForm.onlineDate,
            shortForm.budget,
            shortForm.dateMove
        )
    }
}
