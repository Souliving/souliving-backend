package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.*
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

    @GetMapping("/getFormById/{formId}")
    suspend fun getFormById(@PathVariable formId: Long): FormDto =
        formService.getFormByFormId(formId)?.let {
            logResponse("Get form by user id : $formId")
            it
        } ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find form by form id: $formId")

    @PostMapping("/createFormForUserById/{userId}")
    suspend fun createFormForUserById(
        @PathVariable userId: Long,
        @RequestBody form: CreateFormDto
    ): ShortFormDto = formService.createFormForUserById(userId, form).let {
        logResponse("Create form for user $userId")
        it
    }


    @PostMapping("/getShortFormsWithFilter")
    suspend fun getShortFormsWithFilter(@RequestBody filter: FilterDto): List<ShortFormDto> =
        formService.getShortFormsWithFilter(
            filter
        ).let {
            logResponse("Get all short forms by filter : $filter")
            it
        }

    @PutMapping("/updateMetroInForm")
    suspend fun updateMetroInForm(@RequestBody newMetros: MetroFormDto) {
        formService.updateMetrosInForm(newMetros).let {
            logResponse("Update metro in form : ${newMetros.formId}")
        }
    }

    @GetMapping("/getFavoriteFormsByUserId/{userId}")
    suspend fun getFavoriteFormsByUserId(@PathVariable userId: Long): List<ShortFormDto> =
        formService.getAllFavoriteFormByUserId(userId).let {
            logResponse("Get favorite form by userId : $userId")
            it
        }


    @DeleteMapping("/deleteFavoriteForm")
    suspend fun deleteFavoriteForm(@RequestBody fvDto: FavFormDto) {
        val mainFormId = fvDto.mainFormId
        val favFormId = fvDto.favFormId
        formService.deleteFavoriteForm(mainFormId, favFormId).let {
            logResponse("Delete favorite form by formId : $mainFormId")
        }
    }

    @PutMapping("/addFavoriteForm")
    suspend fun addFavoriteForm(@RequestBody fvDto: FavFormDto) {
        val mainFormId = fvDto.mainFormId
        val favFormId = fvDto.favFormId
        formService.addFavoriteForm(mainFormId, favFormId).let {
            logResponse("Add favorite form by formId : $mainFormId")
        }
    }
}
