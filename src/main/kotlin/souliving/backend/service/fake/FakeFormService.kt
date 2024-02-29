package souliving.backend.service.fake

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import souliving.backend.dto.FormDto
import souliving.backend.model.form.Form
import souliving.backend.repository.FakeFormRepository
import souliving.backend.service.FormService

@Service
class FakeFormService(
    @Autowired
    private val formRepository: FakeFormRepository,
    @Autowired
    private val homeTypeService: FakeHomeTypeService
) : FormService {

    override fun getAllForms(): Flow<FormDto> = formRepository.getAllForms().map { it.toDto() }

    override suspend fun getFormByUserId(id: Long): FormDto? =
        formRepository.getFormByUserId(id)?.toDto()

    override suspend fun getFormByShortFormId(id: Long): FormDto? =
        formRepository.getFormByShortFormId(id)?.toDto()

    suspend fun Form.toDto(): FormDto = fetchDataForFormDto(this)

    private suspend fun fetchDataForFormDto(form: Form): FormDto = withContext(Dispatchers.IO) {

        val homeType = async { homeTypeService.getHomeTypeById(form.homeTypeId!!) }

        FormDto(
            form.id,
            form.userId,
            form.shortFormId,
            form.description,
            homeType.await(),
            form.socialMediaListId,
            form.rating,
            form.reviews
        )
    }

}



