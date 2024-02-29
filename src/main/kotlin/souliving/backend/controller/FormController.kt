package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.FormDto
import souliving.backend.logger.logResponse
import souliving.backend.service.fake.FakeFormService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/form")
class FormController(private var formService: FakeFormService) {

    @GetMapping("/")
    fun getAllForms(): Flow<FormDto> =
        formService.getAllForms().let {
            logResponse("Get all forms")
            it
        }

    @GetMapping("/getFormByUserId/{userId}")
    suspend fun getFormByUserId(@PathVariable userId: Long): FormDto =
        formService.getFormByUserId(userId)?.let {
            logResponse("Get form by User id : $userId")
            it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find form by user id: $userId"
        )

    @GetMapping("/getFormByShortFormId/{shortFormId}")
    suspend fun getFormByShortFormId(@PathVariable shortFormId: Long): FormDto =
        formService.getFormByShortFormId(shortFormId)?.let {
            logResponse("Get form by Short Form Id : $shortFormId")
            it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find form by Short Form Id: $shortFormId"
        )
}
