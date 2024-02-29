package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.ShortFormDto
import souliving.backend.logger.logResponse
import souliving.backend.service.fake.FakeShortFormService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/shortForm")
class ShortFormController(private var shortFormService: FakeShortFormService) {

    @GetMapping("/")
    fun getAllShortForms(): Flow<ShortFormDto> =
        shortFormService.getAllShortForm().let {
            logResponse("Get all Short Forms")
            it
        }

    @GetMapping("/getShortFormByFormId/{id}")
    suspend fun getShortFormByFormId(@PathVariable id: Long): ShortFormDto? =
        shortFormService.getShortFormByFormId(id)?.let {
            logResponse("Get Short Form by Form id: $id")
            it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Didn't find short form by form id: $id"
        )

    @GetMapping("/getShortFormByUserId/{id}")
    suspend fun getShortFormByUserId(@PathVariable id: Long): ShortFormDto? =
        shortFormService.getShortFormByUserId(id)?.let {
            logResponse("Get Short Form by User id: $id")
            it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Didn't find short form by User id: $id"
        )

}
