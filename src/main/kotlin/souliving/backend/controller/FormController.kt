package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.FavFormDto
import souliving.backend.dto.FormDto
import souliving.backend.dto.MetroFormDto
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

    @PutMapping("/updateMetroInForm")
    suspend fun updateMetroInForm(@RequestBody newMetros: MetroFormDto) {
        formService.updateMetrosInForm(newMetros).let {
            logResponse("Update metro in form : ${newMetros.formId}")
        }
    }

    @GetMapping("/getFavoriteFormsByFormId/{formId}")
    suspend fun getFavoriteFormsByFormId(@PathVariable formId: Long): List<ShortFormDto> =
        formService.getAllFavoriteFormByFormId(formId).let {
            logResponse("Get favorite form by formId : $formId")
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
