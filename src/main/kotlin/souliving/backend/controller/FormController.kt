package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.FormDto
import souliving.backend.dto.ShortFormDto
import souliving.backend.logger.logResponse
import souliving.backend.model.Properties
import souliving.backend.service.FormService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/form")
class FormController(private var formService: FormService) {

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

    @GetMapping("getShortForms")
    suspend fun getShortForms(): Flow<ShortFormDto> =
        formService.getShortForms().let {
            logResponse("Get all short forms")
            it
        }

    @GetMapping("/getShortFormsByProperties")
    suspend fun getShortFormsByProperties(@RequestBody properties: Properties): Flow<ShortFormDto> =
        formService.getShortFormsByProperties(properties).let {
            logResponse("Get all short forms by properties : $properties")
            it
        }

}
